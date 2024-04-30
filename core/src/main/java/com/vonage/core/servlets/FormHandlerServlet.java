package com.vonage.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

/**
 * The servlet class for form handler callbacks. It simply needs to response 200 OK
 * with as small a payload as possible.
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Get form handler callback response",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/vonage/api/form-handler" })
public class FormHandlerServlet extends SlingSafeMethodsServlet {

    @Override
    public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setStatus(SlingHttpServletResponse.SC_OK);
    }
}
