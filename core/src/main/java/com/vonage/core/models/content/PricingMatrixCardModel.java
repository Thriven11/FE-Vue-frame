package com.vonage.core.models.content;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Pricing Matrix Card Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface PricingMatrixCardModel {

    /**
     * @return isBestValue
     */
    @Inject
    Boolean getIsBestValue();

    /**
     *
     * @return bannerText
     */
    @ValueMapValue(name = "bannerText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getBannerText();

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return startingPrice
     */
    @ValueMapValue(name = "startingPrice", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getStartingPrice();

    /**
     *
     * @return costDisclaimer
     */
    @ValueMapValue(name = "costDisclaimer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCostDisclaimer();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     * Prices
     *
     * @return Prices
     */
    @Inject
    List<Prices> getPrices();

    /**
     * Sling model for Price Variants.
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public interface Prices {

        /**
         * @return price
         */
        @Inject
        String getPrice();

        /**
         * @return legal
         */
        @Inject
        String getLegal();

        /**
         * @return upperLimit
         */
        @Inject
        String getUpperLimit();

        /**
         * @return hidePrimaryCTA
         */
        @Inject
        String getHidePrimaryCTA();

        /**
         * @return hideSecondaryCTA
         */
        @Inject
        String getHideSecondaryCTA();

        /**
         * @return hideTextLink
         */
        @Inject
        String getHideTextLink();

    }

}
