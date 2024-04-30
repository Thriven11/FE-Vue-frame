package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;


import javax.inject.Inject;

/**
 * Sling Model for Hero Form
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface LightboxFormComponentModel {


    /**
     *
     * @return label
     */
    @Inject
    String getLabel();

    /**
     *
     * @return formPath
     */
    @Inject
    String getFormPath();


    /**
     *
     * @return confMessagePath
     */
    @Inject
    String getConfMessagePath();

}

