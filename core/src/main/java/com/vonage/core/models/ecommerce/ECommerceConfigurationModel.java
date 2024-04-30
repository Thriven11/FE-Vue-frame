package com.vonage.core.models.ecommerce;

import javax.inject.Inject;

import com.vonage.core.services.ECommerceConfigService;

import com.day.cq.wcm.api.Page;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * ECommerce Configuration Model class
 *
 * @author Vonage
 *
 */
@Model(adaptables = { Resource.class, Page.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ECommerceConfigurationModel {

    /**
     * Ecommerce Configuration Service
     */
    @Inject
    private ECommerceConfigService eCommerceConfigService;


    /**
     * Return the B2B API base url from the service configuration
     *
     * @return apiBaseUrl
     */
    public final String getApiBaseUrl() {
        return eCommerceConfigService.getApiBaseUrl();
    }

    /**
     * Return the B2B Storefront Name from the service configuration
     *
     * @return storefrontName
     */
    public final String getStorefrontName() {
      return eCommerceConfigService.getStorefrontName();
    }

    /**
     * Return the B2B Price Book from the service configuration
     *
     * @return priceBook
     */
    public final String getPriceBook() {
      return eCommerceConfigService.getPriceBook();
    }

    /**
     * Return the Zuora page id from the service configuration
     *
     * @return zuoraPageId
     */
    public final String getZuoraPageId() {
        return eCommerceConfigService.getZuoraPageId();
    }

    /**
     * Return the Zuora ifram URL from the service configuration
     *
     * @return zuoraIFrameUrl
     */
    public final String getZuoraIFrameUrl() {
        return eCommerceConfigService.getZuoraIFrameUrl();
    }

    /**
     * Return the Address validation API URL
     *
     * @return addressValidationUrl
     */
    public final String getAddressValidationUrl() {
        return eCommerceConfigService.getAddressValidationUrl();
    }

    /**
     * Return the Address validation API user
     *
     * @return addressValidationUser
     */
    public final String getAddressValidationUser() {
        return eCommerceConfigService.getAddressValidationUser();
    }

    /**
     * Return the Address validation Password
     *
     * @return addressValidationPassword
     */
    public final String getAddressValidationPassword() {
        return eCommerceConfigService.getAddressValidationPassword();
    }

    /**
     * Return the Safe Tech url
     *
     * @return paymentTechUrl
     */
    public final String getPaymentTechUrl() {
        return eCommerceConfigService.getPaymentTechUrl();
    }

    /**
     * Return the Safe Tech merchant id
     *
     * @return paymentTechMerchantId
     */
    public final String getPaymentTechMerchantId() {
        return eCommerceConfigService.getPaymentTechMerchantId();
    }

     /**
     * Return the ezLynx Master Agent ID
     *
     * @return ezLynxMasterAgentId
     */
    public final String getEzLynxMasterAgentId() {
        return eCommerceConfigService.getEzLynxMasterAgentId();
    }

     /**
     * Return the ezLynx Sub Agent ID
     *
     * @return ezLynxSubAgentId
     */
    public final String getEzLynxSubAgentId() {
        return eCommerceConfigService.getEzLynxSubAgentId();
    }
}
