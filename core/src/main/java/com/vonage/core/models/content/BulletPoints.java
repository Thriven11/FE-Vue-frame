package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 *Sling Model for Bulletpoints for hero form component
 */
@Model(adaptables = Resource.class)
public interface BulletPoints {

    /**
     *
     * @return label
     */
    @ValueMapValue(name = "label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLabel();


 }

