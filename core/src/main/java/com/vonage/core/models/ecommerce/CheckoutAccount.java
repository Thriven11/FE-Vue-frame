package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the checkout form component
 */
@Model(adaptables = Resource.class)
public interface CheckoutAccount {

    /**
     *
     * @return Component Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return Component Address
     */
    @ValueMapValue(name = "address", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAddress();

    /**
     *
     * @return Component description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     *
     * @return Component Address
     */
    @ValueMapValue(name = "companyAddress", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCompanyAddress();

    /**
     *
     * @return Component description
     */
    @ValueMapValue(name = "companyDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCompanyDescription();
}
