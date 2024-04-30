package com.vonage.core.services.impl;

import com.vonage.core.services.ECommerceConfigService;
import com.vonage.core.services.EcommerceServiceConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;


/**
 * Service class used for all E Commerce Configuration
 */
@Component(service = ECommerceConfigService.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = EcommerceServiceConfiguration.class)
public class ECommerceConfigServiceImpl implements ECommerceConfigService {

    /**
     * AppServiceConfiguration object
     */
    private EcommerceServiceConfiguration eCommerceConfig;

    /**
     * Activate ECommerceConfigServiceImpl.
     *
     * @param eCommerceConfigParam application default configs value
     */
    @Activate
    public final void activate(final EcommerceServiceConfiguration eCommerceConfigParam) {
        this.eCommerceConfig = eCommerceConfigParam;
    }

    /**
     * Returns the B2B API Base URL
     */
    @Override
    public final String getApiBaseUrl() {
        return eCommerceConfig.apiBaseUrl();
    }

    /**
     * Returns the B2B API Storefront Name
     */
    @Override
    public final String getStorefrontName() {
        return eCommerceConfig.storefrontName();
    }

    /**
     * Returns the B2B API Price Book
     */
    @Override
    public final String getPriceBook() {
        return eCommerceConfig.priceBook();
    }

    /**
     * Returns the Zuora Page Id
     */
    @Override
    public final String getZuoraPageId() {
        return eCommerceConfig.zuoraPageId();
    }

    /**
     * Returns the Address validation API URL
     */
    @Override
    public final String getAddressValidationUrl() {
        return eCommerceConfig.addressValidationUrl();
    }

    /**
     * Returns the Address validation API user
     */
    @Override
    public final String getAddressValidationUser() {
        return eCommerceConfig.addressValidationUser();
    }

    /**
     * Returns the Address validation API password
     */
    @Override
    public final String getAddressValidationPassword() {
        return eCommerceConfig.addressValidationPassword();
    }

    /**
     * Returns the Zuora IFrame URL
     */
    @Override
    public final String getZuoraIFrameUrl() {
        return eCommerceConfig.zuoraIFrameUrl();
    }

    /**
     * Returns the Safe tech url
     */
    @Override
    public final String getPaymentTechUrl() {
        return eCommerceConfig.paymentTechUrl();
    }

    /**
     * Returns the Safe Tech merchant idl
     */
    @Override
    public final String getPaymentTechMerchantId() {
        return eCommerceConfig.paymentTechMerchantId();
    }

    /**
     * Return the ezLynx Master Agent ID
     */
    @Override
    public final String getEzLynxMasterAgentId() {
        return eCommerceConfig.ezLynxMasterAgentId();
    }

     /**
     * Return the ezLynx Sub Agent ID
     *
     * @return ezLynxSubAgentId
     */
    @Override
    public final String getEzLynxSubAgentId() {
        return eCommerceConfig.ezLynxSubAgentId();
    }
}
