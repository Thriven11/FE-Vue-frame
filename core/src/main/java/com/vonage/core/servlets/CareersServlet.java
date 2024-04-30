package com.vonage.core.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

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

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vonage.core.constants.VngCareersConstants;
import com.vonage.core.utils.ServiceUtils;

/**
 * The servlet class for retrieving attribution.
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Get attribution",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.paths=" + "/bin/vonage/api/careers" })
public class CareersServlet extends SlingSafeMethodsServlet {

  /**
   * UID
   */
  private static final long serialVersionUID = 2928204132562034369L;

  /**
   * Logger variable
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CareersServlet.class);

  /**
   * Number of jobs per page
   */
  private static final int NUMBER_OF_JOBS = 20;

  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  /**
   *
   * JobsList
   */
  private List<Hit> list;

  @Override
  public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
      throws ServletException, IOException {
    try {
      final ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
      final QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
      final Session session = resourceResolver.adaptTo(Session.class);

      String sort = "desc";
      int page = 1;

      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");

      final String locId = request.getParameter("loc");
      final String deptId = request.getParameter("dept");
      if (request.getParameter("sort") != null) {
        sort = request.getParameter("sort");
      }
      if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
      }

      final Map predicateMap = new HashMap();
      predicateMap.put("path", VngCareersConstants.CAREERS_JOBS_PATH);
      predicateMap.put("type", "nt:unstructured");
      predicateMap.put("group.p.and", "true");
      if (deptId != null && !deptId.equalsIgnoreCase("0")) {
        predicateMap.put("group.1_property", "@deptIds");
        predicateMap.put("group.1_property.1_value", deptId);
      }
      if (locId != null && !locId.equalsIgnoreCase("0")) {
        predicateMap.put("group.2_property", "@officeIds");
        predicateMap.put("group.2_property.value", locId);
      }
      predicateMap.put("p.limit", "-1");
      predicateMap.put("orderby", "@updatedAt");
      predicateMap.put("orderby.sort", sort);

      final Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);

      final JsonObject jsonOutput = new JsonObject();
      final JsonObject data = new JsonObject();
      final ArrayList<JsonObject> jobsListArray = new ArrayList<JsonObject>();
      final JsonObject dataEle = new JsonObject();

      final SearchResult result = query.getResult();
      this.list = result.getHits();
      // Iterate query results
      for (final Hit hit : list) {
        try {
          final String path = hit.getPath();
          jobsListArray.add(getJobDetails(path));
        } catch (final RepositoryException e) {
          e.printStackTrace();
        } catch (final ParseException e) {
          e.printStackTrace();
        }
      }
      JsonArray jobsList = new JsonArray();
      if (jobsListArray.size() > 0 && jobsListArray.size() >= (page * NUMBER_OF_JOBS)) {
        jobsList = new Gson().toJsonTree(jobsListArray.subList(
                                ((page * NUMBER_OF_JOBS) - NUMBER_OF_JOBS), page * NUMBER_OF_JOBS)).getAsJsonArray();
      } else if (jobsListArray.size() > 0 && jobsListArray.size() < (page * NUMBER_OF_JOBS)) {
        jobsList = new Gson().toJsonTree(jobsListArray.subList(
                                ((page * NUMBER_OF_JOBS) - NUMBER_OF_JOBS), jobsListArray.size())).getAsJsonArray();
      } else {
        if (page > 1) {
          jobsList = new Gson().toJsonTree(new ArrayList<JsonObject>()).getAsJsonArray();
        } else {
          jobsList = new Gson().toJsonTree(jobsListArray).getAsJsonArray();
        }
      }
      dataEle.add("jobsList", jobsList);
      dataEle.addProperty("numberOfJobs", String.valueOf(jobsListArray.size()));
      jsonOutput.add("data", dataEle);
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
         *
         * @param path - JCR path of the job node
         * @return jobObject - JSONObject
         * @throws PathNotFoundException for paths not found
         * @throws ValueFormatException for values
         * @throws ParseException for date parsing
         * @throws RepositoryException for repository errors
         */
        private JsonObject getJobDetails(final String path)
            throws PathNotFoundException, ValueFormatException, RepositoryException, ParseException {
            final JsonObject jobObject = new JsonObject();

            final ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
            Resource jobResource    = resourceResolver.getResource(path);
            Node jobNode = jobResource.adaptTo(Node.class);

            Value[] jobItems = jobNode.getProperty("officeNames").getValues();
            ArrayList<String> locList = new ArrayList<String>();
            for (Value value : jobItems) {
              locList.add(value.getString());
            }
            JsonArray jsArray = new Gson().toJsonTree(locList).getAsJsonArray();
            String deptName = jobNode.getProperty("deptName").getString();

            jobObject.addProperty("id", jobNode.getProperty("jobId").getString());
            jobObject.addProperty("jobRequisitionId", jobNode.getProperty("jobRequisitionId").getString());
            jobObject.addProperty("URL", VngCareersConstants.CAREERS_JOB_DETAILS_URL
                                            + jobNode.getProperty("jobId").getString() + "/");
            jobObject.addProperty("title", jobNode.getProperty("title").getString());

            //TODO: Make this code better
            String dateString = jobNode.getProperty("updatedAt").getString();
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy");
            Date parsedDate = dateParser.parse(dateString);
            jobObject.addProperty("date", dateFormatter.format(parsedDate));
            jobObject.add("locations", jsArray);
            jobObject.addProperty("career", deptName);
            return jobObject;
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
