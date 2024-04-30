package com.vonage.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration Service for the Vonage web application.
 */

@ObjectClassDefinition(name = "Vonage - I18N Pricing Path Configuration", description = "Service Configuration")
public @interface I18NCountryDropdownConfiguration {

    /**
     * Attribute for I18N Pricing Path.
     *
     * @return I18NPricingPath
     */
    @AttributeDefinition(name = "I18N Pricing Path", description = "Attribute for I18N Pricing Path")
    String I18NPricingPath() default "/content/vonage/i18n/pricing";

}
