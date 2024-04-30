package com.vonage.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vonage.core.constants.PricingConstants;
import com.vonage.core.utils.ServiceUtils;

@ExtendWith({ MockitoExtension.class })
class PricingIngestionServiceImplTest {

    @Mock
    private ResourceResolverFactory resolverFactory;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Session session;

    @Mock
    Node node;

    @Mock
    private Resource resource;

    private String voicePriceJson = "{\"country\":\"US\",\"name\":\"United States\",\"dialingPrefix\":\"1\",\"voice\":"
            + "{\"outbound\":{\"currency\":\"EUR\",\"flatPrice\":\"0.01270000\"},\"inbound\":{\"numbers\":"
            + "[{\"monthlyFee\":\"0.90\",\"features\":\"SMS,VOICE\",\"type\":\"mobile\",\"messagingTraffic\":"
            + "\"0.00570000\",\"voiceTraffic\":\"0.00450000\",\"currency\":\"EUR\"},{\"monthlyFee\":\"0.90\","
            + "\"features\":\"SMS,VOICE\",\"type\":\"landline\",\"messagingTraffic\":\"0.00570000\",\"voiceTraffic\":"
            + "\"0.00450000\",\"currency\":\"EUR\"},{\"monthlyFee\":\"0.67\",\"features\":\"VOICE\",\"type\":"
            + "\"tollfree\",\"voiceTraffic\":\"0.01400000\",\"currency\":\"EUR\"},{\"monthlyFee\":\"1.00\","
            + "\"initialFee\":\"1.00\",\"features\":\"SMS,VOICE\",\"type\":\"tollfree\",\"messagingTraffic\":"
            + "\"0.00570000\",\"voiceTraffic\":\"0.01400000\",\"currency\":\"EUR\"}]}}}";
    private String messageJson = "{\"country\":\"IN\",\"name\":\"India\",\"dialingPrefix\":\"91\",\"messaging\":"
            + "{\"outbound\":{\"currency\":\"EUR\",\"flatMobilePrice\":\"0.01550000\"},\"inbound\":{\"numbers\":"
            + "[{\"monthlyFee\":\"100.00\",\"features\":\"SMS\",\"type\":\"mobile\",\"messagingTraffic\":"
            + "\"0.00250000\",\"currency\":\"EUR\"},{\"monthlyFee\":\"100.00\",\"features\":\"SMS\",\"type\":"
            + "\"tollfree\",\"messagingTraffic\":\"0.00250000\",\"currency\":\"EUR\"}]}}}";

    private String verifyJson = "{\"country\":\"DE\",\"name\":\"Germany\",\"dialingPrefix\":\"49\",\"verify\":"
            + "{\"currency\":\"EUR\",\"flatPrice\":\"0.05000000\",\"flatPushPrice\":\"0.05000000\","
            + "\"flatFailPrice\":" + "\"0\"}}";

    private String rateJson = "{'USD':5}";

    private static final String voicePath = "/voice/US/jsonp";
    private static final String messagePath = "/messaging/US/jsonp";
    private static final String verifyPath = "/verify/DE/jsonp";

    @InjectMocks
    PricingIngestionServiceImpl ingestionService;

    @Test
    void testIngestVoicePrice() throws IOException, RepositoryException {
        when(ServiceUtils.getWriteResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.itemExists(PricingConstants.PRODUCTS_BASE_PATH)).thenReturn(true);
        when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
        when(session.getNode(Mockito.anyString())).thenReturn(node);
        when(node.getNode(Mockito.anyString())).thenReturn(node);
        when(node.getPath()).thenReturn(PricingConstants.PRODUCTS_BASE_PATH + voicePath);
        ingestionService.ingestPrice(new Gson().fromJson(voicePriceJson, JsonObject.class), voicePath);
        assertTrue(true);
    }

    @Test
    void testIngestMessagingPrice() throws IOException, RepositoryException {
        when(ServiceUtils.getWriteResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.itemExists(PricingConstants.PRODUCTS_BASE_PATH)).thenReturn(true);
        when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
        when(session.getNode(Mockito.anyString())).thenReturn(node);
        when(node.getNode(Mockito.anyString())).thenReturn(node);
        when(node.getPath()).thenReturn(PricingConstants.PRODUCTS_BASE_PATH + messagePath);
        ingestionService.ingestPrice(new Gson().fromJson(messageJson, JsonObject.class), messagePath);
        assertTrue(true);
    }

    @Test
    void testIngestVerifyPrice() throws IOException, RepositoryException {
        when(ServiceUtils.getWriteResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.itemExists(PricingConstants.PRODUCTS_BASE_PATH)).thenReturn(true);
        when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
        when(session.getNode(Mockito.anyString())).thenReturn(node);
        when(node.getPath()).thenReturn(PricingConstants.PRODUCTS_BASE_PATH + verifyPath);
        ingestionService.ingestPrice(new Gson().fromJson(verifyJson, JsonObject.class), verifyPath);
        assertTrue(true);
    }

    @Test
    void testGetExcangeRate() throws IOException, RepositoryException {
        when(ServiceUtils.getWriteResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.itemExists(PricingConstants.PRODUCTS_BASE_PATH)).thenReturn(true);
        when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
        when(session.getNode(Mockito.anyString())).thenReturn(node);
        when(node.getPath()).thenReturn(PricingConstants.PRODUCTS_BASE_PATH);
        assertTrue(ingestionService.ingestRates(new Gson().fromJson(rateJson, JsonObject.class)));
    }

}
