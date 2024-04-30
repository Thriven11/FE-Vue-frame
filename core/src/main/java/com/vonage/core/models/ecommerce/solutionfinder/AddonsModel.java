package com.vonage.core.models.ecommerce.solutionfinder;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for company size section
 */
@Model(adaptables = Resource.class)
public interface AddonsModel {

    /**
     * Title
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     * Eyebrow
     * @return eyebrow
     */
    @ValueMapValue(name = "eyebrow", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();
}
