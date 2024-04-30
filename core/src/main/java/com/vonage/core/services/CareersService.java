package com.vonage.core.services;

import java.util.Map;

import com.google.gson.JsonObject;

import org.apache.sling.api.resource.Resource;

/**
 * Base class to reading careers data from JCR
 *
 * @author Vonage
 *
 */
public interface CareersService {

    /**
     * @return HashMap List of unique departments From JCR
     */
    Map<String, String> getDepartments();

    /**
     * @return HashMap List of unique offices from JCR
     */
    Map<String, String> getOffices();

    /**
     * @param jobID jobID
     * @return Resource List of unique offices from JCR
     */
    Resource getJobResource(String jobID);

    /**
     * @param jobID jobID
     * @return Resource List of unique offices from JCR
     */
    JsonObject getJobResourceAsJsonObject(String jobID);
}


