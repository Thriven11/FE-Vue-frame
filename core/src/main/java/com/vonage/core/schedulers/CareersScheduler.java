package com.vonage.core.schedulers;

import java.util.Arrays;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.google.gson.JsonObject;
import com.vonage.core.schedulers.interfaces.CareersSchedulerConfig;
import com.vonage.core.services.AppConfigService;
import com.vonage.core.services.CareersIngestionService;
import com.vonage.core.utils.ServiceUtils;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.webservices.Careers;

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

/**
 * Careers Data Sync Scheduler
 *
 * @author Vonage
 *
 */
@Component(immediate = true, service = CareersScheduler.class)
@Designate(ocd = CareersSchedulerConfig.class)
public class CareersScheduler implements Runnable {
  /**
   * Careers Service
   */
  @Reference
  private transient Careers careersService;

  /**
   * Ingestion Service
   */
  @Reference
  private transient CareersIngestionService ingestionService;

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
   * scheduler
   */
  @Reference
  private Scheduler scheduler;

  /**
   * Paths to replicate
   */
  private static final String[] REPLICATION_PATHS = new String[] {"/var/vonage/careers"};

  /**
   * scheduler id
   */
  private int schedulerID;

  /**
   * logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CareersScheduler.class);

  /**
   *
   * @param config CareersSchedulerConfig
   */
  @Activate
  protected final void activate(final CareersSchedulerConfig config) {
    schedulerID = config.schedulerName().hashCode();
    LOGGER.info("CareerScheduler Scheduler  activate method");
    addScheduler(config);
  }

  /**
   *
   * @param config CareersSchedulerConfig
   */
  @Modified
  protected final void modified(final CareersSchedulerConfig config) {
    removeScheduler();
    schedulerID = config.schedulerName().hashCode(); // update schedulerID
    addScheduler(config);
  }

  /**
   *
   * @param config CareersSchedulerConfig
   */
  @Deactivate
  protected final void deactivate(final CareersSchedulerConfig config) {
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
   * @param config CareersSchedulerConfig
   */
  private void addScheduler(final CareersSchedulerConfig config) {
    if (config.serviceEnabled()) {
      ScheduleOptions sopts = scheduler.EXPR(config.schedulerExpression());
      sopts.name(String.valueOf(schedulerID));
      sopts.canRunConcurrently(false);
      scheduler.schedule(this, sopts);
      LOGGER.info("CareerScheduler Scheduler added successfully");
    } else {
      LOGGER.info("CareersScheduler is Disabled, no scheduler job created");
    }
  }

  @Override
  public final void run() {
    try {
      LOGGER.error("Getting careers data");
      ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
      JsonObject jobsData = careersService.getCareersJson("jobs");
      boolean isSuccess = ingestionService.ingestCareers(jobsData);
      JsonObject deptData = careersService.getCareersJson("dept");
      boolean isDeptSuccess = ingestionService.ingestDepartments(deptData);
      JsonObject locData = careersService.getCareersJson("loc");
      boolean isLocSuccess = ingestionService.ingestLocations(locData);
      if (isSuccess
      && isDeptSuccess
      && isLocSuccess
      ) {
        ServiceUtils.replicatePaths(resourceResolver, replicate, ReplicationActionType.ACTIVATE,
            configService.getPublishAgents(), Arrays.asList(REPLICATION_PATHS));
      }
      LOGGER.info("Success: " + isSuccess);
    } catch (RestClientException e) {
      LOGGER.error("Error while importing careers: {}", e.getMessage(), e);
    } catch (ReplicationException e) {
      // TODO Auto-generated catch block
      LOGGER.error("Error while replicating careers: {}", e.getMessage(), e);
    }
  }
}
