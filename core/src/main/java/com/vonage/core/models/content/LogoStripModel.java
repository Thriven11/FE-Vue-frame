package com.vonage.core.models.content;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


/**
 * Sling Model for Logo Strip Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface LogoStripModel {

    /**
     * @return headline
     */
    @Inject
    String getHeadline();

     /**
     * @return numberOfLogos
     */
    @Inject
    String getNumberOfLogos();

}
