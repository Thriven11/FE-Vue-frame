package com.vonage.core.workflows.process;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
// OSGi APIs
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
// Logger APIs
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Workflow APIs
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

/**
 * Vonage Unlock Page Process
 *
 */
@Component(service = WorkflowProcess.class, property = { "process.label=Vonage - Unlock Page Process" })
public class UnlockPageProcess implements WorkflowProcess {

    /** default log. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(UnlockPageProcess.class);

    /**
     * resourceResolverFactory
     */
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public final void execute(final WorkItem item, final WorkflowSession wfsession, final MetaDataMap args)
            throws WorkflowException {
        LOGGER.debug("Started");
        ResourceResolver resourceResolver = wfsession.adaptTo(ResourceResolver.class);
        if (resourceResolver != null) {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            if (pageManager != null) {
                Page payloadPage = pageManager.getPage(item.getContentPath());
                if (payloadPage != null && payloadPage.canUnlock() && payloadPage.isLocked()) {
                    try {
                        payloadPage.unlock();
                        LOGGER.info("Page unlocked after workflow processing: {}", item.getContentPath());
                    } catch (WCMException e) {
                        throw new WorkflowException(e.getMessage(), e);
                    }
                }
            }
        }

        LOGGER.debug("Completed");
    }
}
