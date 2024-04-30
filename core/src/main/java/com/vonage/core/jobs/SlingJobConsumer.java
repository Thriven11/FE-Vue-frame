package com.vonage.core.jobs;

import com.day.cq.wcm.api.Page;
import com.vonage.core.constants.ThirdPartyConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.utils.ServiceUtils;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A JobConsumer Service implementation to demonstrate how to
 * create a JobConsumer to help schedule Jobs.
 * A Job is a special event that will be processed exactly once. When a job is scheduled , it will be
 * available in /var/eventing/jobs . Even when the system fails, the jobs int he queue will retry to execute
 * till it fails
 */

@Component(service = JobConsumer.class,
  immediate = true,
  property = {
    Constants.SERVICE_DESCRIPTION + "=Demo to listen on changes in the resource tree",
    JobConsumer.PROPERTY_TOPICS + "=" + VonageConstants.REPLICATION_JOB_PATH
  })
public class SlingJobConsumer implements JobConsumer {

  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  /**
   * logger for logging.
   */
  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * SWIFTYPE API KEY
  */
  private static final String SWIFT_API_KEY = ThirdPartyConstants.SWIFT_API_KEY;

  /**
   * SWIFTYPE API CRAWL URL
  */
  private static final String SWIFT_API_CRAWL_URL = ThirdPartyConstants.SWIFT_API_CRAWL_URL;

  /**
   * SWIFTYPE ENGINE
  */
  private static final String SWIFT_ENGINE = ThirdPartyConstants.SWIFT_ENGINE;

  /**
   * SWIFTYPE ENGINE
  */
  private static final String SWIFT_ENGINE_STAGE = ThirdPartyConstants.SWIFT_ENGINE_STAGE;

  /**
   * ALLOWED CONTENT PATH FOR SWIFTYPE
  */
  private static final String SWIFT_ALLOWED_PATH = VonageConstants.SITES_ROOT_PATH;

   /**
   * ALLOWED Staging path SWIFTYPE
  */
  private static final String STAGING_PATH = VonageConstants.SITES_STAGING_PATH;

  /**
   * THREAD WAIT TIME
  */
  private static final int WAIT_TIME = VonageConstants.THREAD_WAIT_TIME;

   /**
   * THREAD WAIT TIME ST
  */
  private static final int WAIT_TIME_ST = VonageConstants.THREAD_WAIT_TIME_ST;

  /**
   * CLOUDFLARE API PASS
  */
  private static final String CF_API_KEY = ThirdPartyConstants.CF_API_KEY;

  /**
   * CLOUDFLARE API EMAIL / US
  */
  private static final String CF_API_EMAIL = ThirdPartyConstants.CF_API_EMAIL;

  /**
   * CLOUDLARE API URI START
  */
  private static final String CF_API_URI_START = ThirdPartyConstants.CF_API_URI_START;

  /**
   * CLOUDLARE API URI END
  */
  private static final String CF_API_URI_END = ThirdPartyConstants.CF_API_URI_END;

  /**
   * ALLOWED CONTENT PATH FOR CLOUDFLARE
  */
  //private static final String CF_ALLOWED_PATH = "/content/";
  private static final String CF_ALLOWED_PATH = VonageConstants.SITES_ROOT_PATH;

  /**
   * resource country
   *
   * @return resourceCountry
   */
  private String resourceCountry;

  /**
   * resource language
   *
   * @return resourceLanguage
   */
  private String resourceLanguage;

  /**
   * swiftype domain id
   *
   * @return swiftDomainId
   */
  private String swiftDomainId;

  /**
   * cloudflare zone key
   *
   * @return cloudflareZoneKey
   */
  private String cloudflareZoneKey;


  @Override
  public final JobResult process(final Job job) {
    logger.debug("{}: trying to execute job: {}", job.getTopic());
    try {
        //A Property map will be passed on so we can fetch the values we need here to//Process the request
        String path = (String) job.getProperty("path");
        String language = (String) job.getProperty("language");
        String country = (String) job.getProperty("country");
        String externalizedUrl = (String) job.getProperty("externalizedUrl");

        // Set page/resource country and language
        this.resourceCountry = country.toUpperCase();
        this.resourceLanguage = language.toUpperCase();
        // Set the swiftype and cloudflare id and key need for payload
        this.setSwiftDomainId();
        this.setCloudFlareZoneKey();
        //final String swiftDomainId = getSwiftDomainId();
        //final String cloudflareZoneKey = getCloudFlareZoneKey();

        //logger.info("Processing the JOB *******");
        // Map all values to log
        Map<String, String> requestInfo = new HashMap<>();
        requestInfo.put("Externalized URL", externalizedUrl);
        requestInfo.put("Country", country);
        requestInfo.put("Language", language);
        requestInfo.put("CF zone key", cloudflareZoneKey);
        requestInfo.put("ST domain ID", swiftDomainId);

         /////////////////////////////// CLOUDFLARE API REQUEST ///////////////////////////////
        // Check we get a zone key
        if (path.contains(CF_ALLOWED_PATH)
            && cloudflareZoneKey != null) {
          // HTTP POST request to cloudflare api
          final URL url = new URL(CF_API_URI_START + cloudflareZoneKey + CF_API_URI_END);
          final HttpURLConnection con = (HttpURLConnection) url.openConnection();
          con.setRequestMethod("POST");
          con.setRequestProperty("X-Auth-Email", CF_API_EMAIL);
          con.setRequestProperty("X-Auth-Key", CF_API_KEY);
          con.setRequestProperty("Content-Type", "application/json");
          con.setRequestProperty("Accept", "application/json");
          con.setDoOutput(true);
          // PAYLOAD
          final String jsonInputString
          = "{\"files\":[\"" + externalizedUrl + "\"]}";
          //= "{\"files\":[\"https://www.vonage.com\"]}";
          // Log payload
          requestInfo.put("CF payload", jsonInputString.toString());
          Thread.sleep(WAIT_TIME);

          try (OutputStream os = con.getOutputStream()) {
            final byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
          } catch (final Exception e) {
            logger.error("Exception is ", e);
          }

          final int code = con.getResponseCode();
          System.out.println(code);
          requestInfo.put("CF response code", Integer.toString(code));
          try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            final StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
              response.append(responseLine.trim());
            }
            // Log response
            requestInfo.put("Cloudflare API Response", response.toString());
          } catch (final Exception e) {
            logger.error("Exception is ", e);
          }
        }
          /////////////////////////////// SWIFTYPE API REQUEST ///////////////////////////////
        // Check if path contains /content/vonage/live-copies/
        // Check we get a domain ID
        // Check if the page is disabled for SEO and Crawling
        if (path.contains(SWIFT_ALLOWED_PATH) && swiftDomainId != null
          && !getDisableSeoFlag(path)) {
            String swiftengineslug = "";
          if (externalizedUrl.contains(STAGING_PATH)) {
            swiftengineslug = SWIFT_ENGINE_STAGE;
          } else {
            swiftengineslug = SWIFT_ENGINE;
          }
          if (externalizedUrl.contains(STAGING_PATH)) {
            swiftDomainId = ThirdPartyConstants.SWIFT_ID_US_STAGING;
          }
          // HTTP PUT request to swiftype api
          final URL url = new URL(SWIFT_API_CRAWL_URL + swiftengineslug
          + "/domains/"
          + swiftDomainId + "/crawl_url.json");

          // Log URL
          requestInfo.put("ST request URL", url.toString());

          final HttpURLConnection con = (HttpURLConnection) url.openConnection();
          con.setRequestMethod("PUT");
          con.setRequestProperty("Content-Type", "application/json");
          con.setRequestProperty("Accept", "application/json");
          con.setDoOutput(true);
          // PAYLOAD - api key + url to submit
          final String jsonInputString
          = "{\"auth_token\": \"" + SWIFT_API_KEY + "\",\"url\": \"" + externalizedUrl + "\"}";
          // Log payload
          requestInfo.put("ST payload", jsonInputString.toString());

          Thread.sleep(WAIT_TIME_ST);

          try (OutputStream os = con.getOutputStream()) {
            final byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
          } catch (final Exception e) {
            logger.error("Exception is ", e);
          }

          final int code = con.getResponseCode();
          System.out.println(code);

          requestInfo.put("ST response code", Integer.toString(code));

          try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            final StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
              response.append(responseLine.trim());
            }
            // Log response
            requestInfo.put("Swiftype API Response", response.toString());
          } catch (final Exception e) {
            logger.error("Exception is ", e);
          }
        }
        /**
         * Return the proper JobResult based on the work done...
         * > OK : Processed successfully > FAILED: Processed unsuccessfully and
         * reschedule --> This will keep the JOB up for next retry > CANCEL: Processed
         * unsuccessfully and do NOT reschedule > ASYNC: Process through the
         * JobConsumer.AsyncHandler interface
         */
        // log hashmap values for job
        logger.info("******* Sling Job Process Properties *******: "
        + System.lineSeparator() + "{}", requestInfo
        + System.lineSeparator());

        return JobConsumer.JobResult.OK;

    } catch (final Exception e) {
      logger.error("Exception is ", e);
      return JobResult.FAILED;
    }
  }

  /**
   * This method gets the value to see if this page is SEO disabled for crawlers.
   * This condition would also apply for swifttype. VONDEV-1007
   * @param resPath Page path
   * @return disableSeo returns true if the page is disabled for seo and swiftype
   */
  private boolean getDisableSeoFlag(final String resPath) {
    ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
    ValueMap pageProperties = resourceResolver.getResource(resPath).adaptTo(Page.class).getProperties();
    boolean disableSEO = pageProperties.containsKey("disableIndexForSEO");
    return disableSEO;
  }

  /**
   * Setter swiftDomainId
   */
  private void setSwiftDomainId() {

    // HashMap with swiftype domain id's
    final HashMap<String, String> swiftDomains = new HashMap<String, String>();
    swiftDomains.put("TW", ThirdPartyConstants.SWIFT_ID_TW);
    swiftDomains.put("MY", ThirdPartyConstants.SWIFT_ID_MY);
    swiftDomains.put("NZ", ThirdPartyConstants.SWIFT_ID_NZ);
    swiftDomains.put("HK", ThirdPartyConstants.SWIFT_ID_HK);
    swiftDomains.put("ID", ThirdPartyConstants.SWIFT_ID_ID);
    swiftDomains.put("SG", ThirdPartyConstants.SWIFT_ID_SG);
    swiftDomains.put("PH", ThirdPartyConstants.SWIFT_ID_PH);
    swiftDomains.put("AU", ThirdPartyConstants.SWIFT_ID_AU);
    swiftDomains.put("GB", ThirdPartyConstants.SWIFT_ID_GB);
    swiftDomains.put("US", ThirdPartyConstants.SWIFT_ID_US);

    // Get the swiftype domain id based on the country from hashmap
    //return (String) swiftDomains.get(this.resourceCountry);
    swiftDomainId = swiftDomains.get(this.resourceCountry);

  }

  /**
   * Setter cloudflareZoneKey
   */
  private void setCloudFlareZoneKey() {

      // HashMap with cloudflare zone keys based on resource langugae and country
      final HashMap<String, String> cloudFlareZones = new HashMap<String, String>();
      cloudFlareZones.put("DE_DE", "e7d3a3f273289489f72d7d15b8f1ff57");
      cloudFlareZones.put("EN_US", "47ef267f4aa43d8065db1b5426429db4");
      cloudFlareZones.put("EN_AU", "8166f00fc94772ed1026441cea5b35e4");
      cloudFlareZones.put("EN_CA", "8d9707b9f8433dc710d20b424c69b492");
      cloudFlareZones.put("EN_GB", "f8e70f3b646c482a7652bd0c171ce680");
      cloudFlareZones.put("EN_HK", "589849fb0de2e0fac0daba3a0bd4f71a");
      cloudFlareZones.put("EN_ID", "b5b12ac360ca41c4bcbbe359ea98cc8c");
      cloudFlareZones.put("EN_MY", "cd78943adca6bc3bf02027f2cb07f433");
      cloudFlareZones.put("EN_NZ", "9fa306db974e7a349788d5e81ab1d0b2");
      cloudFlareZones.put("EN_PH", "a4a3436522fcddbbb604e36b9e7a9dbc");
      cloudFlareZones.put("EN_SG", "4d1e897b67e61329324b0450d68ecba2");
      cloudFlareZones.put("EN_TW", "339edfee21ecb2be2604da66bdb8f3be");
      cloudFlareZones.put("ES_AR", "0e7c6a88322c50fe1c0a07b26eea90b9");
      cloudFlareZones.put("ES_CL", "e50087d6edf2d5f8f547d4f78d7466d6");
      cloudFlareZones.put("ES_CO", "3366a3957c4a336f56d0c7808da60920");
      cloudFlareZones.put("ES_ES", "36238b721b249fac7098944aa51be21a");
      cloudFlareZones.put("ES_MX", "ef27f11ac3fb0ee3bf5a18dc9da09ede");
      cloudFlareZones.put("FR_FR", "75ee272913c02dc94a34a44d1ef7f99a");
      cloudFlareZones.put("JA_JP", "f6368657081eebac17fbd9c12a12b874");
      cloudFlareZones.put("KO_KR", "88629460b11a1a79dafadccaf2785be9");
      cloudFlareZones.put("RU_RU", "d34ac24d841327b3f810a1765c9514bf");
      cloudFlareZones.put("ZH_CN", "2376018e0983fc735776a6d4f37751d2");

      // Get the cloudflare zone key based on the langauge and country from hashmap
      //return (String) cloudFlareZones.get(this.resourceLanguage + "_" + this.resourceCountry);
      cloudflareZoneKey = cloudFlareZones.get(this.resourceLanguage + "_" + this.resourceCountry);
  }


   /**
     * Get Swiftype Domain ID
     * @return swiftDomainId
     */
    public final String getSwiftDomainId() {
      return this.swiftDomainId;
  }

   /**
     * Get Cloudflare Zone Key
     * @return cloudflareZoneKey
     */
    public final String getCloudFlareZoneKey() {
      return this.cloudflareZoneKey;
  }

}
