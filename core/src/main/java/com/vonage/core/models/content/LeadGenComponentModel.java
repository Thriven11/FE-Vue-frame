package com.vonage.core.models.content;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * Sling Model for Lead Gen Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface LeadGenComponentModel {

    /**
     * leadGen Content list
     * @return LeadGenContent
     */
    @Inject
    List<LeadGenContent> getLeadGenContent();

    /**
     * Lead Gen Headline
     * @return headline
     */
    @Inject
    String getHeadline();

    /**
     * Lead Gen subtitle
     * @return subtitle
     */
    @Inject
    String getSubtitle();

    /**
     * Lead Gen Image path
     * @return fileReference
     */
    @Inject
    String getFileReference();

    /**
     * Lead Gen Image alt
     * @return alt
     */
    @Inject
    String getAlt();

    /**
     * Sling model for Lead Gen Content.
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public interface LeadGenContent {

        /**
         * @return leadGenBusinessUnit
         */
        @Inject
        String getLeadGenBusinessUnit();

        /**
         * @return phoneNumberLabel
         */
        @Inject
        String getPhoneNumberLabel();

        /**
         * @return phoneNumber
         */
        @Inject
        String getPhoneNumber();

        /**
         * @return hoursLabel
         */
        @Inject
        String getHoursLabel();

        /**
         * @return hoursData
         */
        @Inject
        String getHoursData();

    }

}
