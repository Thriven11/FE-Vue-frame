package com.vonage.core.models.content;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * Sling Model for Highlight Component
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface HighlightComponentModel {

    /**
     * Highlight Headline
     * @return headline
     */
    @Inject
    String getHeadline();

}
