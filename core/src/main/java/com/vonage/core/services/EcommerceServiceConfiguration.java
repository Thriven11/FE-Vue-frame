package com.vonage.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration Service for the Vonage E Commerce pages.
 */

@ObjectClassDefinition(name = "Vonage - E-Commerce Configuration", description = "Service Configuration")
public @interface EcommerceServiceConfiguration {

    /**
     * Attribute for B2B API base url.
     *
     * @return apiBaseUrl
     */
    @AttributeDefinition(name = "B2B API Url", description = "B2B API base Url")
    String apiBaseUrl();

    /**
     * Attribute for B2B API Storefront Name.
     *
     * @return storefrontName
     */
    @AttributeDefinition(name = "B2B API Storefront Name", description = "B2B API Storefront Name")
    String storefrontName();

    /**
     * Attribute for B2B API Price Book.
     *
     * @return priceBook
     */
    @AttributeDefinition(name = "B2B API Price Book", description = "B2B API Price Book")
    String priceBook();

    /**
     * Attribute for the Zuora page id
     *
     * @return zuoraPageId
     */
    @AttributeDefinition(name = "Zuora Page Id", description = "Zuora Page Id")
    String zuoraPageId();

    /**
     * Attribute for the Zuora iframe url      *
     * @return zuoraIFrameUrl
     */
    @AttributeDefinition(name = "Zuora iframe URL", description = "Zuora iframe URL")
    String zuoraIFrameUrl();

    /**
     * Attribute for the Address validation API URL
     *
     * @return addressValidationUrl
     */
    @AttributeDefinition(name = "Address validation URL", description = "Address validation API URL")
    String addressValidationUrl();

    /**
     * Attribute for the Address validation API User
     *
     * @return addressValidationUser
     */
    @AttributeDefinition(name = "Address validation user", description = "Address validation API username")
    String addressValidationUser();

    /**
     * Attribute for the Address validation API Password
     *
     * @return addressValidationPassword
     */
    @AttributeDefinition(name = "Address validation password", description = "Address validation API password")
    String addressValidationPassword();

    /**
     * Attribute for the Safetech URL
     *
     * @return paymentTechUrl
     */
    @AttributeDefinition(name = "Safetech url", description = "Safetech url")
    String paymentTechUrl();

    /**
     * Attribute for the Safetech Merchant ID
     *
     * @return addressValidatpaymentTechMerchantIdionPassword
     */
    @AttributeDefinition(name = "Safetech Merchant ID", description = "Safetech Merchant ID")
    String paymentTechMerchantId();

    /**
     * Attribute for ezLynx Master Agent ID
     *
     * @return ezLynxMasterAgentId
     */
    @AttributeDefinition(name = "ezLynx Master Agent ID", description = "EzLynx master agent id")
    String ezLynxMasterAgentId();

    /**
     * Attribute for ezLynx Sub Agent ID
     *
     * @return ezLynxSubAgentId
     */
    @AttributeDefinition(name = "ezLynx Sub Agent ID", description = "EzLynx sub agent id")
    String ezLynxSubAgentId();


}
