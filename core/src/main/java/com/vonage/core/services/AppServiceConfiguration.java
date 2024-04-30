package com.vonage.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration Service for the Vonage web application.
 */

@ObjectClassDefinition(name = "Vonage - Application Configuration", description = "Service Configuration")
public @interface AppServiceConfiguration {

    /**
     * Attribute for Adobe DTM URL.
     *
     * @return dtmURL
     */
    @AttributeDefinition(name = "Dtm URL", description = "Adobe DTM Integration JS URL")
    String dtmUrl();

    /**
     * Attribute for publishAgents used for publishing content through code.
     *
     * @return publishAgents
     */
    @AttributeDefinition(name = "Publish Agents",
            description = "List of all publish agent IDs for the author environment")
    String[] publishAgents();

    /**
     * Attribute for contentApproverMapping used for content approval workflow.
     *
     * @return contentApproverMapping
     */
    @AttributeDefinition(name = "Content-Approver Mapping",
            description = "Contet path - workflow approver group mapping separated by colon (:) character.")
    String[] contentApproverMapping();

}
