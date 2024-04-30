package com.vonage.core.models.content.navigations;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Utility Navigation
 *
 * @author Vonage
 */
@Model(adaptables = {Resource.class})
public interface UtilityNavComponentModel extends LinkListComponentModel {

    /**
     * Show Language Selector
     *
     * @return showLanguageSelector
     */
    @ValueMapValue(name = "showLanguageSelector",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getShowLanguageSelector();

    /**
     * Hide Country Selector
     *
     * @return hideCountrySelector
     */
    @ValueMapValue(name = "hideCountrySelector",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHideCountrySelector();
}
