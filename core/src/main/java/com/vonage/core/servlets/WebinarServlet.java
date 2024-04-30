package com.vonage.core.servlets;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * The servlet class for adding registrant to On24 webinar. It simply needs to response 200 OK
 * with as small a payload as possible.
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class,
property = {
    "description=Submit form data to On24",
    "sling.servlet.methods=" + HttpConstants.METHOD_POST,
    "sling.servlet.paths=" + "/bin/vonage/api/webinar-registration"
})
@Designate(ocd = WebinarServlet.WebinarConfiguration.class)
public class WebinarServlet extends SlingAllMethodsServlet {

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebinarServlet.class);

     /**
     * Webinar on24 Config
     */
    private static WebinarConfiguration on24Config;

    /**
     * Config for Webinar Servlet
     */
    @ObjectClassDefinition(name = "Webinar On24 Configuration")
    public @interface WebinarConfiguration {

        /**
         * Config for Webinar Servlet
         * @return secret access key
         */
        @AttributeDefinition(name = "Access Token Key", description = "Access Token for ON 24")
        String accessKey();
         /**
         * Config for Webinar Servlet
         * @return secret access key
         */
        @AttributeDefinition(name = "Secret Access Token Key", description = "Secret Access Token for ON 24")
        String secretAccessKey();
         /**
         * Config for Webinar Servlet
         * @return secret access key
         */
        @AttributeDefinition(name = "On24 Virtual Env API Key", description = "API Key for Virtual Env registration")
        String on24VeApiKey();
        /**
         * Config for Webinar Servlet
         * @return on24 Url
         */
        @AttributeDefinition(name = "On24 VE URL ", description = "Endpoint of On24 VE that we post data to")
        String on24VeUrl();
         /**
         * Config for Webinar Servlet
         * @return on24 Url
         */
        @AttributeDefinition(name = "On24 URL ", description = "Endpoint of On24 that we post data to")
        String on24Url();
         /**
         * Config for Webinar Servlet
         * @return client ID
         */
        @AttributeDefinition(name = "Client ID", description = "Client ID for On24")
        String clientID();
    }

    /**
     * Config for Webinar Servlet
     * @param config webinar config
    */
    @Activate
    @Modified
    protected final void activate(final WebinarConfiguration config) {
        on24Config = config;
    }

    @Override
    public final void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse responseOut)
            throws ServletException, IOException {

        final String instance               = request.getParameter("eventinstance");
        final String webinarType            = request.getParameter("webinarType");
        final String accessTokenKey         = on24Config.accessKey();
        final String accessTokenSecret      = on24Config.secretAccessKey();
        final String clientId               = on24Config.clientID();
        final String eventId                = request.getParameter("eventid");
        final String showCode               = request.getParameter("showCode");
        final String url                    = MessageFormat.format(on24Config.on24Url(), clientId, eventId);
        final String on24VeUrl              = MessageFormat.format(on24Config.on24VeUrl(), showCode);
        // final boolean isWebcast             = StringUtils.equalsIgnoreCase(webinarType, "webcast");
        final boolean isVirtualEnv          = StringUtils.equalsIgnoreCase(webinarType, "virtualEvent");

        responseOut.setCharacterEncoding("UTF-8");
        responseOut.setContentType("application/json");

            // START POST
            List<String> paramList = getPayload(request);
            final String payload = String.join("&", paramList);
            String httpUrl = null;
            if (isVirtualEnv) {
                httpUrl = on24VeUrl;
            } else {
                httpUrl = url;
            }
            final URL obj = new URL(httpUrl);
            final HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("accept", "application/json");
            if (!isVirtualEnv) {
                httpURLConnection.setRequestProperty("accessTokenKey", accessTokenKey);
                httpURLConnection.setRequestProperty("accessTokenSecret", accessTokenSecret);
            }
            httpURLConnection.setDoOutput(true);

            final OutputStream os = httpURLConnection.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();
            final int responseCode = httpURLConnection.getResponseCode();

            LOGGER.info("instance: " + instance);
            LOGGER.info("webinarType: " + webinarType);
            LOGGER.info("url: " + httpUrl);
            LOGGER.info("payload: " + payload);
            LOGGER.info("accessTokenKey: " + accessTokenKey);
            LOGGER.info("accessTokenSecret: " + accessTokenSecret);
            LOGGER.info("clientId: " + clientId);
            LOGGER.info("eventId: " + eventId);
            LOGGER.info("responseCode: " + responseCode);
            LOGGER.info("");
            if (
                responseCode == HttpURLConnection.HTTP_OK
                || responseCode == HttpURLConnection.HTTP_CREATED
            ) { // success
                final BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                final StringBuffer responseIn = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    responseIn.append(inputLine);
                } in .close();

                LOGGER.info("POST request succeeded");
                LOGGER.info(responseIn.toString());
                // responseOut.getWriter().write(responseIn.toString());
                responseOut.getWriter().write("{status: 'succeeded'}");
            } else {
                LOGGER.error("Webinar on24 POST request failed");
                LOGGER.error("instance: " + instance);
                LOGGER.error("webinarType: " + webinarType);
                LOGGER.error("url: " + httpUrl);
                LOGGER.error("payload: " + payload);
                LOGGER.error("accessTokenKey: " + accessTokenKey);
                LOGGER.error("accessTokenSecret: " + accessTokenSecret);
                LOGGER.error("clientId: " + clientId);
                LOGGER.error("eventId: " + eventId);
                LOGGER.error("responseCode: " + responseCode);
                LOGGER.error("");
                responseOut.getWriter().write("{status: 'failed', responseCode: " + responseCode + " }");
            }
        // END POST
    }

    /**
     * getPayLoad for On24 registration
     * @param request SlingHttpServletRequest object
     * @return paramList  payload with list of all params
    */
    private List<String> getPayload(final SlingHttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<String, String>();
        // Full list of available On24 fields
        // key=On24 name, value=Voange field names (if exists)
        paramMap.put("firstname",            "firstname");
        paramMap.put("lastname",             "lastname");
        paramMap.put("email",                "email");
        paramMap.put("username",             null);
        paramMap.put("exteventusercd",       null);
        paramMap.put("company",              "companyname");
        paramMap.put("jobtitle",             "jobtitle");
        paramMap.put("jobfunction",          null);
        paramMap.put("addressstreet1",       null);
        paramMap.put("addressstreet2",       null);
        paramMap.put("city",                 "city");
        paramMap.put("state",                "state");
        paramMap.put("zip",                  "zipcode");
        paramMap.put("country",              "country"); // Full country name. No abbreviations
        // paramMap.put("workphone",            "phonenumber");
        // paramMap.put("homephone",            null);
        paramMap.put("companyindustry",      "industry");
        paramMap.put("companysize",          "numberofemployees");
        paramMap.put("eventemail",           null);
        paramMap.put("marketingemail",       "privacypolicyoptin");
        paramMap.put("partnerref",           null);
        paramMap.put("std1",                 null);
        paramMap.put("std2",                 null);
        paramMap.put("std3",                 null);
        paramMap.put("std4",                 null);
        paramMap.put("std5",                 null);
        paramMap.put("std6",                 null);
        paramMap.put("std7",                 null);
        paramMap.put("std8",                 null);
        paramMap.put("std9",                 null);
        paramMap.put("std10",                null);

        List<String> paramList = new ArrayList<String>();

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            final String key = entry.getKey();
            String value = request.getParameter(entry.getValue());

            if (key.equals("marketingemail") || key.equals("marketingemail")) {
                if (value == null) {
                    value = "N";
                } else {
                    value = "Y";
                }
            }

            if (value == null) {
                continue;
            }

            if (key.equals("workphone") || key.equals("homephone")) {
                value = value.replaceAll("[^0-9]", "");
            }

            paramList.add(key + "=" + value);

            if (StringUtils.equalsIgnoreCase(request.getParameter("webinarType"), "virtualEvent")) {
                paramList.add("apiKey=" + on24Config.on24VeApiKey());
                paramList.add("apiMode=Y");
            }

        }

        return paramList;
    }
}
