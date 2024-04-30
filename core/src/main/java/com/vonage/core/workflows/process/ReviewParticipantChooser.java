package com.vonage.core.workflows.process;

import javax.jcr.RepositoryException;

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

// Workflow APIs
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.services.AppConfigService;
import com.vonage.core.utils.BasicUtils;
import com.vonage.core.utils.ServiceUtils;

/**
 * Vonage Review Participant Chooser
 *
 */
@Component(service = ParticipantStepChooser.class, property = { "chooser.label=Vonage Review Participant Chooser" })
public class ReviewParticipantChooser implements ParticipantStepChooser {

    /**
     * resourceResolverFactory
     */
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    /**
     * Vonage configuration service
     */
    @Reference
    private AppConfigService configService;

    /** default log. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(ReviewParticipantChooser.class);

    /**
     * Get Participant.
     *
     * @param workItem    Work Item.
     * @param wfSession   Workflow session object.
     * @param metaDataMap Metadata map object.
     * @return participant.
     * @throws WorkflowException Throws WorkflowException exception to suspend
     *                           execution.
     */
    public final String getParticipant(final WorkItem workItem, final WorkflowSession wfSession,
            final MetaDataMap metaDataMap) throws WorkflowException {
        String participant = VonageConstants.DEFAULT_REVIEW_GROUP;
        ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resourceResolverFactory);
        UserManager userManager = resourceResolver.adaptTo(UserManager.class);
        try {
            // get default reviewer principal from workflow process argument
            String participantArg = PropertiesUtil.toString(metaDataMap.get("PROCESS_ARGS", String.class), "");
            if (participantArg.isEmpty()) {
                // If argument is empty fetch the approver group from AppConfig mapping
                String payload = workItem.getContentPath();
                String[] approvalMapping = configService.getContentApproverMapping();
                if (null != approvalMapping && approvalMapping.length > 0) {
                    participantArg = getApproverGroup(approvalMapping, payload);
                    LOGGER.debug("Payload- {}, Approver group- {}. Assigning dynamically", payload, participantArg);
                }
            }

            // check if principal received is valid
            if (null != userManager.getAuthorizable(participantArg)) {
                participant = participantArg;
                LOGGER.debug("setting choosen participant to prinicipal set in workflow argument: {} ", participant);
            } else {
                LOGGER.error("Participant group not found : {}", participantArg);
            }
        } catch (RepositoryException e) {
            LOGGER.error("Error: {}", e.getMessage(), e);
        }
        return participant;
    }

    /**
     * Get the Workflow Approver Group
     *
     * @param approvalMapping approvalMapping
     * @param path            payload path
     * @return group id
     */
    private String getApproverGroup(final String[] approvalMapping, final String path) {
        for (String entry : approvalMapping) {
            String[] mapping = entry.split(AppConstants.COLON);
            if (mapping.length > 1 && BasicUtils.matchAPath(mapping[0], path)) {
                return mapping[1];
            }
        }
        return null;
    }

}
