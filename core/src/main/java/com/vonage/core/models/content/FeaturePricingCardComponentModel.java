package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling Model for Pricing Card
 */
@Model(adaptables = Resource.class)
public interface FeaturePricingCardComponentModel {

    /**
     *
     * @return currencyToggle
     */
    @ValueMapValue(name = "currencyToggle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCurrencyToggle();

    /**
     *
     * @return caption
     * "starting at" by default
     */
    @ValueMapValue(name = "caption", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCaption();

    /**
     *
     * @return currency
     * Symbol of currency used, e.g. "$" or "€"
     * "$" by default
     */
    @ValueMapValue(name = "currency", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCurrency();

    /**
     *
     * @return price
     */
    @ValueMapValue(name = "price", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPrice();

    /**
     *
     * @return frequency
     * "/month" by default
     */
    @ValueMapValue(name = "frequency", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFrequency();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     *
     * @return headline
     * Title for right section
     * "Per minute feature pricing" by default
     */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

    /**
     *
     * @return offeringList
     */
    @ChildResource(name = "offeringList",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<PerMinuteSubcomponentModel> getOfferingList();
}
