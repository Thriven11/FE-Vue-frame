package com.vonage.core.models.content;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.vonage.core.models.CTAModel;

/**
 * Sling Model for Lead Gen Alternative Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LeadGenAlternativeComponentModel {

    /**
     * isChatLink boolean
     */
    private boolean isChatLink = false;

    /**
     * Chat CTA
     */
    @ChildResource(name = "chatCta",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    private CTAModel chatCTA;

    /**
     * Global Contact CTA
     */
    @ChildResource(name = "globalContactCTA",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    private CTAModel globalContactCTA;

    /**
     * Lead Gen Headline
     */
    @Inject @Named("headline")
    private String headline;

    /**
     * @return phoneNumberLabel
     */
    @Inject @Named("phoneNumberLabel")
    private String phoneNumberLabel;

    /**
     * @return phoneNumber
     */
    @Inject @Named("phoneNumber")
    private String phoneNumber;

    /**
     * @return phoneNumberLabel2
     */
    @Inject @Named("phoneNumberLabel2")
    private String phoneNumberLabel2;

    /**
     * @return phoneNumber2
     */
    @Inject @Named("phoneNumber2")
    private String phoneNumber2;

    /**
     * @return globalContactLabel
     */
    @Inject @Named("globalContactLabel")
    private String globalContactLabel;

  /**
    * init method
    */
    @PostConstruct
    protected final void init() {
        this.isChatLink = StringUtils.contains(this.chatCTA.getLink().getHref(), "leadbot-3-1");
    }

    /**
     * Get Headline
     * @return headline
     */
    public final String getHeadline() {
      return this.headline;
    }

    /**
     * Get phoneNumberLabel
     * @return phoneNumberLabel
     */
    public final String getPhoneNumberLabel() {
      return this.phoneNumberLabel;
    }

     /**
     * Get phoneNumber
     * @return phoneNumber
     */
    public final String getPhoneNumber() {
      return this.phoneNumber;
    }

     /**
     * Get phoneNumberLabel2
     * @return phoneNumberLabel2
     */
    public final String getPhoneNumberLabel2() {
      return this.phoneNumberLabel2;
    }

    /**
     * Get phoneNumber2
     * @return phoneNumber2
     */
    public final String getPhoneNumber2() {
      return this.phoneNumber2;
    }

    /**
     * Get globalContactLabel
     * @return globalContactLabel
     */
    public final String getGlobalContactLabel() {
      return this.globalContactLabel;
    }

    /**
     * isChatLink
     * @return isChatLink
     */
    public final boolean isChatLink() {
      return this.isChatLink;
    }
}
