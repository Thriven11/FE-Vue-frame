package com.vonage.core.services.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.vonage.core.utils.ServiceUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.Resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vonage.core.services.CareersService;

/**
 * Implementation class for Careers Data Connector
 *
 * @author Vonage
 *
 */
@Component(service = CareersService.class)
public class CareersServiceImpl implements CareersService {

  /** offices data path */
  private static final String OFFICES_PATH = "/var/vonage/careers/offices";
  /** departments data path */
  private static final String DEPARTMENTS_PATH = "/var/vonage/careers/departments";
  /** jobs data path */
  private static final String JOBS_PATH = "/var/vonage/careers/jobs";

  /**
     * log variable
     */
    private static final Logger LOG = LoggerFactory.getLogger(CareersServiceImpl.class);

  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  /**
   * Resource Resolver
   */
  private ResourceResolver resourceResolver;

  /**
   * Activate method
   */
  @Activate
  @Modified
  protected final void activate() {
    resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
  }

  @Override
  public final Map<String, String> getDepartments() {
    Map<String, String> deptsList = new HashMap<>();
    final Resource deptsNode = resourceResolver.getResource(DEPARTMENTS_PATH);
    deptsNode.getChildren().forEach(child -> {
      final Node deptNode = child.adaptTo(Node.class);
      try {
        String deptId = deptNode.getProperty("id").getString();
        String deptName = deptNode.getProperty("name").getString();
        deptsList.put(deptId, deptName);
      } catch (RepositoryException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
    Map<String, String> sortedDeptList =
      deptsList.entrySet().stream()
        .sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
          (e1, e2) -> e1, LinkedHashMap::new));
    return sortedDeptList;
  }

  @Override
  public final Map<String, String> getOffices() {
    Map<String, String> officesList = new HashMap<>();
    final Resource officesNode = resourceResolver.getResource(OFFICES_PATH);
    officesNode.getChildren().forEach(child -> {
      final Node deptNode = child.adaptTo(Node.class);
      try {
        String officeId = deptNode.getProperty("id").getString();
        String officeName = deptNode.getProperty("name").getString();
        officesList.put(officeId, officeName);
      } catch (RepositoryException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });

    Map<String, String> sortedOfficesList =
      officesList.entrySet().stream()
        .sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
          (e1, e2) -> e1, LinkedHashMap::new));

    return sortedOfficesList;
  }

  @Override
  public final Resource getJobResource(final String jobID) {
    Resource resource = resourceResolver.getResource(JOBS_PATH + "/" + jobID);
    return resource;
  }

  @Override
  public final JsonObject getJobResourceAsJsonObject(final String jobID) {
    Resource resource = resourceResolver.getResource(JOBS_PATH + "/" + jobID);
    JsonObject jobDetailObject = null;
    if (resource != null) {

      final ValueMap resourceProperties = resource.adaptTo(ValueMap.class);
      final String jobDataString = resourceProperties.get("json", (String) null);

      if (jobDataString != null) {
        LOG.info("JSON OBJECT: " + jobDataString);

         // split json into objects
        final JsonParser jsonParser = new JsonParser();
        jobDetailObject = jsonParser.parse(jobDataString).getAsJsonObject();

      } else {
        LOG.info("------ NO DATA FOUND AGAINST SELECTOR ID -------");
      }
    } else {
      LOG.info("------ NO DATA FOUND AGAINST SELECTOR ID -------");
    }

    return jobDetailObject;
  }
}
