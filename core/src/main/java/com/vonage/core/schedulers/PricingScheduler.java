package com.vonage.core.schedulers;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.google.gson.JsonObject;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.PricingConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.schedulers.interfaces.PricingSchedulerConfig;
import com.vonage.core.services.AppConfigService;
import com.vonage.core.services.PricingIngestionService;
import com.vonage.core.utils.ServiceUtils;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.webservices.CommApiPricing;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

/**
 * Pricing Data Sync Scheduler
 *
 * @author Vonage
 *
 */
@Component(immediate = true, service = PricingScheduler.class)
@Designate(ocd = PricingSchedulerConfig.class)
public class PricingScheduler implements Runnable {
  /**
   * scheduler
   */
  @Reference
  private Scheduler scheduler;

  /**
   * scheduler id
   */
  private int schedulerID;

  /**
   * CommApiPricing Service
   */
  @Reference
  private transient CommApiPricing commApiService;

  /**
   * Ingestion Service
   */
  @Reference
  private transient PricingIngestionService ingestionService;

  /**
   * Replicate service
   */
  @Reference
  private Replicator replicate;

  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  /**
   * Vonage configuration service
   */
  @Reference
  private AppConfigService configService;

  /**
   * Country CSV
   */
  private static final String COUNTRY_CSV = PricingConstants.COUNTRY_CSV;

  /**
   * Products CSV
   */
  private static final String PRODUCTS_CSV = "messaging,voice,verify";

  /**
   * Paths to replicate
   */
  private static final String[] REPLICATION_PATHS = new String[] {
    "/var/commerce/products/vonage/communications-api/messaging",
    "/var/commerce/products/vonage/communications-api/voice",
    "/var/commerce/products/vonage/communications-api/verify",
    "/var/commerce/products/vonage/communications-api"
  };

  /**
   * Sleep for definite period in between multiple hits.
   */
  private static final int WAIT_TIME = VonageConstants.THREAD_WAIT_TIME;

  /**
   * logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(
    PricingScheduler.class
  );

  /**
   *
   * @param config PricingSchedulerConfig
   */
  @Activate
  protected final void activate(final PricingSchedulerConfig config) {
    schedulerID = config.schedulerName().hashCode();
    LOGGER.info("Pricing Scheduler activated");
    addScheduler(config);
  }

  /**
   *
   * @param config PricingSchedulerConfig
   */
  @Modified
  protected final void modified(final PricingSchedulerConfig config) {
    removeScheduler();
    schedulerID = config.schedulerName().hashCode(); // update schedulerID
    addScheduler(config);
  }

  /**
   *
   * @param config PricingSchedulerConfig
   */
  @Deactivate
  protected final void deactivate(final PricingSchedulerConfig config) {
    removeScheduler();
  }

  /**
   * Remove a scheduler based on the scheduler ID
   */
  private void removeScheduler() {
    LOGGER.info("Removing Scheduler Job '{}'", schedulerID);
    scheduler.unschedule(String.valueOf(schedulerID));
  }

  /**
   * Add a scheduler based on the scheduler ID
   * @param config PricingSchedulerConfig
   */
  private void addScheduler(final PricingSchedulerConfig config) {
    if (config.serviceEnabled()) {
      ScheduleOptions sopts = scheduler.EXPR(config.schedulerExpression());
      sopts.name(String.valueOf(schedulerID));
      sopts.canRunConcurrently(false);
      scheduler.schedule(this, sopts);
      LOGGER.info("Pricing Scheduler added successfully");
    } else {
      LOGGER.info("Pricing is Disabled, no scheduler job created");
    }
  }

  /**
   * Get and ingest pricing
   */
  private void getPricing() {
    try {
      String[] countries = COUNTRY_CSV.split(",");
      String[] products = PRODUCTS_CSV.split(",");
      JsonObject exchangeRateJson = commApiService.getExchangeRateJson(PricingConstants.PN_USD);
      ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
      if (exchangeRateJson != null && exchangeRateJson.has(PricingConstants.JSON_RATES)) {
        ingestionService.ingestRates(exchangeRateJson.get(PricingConstants.JSON_RATES).getAsJsonObject());
        LOGGER.info("Rates: " + exchangeRateJson.get(PricingConstants.JSON_RATES));
      }
      for (String countryCode : countries) {
        for (String product : products) {
          String apiUrl =
            AppConstants.SLASH
            + product
            + AppConstants.SLASH
            + countryCode
            + AppConstants.SLASH
            + "jsonp";
          boolean isSuccess = ingestionService.ingestPrice(
            commApiService.getPricingJson("/pricing" + apiUrl),
            apiUrl.replace("/jsonp", "")
          );
          LOGGER.info(
            "Executing ------------- " + apiUrl + ", Success: " + isSuccess
          );
          Thread.sleep(WAIT_TIME);
        }
      }
      ServiceUtils.replicatePaths(
        resourceResolver,
        replicate,
        ReplicationActionType.ACTIVATE,
        configService.getPublishAgents(),
        Arrays.asList(REPLICATION_PATHS)
      );
      LOGGER.info("Pricing data import complete");
    } catch (RestClientException | InterruptedException | ReplicationException e) {
      LOGGER.error("Error while importing prices: {}", e.getMessage(), e);
    }
  }

  @Override
  public final void run() {
    LOGGER.info("Getting pricing data");
    getPricing();
  }
}
