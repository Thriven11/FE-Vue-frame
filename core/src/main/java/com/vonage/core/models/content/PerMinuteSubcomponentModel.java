package com.vonage.core.models.content;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Pricing Card
 */
@Model(adaptables = Resource.class)
public interface PerMinuteSubcomponentModel {

    /**
     *
     * @return price
     * "$0.00475" by default
     */
    @ValueMapValue(name = "price", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPrice();

    /**
     *
     * @return description
     * "2,001 - 100,000 minutes" by default
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();
}
