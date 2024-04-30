package com.vonage.core.services.impl;

import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.vonage.core.services.AppConfigService;
import com.vonage.core.services.AppServiceConfiguration;

/**
 * Service class used by Application for all global configurations
 *
 * @author Vonage
 *
 */
@Component(service = AppConfigService.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = AppServiceConfiguration.class)
public class AppConfigServiceImpl implements AppConfigService {

    /**
     * AppServiceConfiguration object
     */
    private AppServiceConfiguration appConfig;

    /**
     * Reference SlingSettingsService.
     */
    @Reference
    private SlingSettingsService settings;

    /**
     * Activate AppConfigServiceImpl.
     *
     * @param appConfigParam application default configs value
     */
    @Activate
    public final void activate(final AppServiceConfiguration appConfigParam) {
        this.appConfig = appConfigParam;
    }

    /**
     * Attribute for Adobe DTM URL.
     *
     * @return dtmURL
     */
    public final String getDtmUrl() {
        return appConfig.dtmUrl();
    }

    /**
     * Attribute for publish agents.
     *
     * @return publishAgents
     */
    @Override
    public final String[] getPublishAgents() {
        return appConfig.publishAgents();
    }

    @Override
    public final String[] getContentApproverMapping() {
        return appConfig.contentApproverMapping();
    }

}
