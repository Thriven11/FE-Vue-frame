package com.vonage.core.models.content.forms;

import com.vonage.core.services.AttributionService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * The servlet class for retrieving attribution.
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Get ReCaptcha Servlet",
  "sling.servlet.methods=" + HttpConstants.METHOD_GET,
   "sling.servlet.paths=" + "/bin/vonage/api/getRecaptchaResponseServlet" })
public class GetRecaptchaResponseServlet extends SlingSafeMethodsServlet {

//  /**
//   * UID
//   */
//  private static final long serialVersionUID = 2928204132562034369L;

  /**
   * Logger variable
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(GetRecaptchaResponseServlet.class);

  /**
   * AttributionService
   */
  @Reference
  private transient AttributionService attributionService;

  @Override
  public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
    throws ServletException, IOException {

    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");

    LOGGER.debug(":::::::::::::: GetRecaptchaResponseServlet GET() Starts :::::::::::::");
    String resp = "[]";
     String str = "";
     if (request.getParameter("token") != null) {
       str = request.getParameter("token");
    }
     String configUrl =
      "https://www.google.com/recaptcha/api/siteverify?secret=6Lf_6OsUAAAAAAexujJ_TVkBlZaIZ0k69RwkK6p_&response=" + str;
     try {
      LOGGER.debug("::: Params :: token:: " + str);
     if (str != null) {
         String baseUrl = configUrl;
         CloseableHttpClient client = null;
         HttpClientBuilder httpClientBuilder = null;
         httpClientBuilder = HttpClientBuilder.create();
         client = httpClientBuilder.build();
         HttpGet req = new HttpGet(baseUrl);
         HttpResponse response1 = client.execute(req);
         if (response1 != null && response1.getEntity() != null) {
            HttpEntity entity = response1.getEntity();
            resp = EntityUtils.toString(entity, "UTF-8");
         }

        LOGGER.info("Response::: " + resp);
     }
     } catch (Exception e) {
      LOGGER.error("Error in processing http request ::: ");
     }
    response.setContentType("application/json");
    response.getWriter().write(resp);
    LOGGER.debug(":::::::::::::: GetRecaptchaResponseServlet GET() Ends :::::::::::::");
    }
}

