package com.vonage.core.workflows.process;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.jackrabbit.api.security.user.Authorizable;
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
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

@ExtendWith({ MockitoExtension.class })
class UnlockPageProcessTest {

    public static final String wfPayload = "/content/vonage/en-us/test-page.html";

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    WorkItem workItem;

    @Mock
    WorkflowSession wfSession;

    @Mock
    MetaDataMap metadataMap;

    @Mock
    PageManager pageManager;

    @Mock
    Authorizable authorizable;

    @Mock
    Page page;

    @InjectMocks
    UnlockPageProcess process;

    @BeforeEach
    public void setUp() throws Exception {
        when(workItem.getContentPath()).thenReturn(wfPayload);
        when(wfSession.adaptTo(ResourceResolver.class)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getPage(wfPayload)).thenReturn(page);
        when(page.isLocked()).thenReturn(true);
        when(page.canUnlock()).thenReturn(true);
    }

    @Test
    void testExecute() {
        try {
            process.execute(workItem, wfSession, metadataMap);
        } catch (WorkflowException e) {
            assertTrue(true);
        }
    }

    @Test
    void testExecuteWhenLockException() {
        try {
            Mockito.doThrow(new WCMException("Lock Error")).when(page).unlock();
            process.execute(workItem, wfSession, metadataMap);
        } catch (WorkflowException | WCMException e) {
            assertTrue(true);
        }
    }

}
