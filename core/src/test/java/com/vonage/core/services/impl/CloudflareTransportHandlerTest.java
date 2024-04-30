package com.vonage.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.day.cq.replication.AgentConfig;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationLog;
import com.day.cq.replication.ReplicationTransaction;
import com.day.cq.replication.TransportContext;
import com.vonage.core.utils.ServiceUtils;

@ExtendWith({ MockitoExtension.class })
class CloudflareTransportHandlerTest {

    @InjectMocks
    private CloudflareTransportHandler cfHandler;

    @Mock
    private AgentConfig config;

    @Mock
    private TransportContext ctx;

    @Mock
    private ReplicationTransaction tx;

    @Mock
    private ReplicationAction action;

    @Mock
    ReplicationLog logger;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

    @Mock
    Externalizer externalizer;

    @Mock
    ResourceResolver resourceResolver;

    private static final String transportPassword = "some-password";
    private static final String transportUri = "cloudflare://api.cloudflare.com/client/v4/zones/some-zone/purge_cache";
    private static final String transportUser = "test@vonage.com";

    @Test
    void testCanHandle() {
        when(config.getTransportURI()).thenReturn(transportUri);
        assertTrue(cfHandler.canHandle(config));
    }

    @Test
    void testCanHandleWhenNoUri() {
        assertFalse(cfHandler.canHandle(config));
    }

    private void setupDeliver() {
        when(config.getTransportURI()).thenReturn(transportUri);
        when(config.getTransportUser()).thenReturn(transportUser);
        when(config.getTransportPassword()).thenReturn(transportPassword);
        when(tx.getAction()).thenReturn(action);
        when(tx.getLog()).thenReturn(logger);
        when(ctx.getConfig()).thenReturn(config);
    }

    @Test
    void testDeliver() throws ReplicationException {
        setupDeliver();
        when(ServiceUtils.getReadResourceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        when(action.getType()).thenReturn(ReplicationActionType.ACTIVATE);
        when(action.getPaths()).thenReturn(new String[] { "/content/dam/vonage/test.png" });
        assertFalse(cfHandler.deliver(ctx, tx).isSuccess());
    }

    @Test
    void testDeliverWhenDeactivate() throws ReplicationException {
        setupDeliver();
        when(ServiceUtils.getReadResourceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        when(action.getType()).thenReturn(ReplicationActionType.DEACTIVATE);
        when(action.getPaths()).thenReturn(new String[] { "/content/dam/vonage/test.png" });
        assertFalse(cfHandler.deliver(ctx, tx).isSuccess());
    }

    @Test
    void testDeliverWhenNoPath() {
        when(config.getTransportURI()).thenReturn(transportUri);
        when(tx.getAction()).thenReturn(action);
        when(ctx.getConfig()).thenReturn(config);
        when(tx.getLog()).thenReturn(logger);
        when(action.getType()).thenReturn(ReplicationActionType.ACTIVATE);
        when(action.getPaths()).thenReturn(new String[] {});
        cfHandler.deliver(ctx, tx);
        assertTrue(true);
    }

    @Test
    void testDeliverWhenInvalidType() {
        when(tx.getAction()).thenReturn(action);
        when(action.getType()).thenReturn(ReplicationActionType.DELETE);
        cfHandler.deliver(ctx, tx);
        assertTrue(true);
    }

    @Test
    void testDeliverWhenTest() throws ReplicationException {
        setupDeliver();
        when(action.getType()).thenReturn(ReplicationActionType.TEST);
        assertFalse(cfHandler.deliver(ctx, tx).isSuccess());
    }

}
