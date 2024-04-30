package com.vonage.core.workflows.process;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

/**
 * Workflow payload Validation Process
 *
 */
@Component(service = WorkflowProcess.class, property = { "process.label=Vonage - Payload Validation Process" })
public class ValidatePayloadProcess implements WorkflowProcess {

    /** default log. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(ValidatePayloadProcess.class);

    /**
     * resourceResolverFactory
     */
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public final void execute(final WorkItem item, final WorkflowSession wfSession, final MetaDataMap args)
            throws WorkflowException {
        LOGGER.debug("Inside Execute");
        ResourceResolver resourceResolver = wfSession.adaptTo(ResourceResolver.class);
        Resource contentResource = resourceResolver.getResource(item.getContentPath());

        /* check if page is a valid CQ page */
        Boolean isValidPage = contentResource.getResourceType().equalsIgnoreCase(NameConstants.NT_PAGE)
                && (null != contentResource.getChild(JcrConstants.JCR_CONTENT));
        if (Boolean.FALSE.equals(isValidPage)) {
            item.getWorkflowData().getMetaDataMap().put("activateStatus", "invalid payload");
            throw new WorkflowException("Invalid Payload. Payload {} is not a page." + item.getContentPath());
        }

        item.getWorkflowData().getMetaDataMap().put("activateStatus", "valid");
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager != null) {
            Page payloadPage = pageManager.getPage(item.getContentPath());
            if (payloadPage != null && !payloadPage.isLocked()) {
                try {
                    payloadPage.lock();
                    LOGGER.debug("Page locked for workflow processing: {}", item.getContentPath());
                } catch (WCMException e) {
                    throw new WorkflowException(e.getMessage(), e);
                }
            }
        }
        LOGGER.debug("Process Completed");
    }

}
