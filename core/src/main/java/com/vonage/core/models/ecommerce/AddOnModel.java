package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * Sling Model for Add-on
 */
@Model(adaptables = Resource.class)
public interface AddOnModel {

    /**
     *
     * @return section Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
    *
    * @return section SubTitle
    */
   @ValueMapValue(name = "subTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getSubTitle();
   /**
    *
    * @return section headline
    */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

}
