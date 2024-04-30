package com.vonage.core.services;

/**
 * Interface for ECommerce Config Service - ECommerceConfigServiceImpl.
 */
public interface ECommerceConfigService {

    /**
     * Attribute for B2B API base url.
     *
     * @return apiBaseUrl
     */
    String getApiBaseUrl();

    /**
     * Attribute for the B2B API Storefront Name
     *
     * @return storefrontName
     */
    String getStorefrontName();

    /**
     * Attribute for the B2B API Price Book
     *
     * @return priceBook
     */
    String getPriceBook();

    /**
     * Attribute for the Zuora page id
     *
     * @return zuoraPageId
     */
    String getZuoraPageId();

    /**
     * Attribute for the Zuora iframe url
     *
     * @return zuoraIFrameUrl
     */
    String getZuoraIFrameUrl();

    /**
     * Attribute for the Address validation API URL
     *
     * @return addressValidationUrl
     */
    String getAddressValidationUrl();

    /**
     * Attribute for the Address validation API user
     *
     * @return addressValidationUser
     */
    String getAddressValidationUser();

    /**
     * Attribute for the Address validation Password
     *
     * @return addressValidationPassword
     */
    String getAddressValidationPassword();

    /**
     * Attribute for the Safe tech url
     *
     * @return paymentTechUrl
     */
    String getPaymentTechUrl();

    /**
     * Attribute for the Safe tech merchant id
     *
     * @return Payment Tech Merchant Id
     */
    String getPaymentTechMerchantId();

    /**
     * Attribute for ezLynx Master Agent ID
     *
     * @return ezLynxMasterAgentId
     */
    String getEzLynxMasterAgentId();

    /**
     * Attribute for ezLynx Sub Agent ID
     *
     * @return ezLynxSubAgentId
     */
    String getEzLynxSubAgentId();
}
