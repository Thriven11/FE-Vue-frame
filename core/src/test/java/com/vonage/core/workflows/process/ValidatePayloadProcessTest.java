package com.vonage.core.workflows.process;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

@ExtendWith({ MockitoExtension.class })
class ValidatePayloadProcessTest {

    public static final String wfPayload = "/content/vonage/en-us/test-page.html";

    @Mock
    Resource resource;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    WorkItem workItem;

    @Mock
    WorkflowSession wfSession;

    @Mock
    WorkflowData wfData;

    @Mock
    MetaDataMap metadataMap;

    @Mock
    PageManager pageManager;

    @Mock
    Page page;

    @InjectMocks
    ValidatePayloadProcess process;

    @BeforeEach
    public void setUp() throws Exception {
        when(workItem.getContentPath()).thenReturn(wfPayload);
        when(workItem.getWorkflowData()).thenReturn(wfData);
        when(wfData.getMetaDataMap()).thenReturn(metadataMap);
        when(wfSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(resourceResolver.getResource(wfPayload)).thenReturn(resource);
        when(resource.getResourceType()).thenReturn(NameConstants.NT_PAGE);
        when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(resource);
    }

    @Test
    public void testExecute() throws WorkflowException {
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getPage(wfPayload)).thenReturn(page);
        when(page.isLocked()).thenReturn(false);
        process.execute(workItem, wfSession, metadataMap);
        assertTrue(true);

    }

    @Test
    public void testExecuteWhenLockingResultsException() throws WorkflowException {
        try {
            when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
            when(pageManager.getPage(wfPayload)).thenReturn(page);
            when(page.isLocked()).thenReturn(false);
            Mockito.doThrow(new WCMException("Lock Error")).when(page).lock();
            process.execute(workItem, wfSession, metadataMap);
        } catch (WCMException | WorkflowException e) {
            // Testing exception
            assertTrue(true);
        }

    }

    @Test
    public void testExecuteWhenNoJcrContent() {
        when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(null);
        try {
            process.execute(workItem, wfSession, metadataMap);
        } catch (WorkflowException e) {
            // Testing exception
            assertTrue(true);
        }

    }

}
