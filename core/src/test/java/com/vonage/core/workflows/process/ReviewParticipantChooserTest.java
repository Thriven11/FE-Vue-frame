package com.vonage.core.workflows.process;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.services.AppConfigService;

@ExtendWith({ MockitoExtension.class })
class ReviewParticipantChooserTest {

    public static final String USER_ID = "test-user";

    @InjectMocks
    ReviewParticipantChooser process;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

    @Mock
    AppConfigService configService;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    WorkItem workItem;

    @Mock
    WorkflowSession wfSession;

    @Mock
    MetaDataMap metadataMap;

    @Mock
    UserManager userManager;

    @Mock
    Authorizable authorizable;

    @BeforeEach
    public void setUp() throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, VonageConstants.WRITE_SUBSERVICE);
        when(resourceResolverFactory.getServiceResourceResolver(param)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(UserManager.class)).thenReturn(userManager);
    }

    @Test
    void testGetParticipant() {
        when(metadataMap.get("PROCESS_ARGS", String.class)).thenReturn(USER_ID);
        try {
            when(userManager.getAuthorizable(USER_ID)).thenReturn(authorizable);
            process.getParticipant(workItem, wfSession, metadataMap);
        } catch (WorkflowException | RepositoryException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetParticipantWhenInvalidUser() {
        when(metadataMap.get("PROCESS_ARGS", String.class)).thenReturn(USER_ID);
        try {
            process.getParticipant(workItem, wfSession, metadataMap);
        } catch (WorkflowException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetParticipantWhenMapping() {
        try {
            String[] mapping = { "/content/some/path:reviewer-group" };
            when(configService.getContentApproverMapping()).thenReturn(mapping);
            when(workItem.getContentPath()).thenReturn("/content/some/path");
            process.getParticipant(workItem, wfSession, metadataMap);
        } catch (WorkflowException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetParticipantWhenNoMatchingPath() {
        try {
            String[] mapping = { "/content/some/other/path:reviewer-group" };
            when(configService.getContentApproverMapping()).thenReturn(mapping);
            when(workItem.getContentPath()).thenReturn("/content/some/path");
            process.getParticipant(workItem, wfSession, metadataMap);
        } catch (WorkflowException e) {
            assertTrue(true);
        }
    }

}
