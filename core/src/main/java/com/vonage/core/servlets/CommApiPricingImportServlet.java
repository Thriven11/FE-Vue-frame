package com.vonage.core.servlets;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.PricingConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.services.PricingIngestionService;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.webservices.CommApiPricing;

/**
 * Servlet class
 *
 * @author Vonage
 *
 */
@Component(service = { Servlet.class },
        property = { SLING_SERVLET_RESOURCE_TYPES + "=vonage/servlets/commapi", SLING_SERVLET_METHODS + "=GET" })
public class CommApiPricingImportServlet extends SlingSafeMethodsServlet {

    /**
     * CommApiPricing Service
     */
    @Reference
    private transient CommApiPricing commApiService;

    /**
     * Ingestion Service
     */
    @Reference
    private transient PricingIngestionService ingestionService;

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -561754178433890187L;

    /**
     * Country CSV
     */
    private static final String COUNTRY_CSV = PricingConstants.COUNTRY_CSV;

    /**
     * Sleep for definite period in between multiple hits. Not a final variable
     * intentionally as its being modified in Junit
     */
    private static int waitTime = VonageConstants.THREAD_WAIT_TIME;

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommApiPricingImportServlet.class);

    /**
     * Add overrides for other SlingSafeMethodsServlet here (doGeneric, doHead,
     * doOptions, doTrace) *
     */
    @Override
    protected final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException {
        String suffix = request.getRequestPathInfo().getSuffix();
        if (null == suffix) {
            response.getWriter().write("API SUFFIX missing!");
            return;
        }
        try {
            String pagePath = request.getRequestPathInfo().getResourcePath().replace("/jcr:content", "");
            String path = pagePath.substring(pagePath.lastIndexOf('/'));
            JsonObject exchangeRateJson = commApiService.getExchangeRateJson(PricingConstants.PN_USD);
            if (null != exchangeRateJson && exchangeRateJson.has(PricingConstants.JSON_RATES)) {
                ingestionService.ingestRates(exchangeRateJson.get(PricingConstants.JSON_RATES).getAsJsonObject());
                response.getWriter().print("Rates: " + exchangeRateJson.get(PricingConstants.JSON_RATES));
            }
            if (suffix.indexOf("/all/") > -1) {
                String[] countries = COUNTRY_CSV.split(",");
                for (String countryCode : countries) {
                    String apiUrl = suffix.replace("/all/", AppConstants.SLASH + countryCode + AppConstants.SLASH);
                    boolean isSuccess = ingestionService.ingestPrice(commApiService.getPricingJson(path.concat(apiUrl)),
                            apiUrl.replace("/jsonp", ""));
                    response.getWriter()
                            .println("<br/>Executing ------------- " + apiUrl + ", isSuccess: " + isSuccess);
                    response.getWriter().flush();
                    Thread.sleep(waitTime);
                }
            } else {
                JsonObject object = commApiService.getPricingJson(path.concat(suffix));
                if (null != object) {
                    boolean isSuccess = ingestionService.ingestPrice(object, suffix.replace("/jsonp", ""));
                    response.getWriter().write(", isSuccess: " + isSuccess + "<br/>");
                    response.getWriter().write(object.toString());
                } else {
                    response.getWriter().write("Response is null!");
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (RestClientException | InterruptedException e) {
            LOGGER.error("Error while importing prices: {}", e.getMessage(), e);
        }
    }
}
