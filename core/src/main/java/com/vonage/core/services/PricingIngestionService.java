package com.vonage.core.services;

import com.google.gson.JsonObject;

/**
 * Ingestion Service
 *
 * @author Vonage
 */
public interface PricingIngestionService {

    /**
     * Method for ingesting prices
     *
     * @param pricingObject - Pricing JsonObject
     * @param relPath       - Relative Path in AEM
     *
     * @return true if successful
     */
    boolean ingestPrice(JsonObject pricingObject, String relPath);

    /**
     * Method for ingesting rates
     *
     * @param exchangeObject - Exchange Rate JsonObject
     * @return true if successful
     */
    boolean ingestRates(JsonObject exchangeObject);
}
