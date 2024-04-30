package com.vonage.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The servlet class for retrieving geolocation information from CloudFlare CDN.
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Get geolocation",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/vonage/api/geolocation" })
public class GeoLocationServlet extends SlingSafeMethodsServlet {

    /**
     ** UID
     */
    private static final long serialVersionUID = 2928204132562034369L;

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationServlet.class);

    @Override
    public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // String country = request.getHeader("cf-ipcountry");
        // String city = request.getHeader("cf-city");
        // String postalCode = request.getHeader("cf-zip-code");

        try {

            Enumeration<String> names = request.getHeaderNames();

            JsonObject jsonOutput = new JsonObject();
            JsonObject data = new JsonObject();

            while (names.hasMoreElements()) {
              String name = names.nextElement();
              String value = request.getHeader(name);
              data.addProperty(name, value);
            }

            // data.addProperty("country", country);
            // data.addProperty("city", city);
            // data.addProperty("postalCode", postalCode);
            jsonOutput.add("data", data);

            response.setStatus(SlingHttpServletResponse.SC_OK);
            response.getWriter().write(jsonOutput.toString());
            return;
        } catch (Exception e) {
            LOGGER.error("Can't build data object! Reason: {}", e.getMessage(), e);

            JsonObject jsonOutput = getErrorObject("500", "Internal server error");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonOutput.toString());
            return;
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
