package com.vonage.core.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vonage.core.services.CareersIngestionService;
import com.vonage.core.utils.ServiceUtils;
import com.vonage.design.restful.clients.BasicClient;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.models.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Careers Ingestion Service
 *
 * @author Vonage
 *
 */
@Component(immediate = true, service = CareersIngestionService.class)
public class CareersIngestionServiceImpl extends BasicClient implements CareersIngestionService {
  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(
    CareersIngestionServiceImpl.class
  );

  /**
   * JCR base path
   */
  private static final String CAREERS_BASE_PATH = "/var/vonage/careers";

  /**
   * JCR base path
   */
  private static final String CAREERS_BASE_BACKUP_PATH = "/var/vonage/careers_backup";

  /**
   * JCR base path
   */
  private static final String GREENHOUSE_DEPTS_ENDPOINT =
      "https://boards-api.greenhouse.io/v1/boards/vonage/departments/";

  /**
   * Jobs path
   */
  private static final String JOBS_PATH = "/jobs/";

  /**
   * Departments path
   */
  private static final String DEPARTMENTS_PATH = "/departments/";

  /**
   * Offices path
   */
  private static final String OFFICES_PATH = "/offices/";

  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  @Override
  public final boolean ingestDepartments(final JsonObject depts) {
    ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
    JsonArray deptsList = depts.getAsJsonArray("departments");
    if (resourceResolver != null) {
      Session session = resourceResolver.adaptTo(Session.class);
      if (session != null) {
        if (deptsList != null && deptsList.size() > 0) {
          deptsList.forEach(
            dept -> {
              if (dept.isJsonObject()) {
                Map<String, String> deptNode = new HashMap<>();
                final JsonObject deptObj = dept.getAsJsonObject();
                if (deptObj.has("parent_id")
                  && !"0".equalsIgnoreCase(deptObj.get("id").toString())
                  && "null".equalsIgnoreCase(deptObj.get("parent_id").toString())) {
                    deptNode.put("id", deptObj.get("id").getAsString());
                    deptNode.put("name", deptObj.get("name").getAsString());
                    ServiceUtils.createProductNode(
                      resourceResolver,
                      CAREERS_BASE_PATH + DEPARTMENTS_PATH + deptNode.get("id"),
                      deptNode
                    );
                }
              }
            }
          );
        }
      }
    }

    return true;
  }


  @Override
  public final boolean ingestLocations(final JsonObject locs) {
    ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
    JsonArray officesList = locs.getAsJsonArray("offices");
    if (resourceResolver != null) {
      Session session = resourceResolver.adaptTo(Session.class);
      if (session != null) {
        if (officesList != null && officesList.size() > 0) {
          officesList.forEach(
            office -> {
              if (office.isJsonObject()) {
                Map<String, String> locationsNode = new HashMap<>();
                final JsonObject officeObj = office.getAsJsonObject();
                if (!"0".equalsIgnoreCase(officeObj.get("id").toString())) {
                  locationsNode.put("id", officeObj.get("id").getAsString());
                  locationsNode.put("name", officeObj.get("name").getAsString());
                  if (!"null".equalsIgnoreCase(officeObj.get("location").toString())) {
                    locationsNode.put("location", officeObj.get("location").getAsString());
                  }
                  ServiceUtils.createProductNode(
                    resourceResolver,
                    CAREERS_BASE_PATH + OFFICES_PATH + officeObj.get("id").toString(),
                    locationsNode
                  );
                }
              }
            }
          );
        }
      }
    }
    return true;
  }

  @Override
  public final boolean ingestCareers(final JsonObject careers) {
    boolean isSuccess;
    if (careers != null) {
      ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(
        resolverFactory
      );
      if (resourceResolver != null) {
        Session session = resourceResolver.adaptTo(Session.class);
        if (session != null) {

          backupExistingCareers();

          Map<String, Map<String, String>> careersMap = new HashMap<>();
          try {
            careersMap.putAll(fetchCareersData(careers));
          } catch (RestClientException e) {
            e.printStackTrace();
          }

          isSuccess =
            ServiceUtils.createProductNode(
              resourceResolver,
              CAREERS_BASE_PATH + DEPARTMENTS_PATH,
              careersMap.get("departments")
            );
          if (isSuccess) {
            isSuccess =
              ServiceUtils.createProductNode(
                resourceResolver,
                CAREERS_BASE_PATH + OFFICES_PATH,
                careersMap.get("offices")
              );
          }
          return isSuccess;
        }
      }
    }
    return false;
  }
  /**
   * Get department name
   * @param deptId Department ID
   * @throws RestClientException - exception
   * @return String department Name
   */
  public final String getDepartmentName(final String deptId)
    throws RestClientException {
    Response response = super.get(GREENHOUSE_DEPTS_ENDPOINT + deptId);
    JsonObject json = new Gson().fromJson(response.getBody().trim(), JsonObject.class);
    String deptName = json.get("name").getAsString();
    return deptName;
  }

  /**
   * Fetch all careers data as a map with nested departments and offices
   * Also create JCR node for each job
   * @param object jobs object from API response
   * @throws RestClientException - exception
   * @return map of jobs by id
   */
private Map<String, Map<String, String>> fetchCareersData(
    final JsonObject object
  )  throws RestClientException {
    ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(
      resolverFactory
    );
    Map<String, Map<String, String>> careersData = new HashMap<>();
    Map<String, String> departments = new HashMap<>();
    Map<String, String> offices = new HashMap<>();
    JsonArray jobsList = object.getAsJsonArray("jobs");
    if (resourceResolver != null) {
      Session session = resourceResolver.adaptTo(Session.class);
      if (session != null) {
        if (jobsList != null && jobsList.size() > 0) {
          jobsList.forEach(
            job -> {
              if (job.isJsonObject()) {
                final JsonObject jobObj = job.getAsJsonObject();
                final HashMap<String, String> jobNode = new HashMap<>();
                final JsonArray departmentsList = jobObj.getAsJsonArray("departments");
                final JsonArray officesList = jobObj.getAsJsonArray("offices");
                final String updatedAt = jobObj.get("updated_at").getAsString();
                final String title = jobObj.get("title").getAsString();
                final String jobId = jobObj.get("id").getAsString();
                LOGGER.info("job id is " + jobId);
                final String jobRequisitionId = jobObj.get("requisition_id").getAsString();
                final ArrayList<String> deptIds = new ArrayList<String>();
                final ArrayList<String> deptNames = new ArrayList<String>();
                final ArrayList<String> officeIds = new ArrayList<String>();
                final ArrayList<String> officeNames = new ArrayList<String>();
                if (officesList != null && officesList.size() > 0) {
                  officesList.forEach(office -> {
                    officeIds.add(office.getAsJsonObject().get("id").getAsString());
                    officeNames.add(office.getAsJsonObject().get("name").getAsString());
                  });
                }
                if (departmentsList != null && departmentsList.size() > 0) {
                  departmentsList.forEach(dept -> {
                    String departmentId = dept.getAsJsonObject().get("id").getAsString();
                    String deptName = dept.getAsJsonObject().get("name").getAsString();
                    // TODO: Consider parentID for the department.
                    if (!dept.getAsJsonObject().get("parent_id").isJsonNull()) {
                      departmentId = dept.getAsJsonObject().get("parent_id").getAsString();
                      try {
                        deptName = this.getDepartmentName(departmentId);
                      } catch (RestClientException e) {
                        e.printStackTrace();
                      }
                    }
                    deptIds.add(String.valueOf(departmentId).toString());
                    deptNames.add(deptName);
                  });
                }

                // convertJsonArrayToMapEntries(departmentsList, departments);
                // convertJsonArrayToMapEntries(officesList, offices);
                jobNode.put("json", String.valueOf(jobObj));
                if (departmentsList != null && departmentsList.size() > 0) {
                  jobNode.put("departments", String.valueOf(departmentsList));
                }
                if (officesList != null && officesList.size() > 0) {
                  jobNode.put("offices", String.valueOf(officesList));
                }
                jobNode.put("updatedAt", updatedAt);
                jobNode.put("title", title);
                jobNode.put("jobId", jobId);
                jobNode.put("jobRequisitionId", jobRequisitionId);

                ServiceUtils.createProductNode(
                  resourceResolver,
                  CAREERS_BASE_PATH + JOBS_PATH + jobObj.get("id").getAsString(),
                  jobNode
                );

                // Add the additional locations and deptsList properties which are used for search
                String[] locationsArray = officeIds.toArray(new String[0]);
                String[] locationsNamesArray = officeNames.toArray(new String[0]);
                Node currentJobNode = resourceResolver.getResource(CAREERS_BASE_PATH
                  + JOBS_PATH + jobObj.get("id")).adaptTo(Node.class);
                try {
                  if (deptIds != null && deptIds.size() > 0) {
                    currentJobNode.setProperty("deptIds", deptIds.get(0).toString());
                    currentJobNode.setProperty("deptName", deptNames.get(0).toString());
                  }
                  currentJobNode.setProperty("officeIds", locationsArray);
                  currentJobNode.setProperty("officeNames", locationsNamesArray);
                  ServiceUtils.commit(resourceResolver);
                } catch (RepositoryException | PersistenceException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }

              }
            }
          );
          careersData.put("departments", departments);
          careersData.put("offices", offices);
        }
      }
    }
    return careersData;
  }

  /**
   * Adds items in a Json Array to a provided Map.
   * @param array Json Array to be converted and put into map
   * @param map Map to be mutated
   */
  private void convertJsonArrayToMapEntries(
    final JsonArray array,
    final Map<String, String> map
  ) {
    if (array != null && array.size() > 0) {
      array.forEach(
        item -> {
          if (item.isJsonObject()) {
            final JsonObject itemObj = item.getAsJsonObject();
            map.put(itemObj.get("id").getAsString(), String.valueOf(itemObj));
          }
        }
      );
    }
  }

  /**
   * Backup existing careers from JCR
   */
  private void backupExistingCareers() {
    ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(
      resolverFactory
    );
    if (resourceResolver != null) {
      Session session = resourceResolver.adaptTo(Session.class);
      try {
        if (session != null && session.itemExists(CAREERS_BASE_BACKUP_PATH)) {
          session.removeItem(CAREERS_BASE_BACKUP_PATH);
          session.save();
        }
        session.move(CAREERS_BASE_PATH, CAREERS_BASE_BACKUP_PATH);
        session.save();

        if (!session.itemExists(CAREERS_BASE_PATH)) {
          ResourceUtil.getOrCreateResource(resourceResolver, CAREERS_BASE_PATH,
                          JcrResourceConstants.NT_SLING_FOLDER, (String) null, true);
          session.save();
        }

      } catch (RepositoryException e) {
        LOGGER.error("Unable to remove existing careers. Data may be stale. Error: {}", e.getMessage(), e);
      } catch (PersistenceException e) {
        LOGGER.error("Unable to create careers folder. Data may be stale. Error: {}", e.getMessage(), e);
      }
    }
  }
}
