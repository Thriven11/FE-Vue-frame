package com.vonage.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Zuora Callback",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.paths=" + "/bin/vonage/api/zuoracallback"
})
public class ZuoraCallbackServlet extends SlingAllMethodsServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6412914782665946968L;

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZuoraCallbackServlet.class);




    @Override
    public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
        throws ServletException, IOException {
      try {
        response.setStatus(SlingHttpServletResponse.SC_OK);
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<script>parent.postMessage(");
        out.println("{isPaymentSuccess:true,queryParams:window.location.search},");
        out.println("window.location.origin");
        out.println(");</script>");
        out.println("</body></html>");

      } catch (IOException e) {
        LOGGER.error("Can't build callback page! Reason: {}", e.getMessage(), e);
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
