package com.vonage.core.models.content;

import java.util.List;

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
public interface DetailsGridModel {

    /**
     * @return alignment
     */
    @Inject
    String getAlignment();

     /**
     * @return theme
     */
    @Inject
    String getTheme();

     /**
     * @return numberOfFeatures
     */
    @Inject
    String getNumberOfFeatures();

    /**
     * @return eyebrow
     */
    @Inject
    String getEyebrow();

    /**
     * @return getIsEyebrowAPill
     */
    @Inject
    Boolean getEyebrowapill();

    /**
     * @return title
     */
    @Inject
    String getTitle();

    /**
     * @return description
     */
    @Inject
    String getDescription();

     /**
     * List of Items
     * @return listOfItems
     */
    @Inject
    List<ItemsList> getListOfItems();

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
