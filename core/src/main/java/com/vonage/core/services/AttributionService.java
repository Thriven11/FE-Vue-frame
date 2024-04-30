package com.vonage.core.services;

import java.io.IOException;
import java.util.HashMap;

/**
 * Attribution Service
 *
 * @author Vonage
 */
public interface AttributionService {

    /**
     * Abstract method to get a campaign
     *
     * @param name - The campaign name
     * @return campaign - The campaign info
     * @throws IOException - IOException
     */
    HashMap<String, String> getCampaign(String name) throws IOException;
}
