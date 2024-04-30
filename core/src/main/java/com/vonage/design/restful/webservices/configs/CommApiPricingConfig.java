package com.vonage.design.restful.webservices.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration for Communication API pricing Service.
 */
@ObjectClassDefinition(name = "Vonage - Communication API pricing Service",
        description = "Configuration of Communication API pricing Service")
public @interface CommApiPricingConfig {

    /**
     * Attribute for enabling/ disabling the API
     *
     * @return apiEnabled - true or false
     */
    @AttributeDefinition(name = "Enabled",
            description = "UnCheck to stop API calls to be send to the external API servers")
    boolean apiEnabled() default true;

    /**
     * Attribute for Communication API base URL
     *
     * @return apiUrl - API base URL
     */
    @AttributeDefinition(name = "API - Base URL",
            description = "Communication API base URL e.g. https://rest.nexmo.com")
    String apiUrl() default "https://rest.nexmo.com";

    /**
     * Attribute for Exchange API base URL
     *
     * @return exchangeApiUrl - API base URL with token
     */
    @AttributeDefinition(name = "Exchange API - Base URL with token",
            description = "Exchange API base URL e.g. http://data.fixer.io/api/latest?access_key={vonage-key}")
    String exchangeApiUrl();

}
