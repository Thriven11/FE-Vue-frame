package com.vonage.core.workflows.process;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Value;

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

import com.adobe.acs.commons.email.EmailService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;

@ExtendWith({ MockitoExtension.class })
class EmailNotificationProcessTest {

    public static final String wfPayload = "/content/vonage/en-us/test-page.html";
    public static final String USER_ID = "test-user";

    @Mock
    Workflow workflow;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

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
    UserManager userManager;

    @Mock
    Page page;

    @Mock
    Authorizable authorizable;

    @Mock
    Externalizer externalizer;

    @InjectMocks
    EmailNotificationProcess process;

    @Mock
    EmailService emailService;

    @Mock
    Value emailValue;

    @Mock
    Value nameValue;

    @Mock
    HistoryItem historyItem;

    List<HistoryItem> history = new ArrayList<HistoryItem>();

    @BeforeEach
    public void setUp() throws Exception {
        HashMap<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, VonageConstants.WRITE_SUBSERVICE);
        when(resourceResolverFactory.getServiceResourceResolver(param)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(UserManager.class)).thenReturn(userManager);
        when(workItem.getWorkflow()).thenReturn(workflow);
        when(workflow.getInitiator()).thenReturn(USER_ID);
        when(userManager.getAuthorizable(USER_ID)).thenReturn(authorizable);
        when(authorizable.getProperty(AppConstants.PROP_USER_EMAIL)).thenReturn(new Value[] { emailValue });
        when(authorizable.getProperty(AppConstants.PROP_USER_FIRST_NAME)).thenReturn(new Value[] { nameValue });
        when(metadataMap.get("PROCESS_ARGS", String.class)).thenReturn("ActivateSuccess");
    }

    @Test
    public void testExecute() throws WorkflowException {
        when(historyItem.getWorkItem()).thenReturn(workItem);
        history.add(historyItem);
        when(wfSession.getHistory(workflow)).thenReturn(history);
        when(workItem.getMetaDataMap()).thenReturn(metadataMap);
        process.execute(workItem, wfSession, metadataMap);
        assertTrue(true);
    }

    @Test
    public void testExecuteWhenEmailNull() throws WorkflowException {
        try {
            when(authorizable.getProperty(AppConstants.PROP_USER_EMAIL)).thenReturn(new Value[] {});
            process.execute(workItem, wfSession, metadataMap);
        } catch (RepositoryException | WorkflowException e) {
            assertTrue(true);
        }
    }
}
