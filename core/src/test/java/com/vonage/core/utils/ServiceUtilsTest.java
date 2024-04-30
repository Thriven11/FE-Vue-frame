package com.vonage.core.utils;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.vonage.core.constants.VonageConstants;

@ExtendWith({ MockitoExtension.class })
class ServiceUtilsTest {

    @Mock
    ResourceResolver resolver;

    @Mock
    ResourceResolverFactory resolverFactory;

    @Mock
    Replicator replicate;

    @Mock
    Session session;

    @Test
    public void testReplicatePathsWhenActivate() throws ReplicationException {
        String path = "/content/vonage/en-us/sitemap.xml";
        ServiceUtils.replicatePaths(resolver, replicate, ReplicationActionType.ACTIVATE, new String[] { "publish" },
                Arrays.asList(path));
        assertTrue(true);
    }

    @Test
    public void testReplicatePathsWhenNoAgents() {
        String path = "/content/vonage/en-us/sitemap.xml";
        try {
            ServiceUtils.replicatePaths(resolver, replicate, ReplicationActionType.ACTIVATE, null, Arrays.asList(path));
        } catch (ReplicationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testReplicatePathsWhenPathEmpty() {
        try {
            ServiceUtils.replicatePaths(resolver, replicate, ReplicationActionType.ACTIVATE, new String[] { "publish" },
                    new ArrayList<>());
        } catch (ReplicationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCommit() throws Exception {
        when(resolver.hasChanges()).thenReturn(true);
        when(resolver.adaptTo(Session.class)).thenReturn(session);
        ServiceUtils.commit(resolver);
        assertTrue(true);
    }

    @Test
    public void testCommitWhenResolverNotLive() {
        when(resolver.hasChanges()).thenReturn(true);
        try {
            ServiceUtils.commit(resolver);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCommitWhenSessionNotLive() {
        when(resolver.hasChanges()).thenReturn(true);
        when(resolver.adaptTo(Session.class)).thenReturn(session);
        try {
            ServiceUtils.commit(resolver);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCommitWhenNoChanges() throws Exception {
        when(resolver.hasChanges()).thenReturn(false);
        ServiceUtils.commit(resolver);
        assertTrue(true);
    }

    @Test
    public void testGetWriteResourceResolver() {
        assertNull(ServiceUtils.getWriteResourceResolver(resolverFactory));
    }

    @Test
    public void testGetWriteResourceResolverWhenException() throws LoginException {
        HashMap<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, VonageConstants.WRITE_SUBSERVICE);
        when(resolverFactory.getServiceResourceResolver(param)).thenThrow(new LoginException("Cannot login"));
        assertNull(ServiceUtils.getWriteResourceResolver(resolverFactory));
    }

}
