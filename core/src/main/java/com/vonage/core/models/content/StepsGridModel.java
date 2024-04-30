package com.vonage.core.models.content;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;


/**
 * Sling Model for Details Grid Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface StepsGridModel {

    /**
     * @return eyebrow
     */
    @Inject
    String getEyebrow();

    /**
     * @return title
     */
    @Inject
    String getTitle();

    /**
     * @return introText
     */
    @Inject
    String getIntroText();

    /**
     * @return description
     */
    @Inject
    String getIntroDescription();

    /**
     *
     * @return cta
     */
    @CTAProperty
    CTA getLink();

    /**
     * Sling model for Header List.
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public interface ItemsList {

        /**
         * @return listItem
         */
        @Inject
        String getListItem();
    }

}
