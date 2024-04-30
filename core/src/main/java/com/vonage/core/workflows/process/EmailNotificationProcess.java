package com.vonage.core.workflows.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
// Sling APIs
import org.apache.sling.commons.osgi.PropertiesUtil;
// OSGi APIs
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
// Logger APIs
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.email.EmailService;
// Workflow APIs
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.Externalizer;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.utils.ServiceUtils;

/**
 * Vonage Email Notification Process
 *
 */
@Component(service = WorkflowProcess.class, property = { "process.label=Vonage - Email Notification Process" })
public class EmailNotificationProcess implements WorkflowProcess {

    /** default log. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationProcess.class);

    /**
     * Constant map, to map Unique IDs to Email templates
     */
    private static final Map<String, String> EMAIL_TEMPLATE_MAP = new HashMap<>();
    static {
        EMAIL_TEMPLATE_MAP.put("ActivateSuccess", "/etc/notification/email/vonage/activate-page-success.txt");
        EMAIL_TEMPLATE_MAP.put("ActivateInvalidContent",
                "/etc/notification/email/vonage/activate-page-error-invalid-content.txt");
        EMAIL_TEMPLATE_MAP.put("ActivateRequestRejected",
                "/etc/notification/email/vonage/activate-page-error-rejected.txt");
    }

    /**
     * resourceResolverFactory
     */
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    /**
     * emailService
     */
    @Reference
    private EmailService emailService;

    /**
     * externalizer
     */
    @Reference
    private Externalizer externalizer;

    @Override
    public final void execute(final WorkItem item, final WorkflowSession wfsession, final MetaDataMap args)
            throws WorkflowException {
        String processArg = args.get("PROCESS_ARGS", String.class);
        LOGGER.debug("workflow Started with Args: {}", processArg);
        try {
            ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resourceResolverFactory);
            UserManager userManager = resourceResolver.adaptTo(UserManager.class);
            Authorizable authorizable = userManager.getAuthorizable(item.getWorkflow().getInitiator());
            String userEmail = PropertiesUtil.toString(authorizable.getProperty(AppConstants.PROP_USER_EMAIL), "");
            String userName = PropertiesUtil.toString(authorizable.getProperty(AppConstants.PROP_USER_FIRST_NAME), "");
            if (userEmail == null || userEmail.isEmpty()) {
                throw new WorkflowException("User email address is not available for user: " + authorizable.getID()
                        + ". Cannot send email.");
            }

            /* build email */
            String emailTemplate = EMAIL_TEMPLATE_MAP.get(processArg);
            Map<String, String> emailParams = new HashMap<>();
            emailParams.put("recipientName", userName);
            emailParams.put("reviewComments", getReviewComments(item, wfsession));
            emailParams.put("payload",
                    externalizer.externalLink(resourceResolver, Externalizer.AUTHOR, item.getContentPath()) + ".html");
            String[] recipients = {userEmail};
            emailService.sendEmail(emailTemplate, emailParams, recipients);
        } catch (RepositoryException e) {
            throw new WorkflowException(e.getMessage(), e);
        }
        LOGGER.debug("Completed");
    }

    /**
     * Get Review Comments.
     *
     * @param item      Work Item.
     * @param wfsession Work Session.
     * @return reviewComments.
     * @throws WorkflowException Throws WorkflowException exception to suspend
     *                           execution.
     */
    private String getReviewComments(final WorkItem item, final WorkflowSession wfsession) throws WorkflowException {
        String reviewComments = null;
        final List<HistoryItem> history = wfsession.getHistory(item.getWorkflow());
        if (Boolean.FALSE.equals(history.isEmpty())) {
            HistoryItem lastWorkItem = history.get(history.size() - 1);
            reviewComments = PropertiesUtil.toString(lastWorkItem.getWorkItem().getMetaDataMap().get("comment"), "");
        }
        return reviewComments;
    }

}
