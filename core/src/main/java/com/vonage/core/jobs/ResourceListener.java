package com.vonage.core.jobs;

import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.utils.ServiceUtils;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.commons.lang.StringUtils;


/**
 * A services to demonstrate how changes in replication
 * can be listened for. It registers an event handler services.
 * The component is activated immediately after the bundle is
 * started through the immediate flag.
 * Please note, that apart from EventHandler services,
 * the immediate flag should not be set on a services.
 *
 * Event Mechanism in Sling is "Fire event Forget about it" . So there is no guarantee
 * that the event will execute what it needs to if something goes wrong
 * So we have to schedule jobs in such cases
 */
@Component(service = EventHandler.class,
  immediate = true,
  property = {
    Constants.SERVICE_DESCRIPTION + "=Demo to listen on changes in the replication",
    EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC
  })
public class ResourceListener implements EventHandler {

  /**
   * logger for logging.
   */
  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * jobManager for managing sling jobs.
   */
  @Reference
  private JobManager jobManager;

    /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;


  /**
   * ALLOWED CONTENT PATH FOR SWIFTYPE
  */
  private static final String CONTENT_PATH = VonageConstants.SITES_ROOT_PATH;

  /**
   * REPLICATION JOB PATH
  */
  private static final String REPLICATION_JOB_PATH = VonageConstants.REPLICATION_JOB_PATH;

  /**
   * handleEvent for handling event
   * @param event Event object
   */
  public final void handleEvent(final Event event) {
    try {
      logger.info("Resource event: {} at: {}", event.getTopic());
      logger.info("Replication Event is {}", ReplicationAction.fromEvent(event).getType());
      if (ReplicationAction.fromEvent(event).getType().equals(ReplicationActionType.ACTIVATE)) {

        String eventPath = ReplicationAction.fromEvent(event).getPath();

        // Currently, we only need to add pages to the job
        if (StringUtils.startsWith(eventPath, "/content/")) {

          logger.info("Triggered activate on {}", eventPath);

          //Create a property map to pass it to the JobConsumer service
          Map<String, Object> jobProperties = new HashMap<String, Object>();
          jobProperties.put("path", eventPath);

          ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
          Externalizer externalizer = resourceResolver.adaptTo(Externalizer.class);

          if (resourceResolver.getResource(eventPath) != null) {
            // Get the resource, we can check resourceType to check if page, asset or file
            Resource resource = resourceResolver.getResource(eventPath);
            String resourceType = resource.getResourceType();
            jobProperties.put("resourceType", resourceType);

            // We only need to handle pages at the moment
            // Check pages are under our content path
            if (resourceType.contains("cq:Page") && StringUtils.startsWith(eventPath, CONTENT_PATH)) {

              Page page = resourceResolver.getResource(eventPath)
              .adaptTo(Page.class)
              .getAbsoluteParent(4);

              // Check we have a page lang and country for mapping to the external url
              if (page.getLanguage().getLanguage() != null && page.getLanguage().getCountry() != null) {
                String language = page.getLanguage().getLanguage();
                String country = page.getLanguage().getCountry();
                String publishMapKey = "publish_" + country.toLowerCase() + "-" + language.toLowerCase();
                String publishEndPath = eventPath.replace(page.getAbsoluteParent(4).getPath(), "") + "/";
                // Handle the additional alphabets in the articles
                if (StringUtils.contains(publishEndPath, "/resources/articles/")) {
                  String[] publishUrlArray = publishEndPath.split(AppConstants.SLASH);
                  ArrayList<String> publishUrlList = new ArrayList<>(Arrays.asList(publishUrlArray));
                  publishUrlList.remove(3);
                  publishEndPath = String.join(AppConstants.SLASH, publishUrlList) + AppConstants.SLASH;
                }
                String externalizedUrl = externalizer.externalLink(resourceResolver, publishMapKey, publishEndPath);

                // Add all required properties to the job
                jobProperties.put("externalizedUrl", externalizedUrl);
                jobProperties.put("country", country);
                jobProperties.put("language", language);

              }
            }
            // If we need to handle images or files, use the resourceType condition
            // An asset has a resourceType dam:Asset
            // Sitemap has a resourceType nt:file

            // Ensure that we have all properties needed to process the job
            if (jobProperties.get("path") != null
                && jobProperties.get("externalizedUrl") != null
                && jobProperties.get("language") != null
                && jobProperties.get("country") != null) {

              // Point to the registered Job Consumer Property Topics
              jobManager.addJob(REPLICATION_JOB_PATH, jobProperties);
              logger.info("the job has been started for: {}", jobProperties);
            } else {
              logger.info("We don't have all required properties, don't add the sling job");
            }

          }
        }
      }
    } catch (Exception e) {
      logger.error("Exception is ", e);
    }
  }
}
