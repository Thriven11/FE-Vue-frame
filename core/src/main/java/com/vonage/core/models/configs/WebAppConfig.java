package com.vonage.core.models.configs;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;

import com.vonage.core.beans.config.AppConfig;
import com.vonage.core.beans.config.EnvConfig;
import com.vonage.core.services.AppConfigService;
import com.vonage.core.services.OneTrustService;

/**
 * Sling Model for WebAppConfig
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class WebAppConfig {

    /**
     * The Constant LOGGER.
     */

    public static final Logger LOGGER = LoggerFactory.getLogger(WebAppConfig.class);

    /**
     * currentPage variable
     */
    @Inject
    private Page currentPage;

    /**
     * resource resolver
     */
    @Inject
    private ResourceResolver resourceResolver;

    /**
     * The variable envConfig.
     */
    private EnvConfig envConfig;

    /**
     * The variable appConfig.
     */
    private AppConfig appConfig;

    /**
     * The variable loadingXF.
     */
    private String loadingXF;

    /**
     * Sling Settings Service
     *
     * @return SlingSettingsService
     */
    @Inject
    private SlingSettingsService settings;

    /**
     * Application Config Service
     *
     * @return AppConfigService
     */
    @Inject
    private AppConfigService appConfigService;


  /**
   * Careers Service
   */
    @OSGiService
    private OneTrustService oneTrustService;

     /**
     * SlingHttpServletRequest
     */
    @Inject
    private SlingHttpServletRequest request;

       /**
     * OneTrust
     */
    private String oneTrustCodeFromService;

    /**
     * Init Method of Model.
     */
    @PostConstruct
    protected final void init() {
        this.getOneTrustCode();
        this.setAppConfig();
        this.setEnvConfig();
        this.setLoadingXF();

    }

        /**
     * Method to obtain lcoale specific code for one trust
     */
    private void getOneTrustCode() {
      String countryCode = currentPage.getLanguage().getCountry().toLowerCase();
      String languageCode = currentPage.getLanguage().getLanguage().toLowerCase();
      String locale = countryCode + "-" + languageCode;
        oneTrustCodeFromService = oneTrustService.getCodePerLocale(locale);

      }

    /**
     * @return envConfig
     */

    public final EnvConfig getEnvConfig() {
        return this.envConfig;
    }

    /**
     * @return appConfig
     */
    public final AppConfig getAppConfig() {
        return this.appConfig;
    }

    /**
     * @return loadingXF
     */
    public final String getLoadingXF() {
        return this.loadingXF;
    }

    /**
     * Set EnvConfig
     */
    public final void setEnvConfig() {

        String runmodes = settings.getRunModes().toString();
        EnvConfig envConfigObj = new EnvConfig();
        envConfigObj.setRunModes(runmodes);
        envConfigObj.setExactRunMode(this.calculateRunMode(runmodes));
        envConfigObj.setAemMode(this.calculateAemMode(runmodes));
        this.envConfig = envConfigObj;
    }

    /**
     * Set AppConfig
     */
    public final void setAppConfig() {
        AppConfig appConfigObj = new AppConfig();
        appConfigObj.setDtmUrl(appConfigService.getDtmUrl());
        this.appConfig = appConfigObj;
    }

    /**
     * Set loadingXF
     */
    public final void setLoadingXF() {
        InheritanceValueMap inheritedProp = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
        String xfPath = inheritedProp.getInherited("loadingXF", String.class);
        if (StringUtils.isNotEmpty(xfPath)) {
            Resource resource = resourceResolver.getResource(xfPath);
            if (resource != null) {
                this.loadingXF = xfPath + "/jcr:content/root";
            }
        }
    }

    /**
     * @return runmode calculate exact run mode based on env.
     * @param runmodes pass actual runmode to clean up and set exact runmode
     */
    public final String calculateRunMode(final String runmodes) {
        String runmode = "local";
        if (StringUtils.containsIgnoreCase(runmodes, "prod")) {
            runmode = "prod";
        } else if (StringUtils.containsIgnoreCase(runmodes, "stage")) {
            runmode = "stage";
        } else if (StringUtils.containsIgnoreCase(runmodes, "perf")) {
            runmode = "perf";
        } else if (StringUtils.containsIgnoreCase(runmodes, "qa")) {
            runmode = "qa";
        } else if (StringUtils.containsIgnoreCase(runmodes, "dev")) {
            runmode = "dev";
        }
        return runmode;
    }
    /**
     * @return aemmode calculate the aem mode on env.
     * @param aemmodes pass aemmode to clean up and set aemmode
     */
    public final String calculateAemMode(final String aemmodes) {
      String aemmode = "local";
      if (StringUtils.containsIgnoreCase(aemmodes, "author")) {
        aemmode = "author";
      } else if (StringUtils.containsIgnoreCase(aemmodes, "publish")) {
        aemmode = "publish";
      }
      return aemmode;
    }
     /**
     * Get featurePage
     * @return oneTrustService
     */
    public final OneTrustService getOneTrustService() {
        return oneTrustService;
     }
     /**
      * Get featurePage
      * @return oneTrustService
      */
     public final String getOneTrustCodeFromService() {
       return oneTrustCodeFromService;
     }
}
