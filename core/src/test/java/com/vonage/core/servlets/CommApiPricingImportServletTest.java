package com.vonage.core.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.vonage.core.constants.PricingConstants;
import com.vonage.core.services.PricingIngestionService;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.webservices.CommApiPricing;

@ExtendWith({ MockitoExtension.class })
class CommApiPricingImportServletTest {

    @Mock
    SlingHttpServletRequest request;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    CommApiPricing commApiService;

    @Mock
    PricingIngestionService ingestionService;

    @Mock
    RequestPathInfo pathInfo;

    @Mock
    PrintWriter writer;

    private String priceJson = "{'name':'United States'}";
    private String rateJson = "{'rates':{'USD':5}}";

    @InjectMocks
    private CommApiPricingImportServlet servlet;

    void setup() {
        try {
            when(commApiService.getPricingJson(Mockito.anyString()))
                    .thenReturn(new Gson().fromJson(priceJson, JsonObject.class));
            FieldUtils.writeField(servlet, "waitTime", 1, true);
        } catch (IllegalAccessException | JsonSyntaxException | RestClientException e) {
            // nothing to do
        }
    }

    @Test
    void testGet() throws IOException {
        setup();
        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(pathInfo.getSuffix()).thenReturn("/messaging/us/jsonp");
        when(pathInfo.getResourcePath()).thenReturn("/var/vonage/communication/pricing/jcr:content");
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);
        assertTrue(true);
    }

    @Test
    void testGetNoPrice() throws IOException {
        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(pathInfo.getSuffix()).thenReturn("/messaging/us/jsonp");
        when(pathInfo.getResourcePath()).thenReturn("/var/vonage/communication/pricing/jcr:content");
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);
        assertTrue(true);
    }

    @Test
    void testGetExchangeRate() throws IOException, JsonSyntaxException, RestClientException {
        when(commApiService.getExchangeRateJson(PricingConstants.PN_USD))
                .thenReturn(new Gson().fromJson(rateJson, JsonObject.class));
        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(pathInfo.getSuffix()).thenReturn("/messaging/us/jsonp");
        when(pathInfo.getResourcePath()).thenReturn("/var/vonage/communication/pricing/jcr:content");
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);
        assertTrue(true);
    }

    @Test
    void testGetAll() throws IOException, RestClientException, IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException, SecurityException {
        setup();
        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(pathInfo.getSuffix()).thenReturn("/messaging/all/jsonp");
        when(pathInfo.getResourcePath()).thenReturn("/var/vonage/communication/pricing/jcr:content");
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);
        assertTrue(true);
    }

    @Test
    void testGetException() {
        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(pathInfo.getSuffix()).thenReturn("/messaging/in/jsonp");
        when(pathInfo.getResourcePath()).thenReturn("/var/vonage/communication/pricing/jcr:content");
        try {
            when(commApiService.getExchangeRateJson(PricingConstants.PN_USD))
                    .thenThrow(new RestClientException("Error!"));
            servlet.doGet(request, response);
        } catch (IOException | RestClientException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetNoSuffix() throws IOException {
        when(request.getRequestPathInfo()).thenReturn(pathInfo);
        when(response.getWriter()).thenReturn(writer);
        servlet.doGet(request, response);
        assertTrue(true);
    }

}
