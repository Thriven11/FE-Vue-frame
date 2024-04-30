package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Plan Summary
 */
@Model(adaptables = Resource.class)
public interface PostPurchaseCard {

    /**
    *
    *  @return description
    *
    */
   @ValueMapValue(name = "number", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getNumber();

    /**
     *
     *  @return description
     *
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

}
