package com.vonage.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.facets.Bucket;
import com.day.cq.search.facets.Facet;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vonage.core.constants.VngCareersConstants;
import com.vonage.core.models.careers.DepartmentModel;
import com.vonage.core.utils.ServiceUtils;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The servlet class for retrieving attribution.
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Get attribution",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.paths=" + "/bin/vonage/api/careers/map",
    "sling.servlet.extension=" + "json" })
public class CareersMapServlet extends SlingSafeMethodsServlet {

  /**
   * UID
   */
  private static final long serialVersionUID = 2928204132562034369L;

  /**
   * Logger variable
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CareersMapServlet.class);

  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  @Override
  public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
      throws ServletException, IOException {
    try {
      final ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
      final QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
      final Session session = resourceResolver.adaptTo(Session.class);

      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      final JsonObject jsonOutput = new JsonObject();
      final JsonObject data = new JsonObject();
      JsonArray jobsList = new JsonArray();
      // ArrayList<JsonObject> locationsArray = new ArrayList<JsonObject>();
      JsonObject locationsObj = new JsonObject();

      Resource officesRes = resourceResolver.getResource(VngCareersConstants.CAREERS_OFFICE_PATH);
      Iterator<Resource> officeIter = officesRes.listChildren();
      while (officeIter.hasNext()) {
        Resource officeRes = officeIter.next();
      //officesRes.getChildren().forEach(officeRes -> {
        LOGGER.error("path is " + officeRes.getPath());
        Node officeNd = officeRes.adaptTo(Node.class);
        LOGGER.error(" id is " + officeRes.getValueMap().get("id", String.class));
          String id = officeRes.getValueMap().get("id").toString();
          final HashMap predicateMap = new HashMap();
            predicateMap.put("path", VngCareersConstants.CAREERS_JOBS_PATH);
            predicateMap.put("type", "nt:unstructured");
            predicateMap.put("1_property", "deptIds");
            predicateMap.put("2_property", "officeIds");
            predicateMap.put("2_property.operation", "equals");
            predicateMap.put("2_property.value", id);
            predicateMap.put("facets", true);

            final Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);
            final SearchResult result = query.getResult();
            JsonObject locObj = new JsonObject();
            JsonObject departmentsObj = new JsonObject();
            locObj.addProperty("total", String.valueOf(result.getTotalMatches()));

            // Facets
            try {
              if (result.getTotalMatches() > 0 && result.getFacets().size() > 0) {
                Map<String, Facet> facets = result.getFacets();
                LOGGER.error("facets list size " + facets.size());
                LOGGER.error("facets keyset " + facets.keySet().toString());
                Facet facet = facets.get("1_property");
                if (facet.getContainsHit()) {
                  LOGGER.error(" Location ID is 4009298002");
                  for (Bucket bucket : facet.getBuckets()) {
                    LOGGER.error(bucket.getValue() + " : " + bucket.getCount());
                    departmentsObj.addProperty(bucket.getValue(), bucket.getCount());
                  }
                  locObj.add("departments", departmentsObj);
                  locationsObj.add(id, locObj);
                }
              }
            } catch (RepositoryException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
         // });
          }

      JsonObject locations = new JsonObject();
      locations.add("locations", locationsObj);
      locations.add("departments", this.getDepartments());

      jsonOutput.add("data", locations);
      response.setStatus(SlingHttpServletResponse.SC_OK);
      response.getWriter().write(jsonOutput.toString());

    } catch (IOException e) {
      LOGGER.error("Can't build data object! Reason: {}", e.getMessage(), e);
      JsonObject jsonOutput = getErrorObject("500", "Internal server error");
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write(jsonOutput.toString());
      return;
    }
  }

   /**
   * Get job details
   * @return departmentsObj - JSONObject
   */
   final JsonObject getDepartments() {
    JsonObject deptObj = new JsonObject();
    final ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
    Resource deptRes = resourceResolver.getResource(VngCareersConstants.CAREERS_DEPARTMENTS_PATH);
        Iterator<Resource> depIter = deptRes.listChildren();
    while (depIter.hasNext()) {
      Resource dept = depIter.next();
    //deptRes.getChildren().forEach(dept -> {
      DepartmentModel deptModel = dept.adaptTo(DepartmentModel.class);
      deptObj.addProperty(deptModel.getId(), deptModel.getName());
    //});
    }
    return deptObj;
  }
    /**
     * Get error object
     *
     * @param status - Status for the error
     * @param title  - Title for the error
     * @return errorObject - JSONObject
     */
    private JsonObject getErrorObject(final String status, final String title) {
        final JsonObject errorObject = new JsonObject();

        final JsonArray errors = new JsonArray();
        final JsonObject error = new JsonObject();

        error.addProperty("status", status);
        error.addProperty("title", title);
        errors.add(error);
        errorObject.add("errors", errors);

        return errorObject;
    }

}
