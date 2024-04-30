package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the checkout form component, shipping section
 */
@Model(adaptables = Resource.class)
public interface CheckoutShippingModel {

    /**
     *
     * @return section Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return same As Service checkbox label
     */
    @ValueMapValue(name = "sameAsService", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSameAsService();

    /**
     *
     * @return same shipping Method label
     */
    @ValueMapValue(name = "shippingMethod", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getShippingMethod();

    /**
     *
     * @return Legal text
     */
    @ValueMapValue(name = "legalText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLegalText();
}
