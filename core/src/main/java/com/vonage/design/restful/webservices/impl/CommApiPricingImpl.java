package com.vonage.design.restful.webservices.impl;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vonage.core.constants.PricingConstants;
import com.vonage.design.restful.clients.BasicClient;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.models.Response;
import com.vonage.design.restful.webservices.CommApiPricing;
import com.vonage.design.restful.webservices.configs.CommApiPricingConfig;

/**
 * Implementation class for Communications API Pricing Connector
 *
 * @author Vonage
 *
 */
@Component(service = CommApiPricing.class)
@Designate(ocd = CommApiPricingConfig.class)
public class CommApiPricingImpl extends BasicClient implements CommApiPricing {

    /**
     * API URL
     */
    private String baseUrl;

    /**
     * is API calls Enabled
     */
    private boolean isEnabled;

    /**
     * exchangeApiUrl
     */
    private String exchangeApiUrl;

    /**
     * Activate method
     *
     * @param config configuration
     */
    @Activate
    @Modified
    protected final void activate(final CommApiPricingConfig config) {
        baseUrl = config.apiUrl().trim();
        isEnabled = config.apiEnabled();
        exchangeApiUrl = config.exchangeApiUrl().trim();
    }

    @Override
    public final JsonObject getPricingJson(final String apiUrl) throws RestClientException {
        JsonObject json = null;
        if (isEnabled && StringUtils.isNotEmpty(this.baseUrl)) {
            Response response = super.get(this.baseUrl.concat(apiUrl));
            if (null != response) {
                json = new Gson().fromJson(response.getBody().trim(), JsonObject.class);
            }
        }
        return json;
    }

    @Override
    public final JsonObject getExchangeRateJson(final String currencyCode) throws RestClientException {
        JsonObject json = null;
        if (isEnabled && StringUtils.isNotEmpty(this.baseUrl)) {
            Response response = super.get(
                    String.format(PricingConstants.FIXER_API_FORMAT, this.exchangeApiUrl, currencyCode));
            if (null != response) {
                json = new Gson().fromJson(response.getBody().trim(), JsonObject.class);
            }
        }
        return json;
    }
}
