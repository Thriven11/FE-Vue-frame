package com.vonage.core.services;

/**
 * Interface for Application Config Service - AppConfigServiceImpl.
 */
public interface AppConfigService {

    /**
     * Attribute for Adobe DTM URL.
     *
     * @return dtmURL
     */
    String getDtmUrl();

    /**
     * Get list of publish agent IDs
     *
     * @return publishAgents
     */
    String[] getPublishAgents();

    /**
     * Get workflow "content path - approver group" mapping
     *
     * @return contentApproverMapping
     */
    String[] getContentApproverMapping();
}
