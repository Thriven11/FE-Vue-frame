package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the abandoned cart modal
 */
@Model(adaptables = Resource.class)
public interface ShareCartModel {


    /**
     *
     * @return Share Cart link label
     */
    @ValueMapValue(name = "label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLabel();

    /**
     *
     * @return  Confirmation Message for share cart
     */
    @ValueMapValue(name = "confMsg", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfMsg();

    /**
     *
     * @return  Functionality to choose between copy link and share cart
     */
    @ValueMapValue(name = "functionality", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFunctionality();
}

