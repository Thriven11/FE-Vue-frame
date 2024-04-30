package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the checkout form component
 */
@Model(adaptables = Resource.class)
public interface CartCheckoutModel {

    /**
    *
    * @return Share Cart Checkbox
    */
   @ValueMapValue(name = "sharecartCheckbox", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getSharecartCheckbox();

}
