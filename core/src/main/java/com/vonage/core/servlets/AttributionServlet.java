package com.vonage.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.utils.ServiceUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
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
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/vonage/api/attribution" })
public class AttributionServlet extends SlingSafeMethodsServlet {

    /**
     * UID
     */
    private static final long serialVersionUID = 2928204132562034369L;

     /**
     * Resource Resolver Factory
     */
    @Reference
    private ResourceResolverFactory resolverFactory;

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AttributionServlet.class);

    @Override
    public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String reqUrl = request.getRequestURI();
        String[] campaignNameArr = reqUrl.split("[.]");
        String cmpName = null;
        if (campaignNameArr.length > 1) {
            cmpName = campaignNameArr[1];
        }
        ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
        JsonObject jsonOutput = new JsonObject();
        JsonObject data = new JsonObject();
        if (cmpName != null) {
            try {
                if (StringUtils.contains(cmpName, "-api-")) {
                    data.addProperty("name", cmpName);
                    data.addProperty("id", "7011O000002cfuc");
                    data.addProperty("tfn", "1.844.365.9460");
                    jsonOutput.add("data", data);
                } else {
                    Resource attributionResource = resourceResolver
                            .getResource(VonageConstants.ATTRIBUTION_CAMPAIGNS_PATH + "/" + cmpName);
                    if (attributionResource != null) {
                        ValueMap properties = attributionResource.adaptTo(ValueMap.class);
                        if (null != properties) {
                            // remove carriage return and trim whitespace
                            String id = properties.get("campaignId", (String) null).replaceAll("\\\\r", "").trim();
                            String tfn = properties.get("campaignTfn", (String) null);
                            data.addProperty("name", cmpName);
                            data.addProperty("id", id);
                            data.addProperty("tfn", tfn);
                            jsonOutput.add("data", data);
                        }
                    }
                }
                response.setStatus(SlingHttpServletResponse.SC_OK);
                response.getWriter().write(jsonOutput.toString());
                return;

            } catch (IOException e) {
                LOGGER.error("IO Can't build data object! Reason: {}", e.getMessage(), e);
                jsonOutput = getErrorObject("500", "Internal server error");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(jsonOutput.toString());
                return;
            } catch (Exception e) {
                LOGGER.error(": Exception- {}", e.getMessage());
                jsonOutput = getErrorObject("500", "Internal server error");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(jsonOutput.toString());
                return;
            } finally {
                if (resourceResolver.isLive()) {
                    resourceResolver.close();
                }
            }
        } else {
            jsonOutput = getErrorObject("404", "Campaign not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(jsonOutput.toString());
        }
    }

    /**
     * Get error object
     *
     * @param status - Status for the error
     * @param title  - Title for the error
     * @return errorObject - JSONObject
     */
    private JsonObject getErrorObject(final String status, final String title) {
        JsonObject errorObject = new JsonObject();

        JsonArray errors = new JsonArray();
        JsonObject error = new JsonObject();

        error.addProperty("status", status);
        error.addProperty("title", title);
        errors.add(error);
        errorObject.add("errors", errors);

        return errorObject;
    }

}
