package com.vonage.design.restful.webservices;

import com.google.gson.JsonObject;
import com.vonage.design.restful.exceptions.RestClientException;

/**
 * Base class to Communications API
 *
 * @author Vonage
 *
 */
public interface CommApiPricing {

    /**
     * Get Json response from Communications API Pricing Service
     *
     * @param apiSuffix - of format "/messaging/us/jsonp"
     * @return JsonObject - response object
     * @throws RestClientException - Exception
     */
    JsonObject getPricingJson(String apiSuffix) throws RestClientException;

    /**
     * Get the json response from the fixer API. Example URL -
     * http://data.fixer.io/api/latest?access_key=some_api_key&symbols=USD
     *
     * @param currencyCode - multiple values separated by comma
     * @return json - the API response
     * @throws RestClientException - Exception
     */
    JsonObject getExchangeRateJson(String currencyCode) throws RestClientException;

}
