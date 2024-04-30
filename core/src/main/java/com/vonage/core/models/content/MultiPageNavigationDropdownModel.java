package com.vonage.core.models.content;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling model for List of MultiPageNavigationDropdown
 *
 * @author Vonage
 */
@Model(adaptables = {Resource.class})
public interface MultiPageNavigationDropdownModel {

    /**
     * Dropdown headline
     *
     * @return headline
     */
    @ValueMapValue(name = "headline",
    injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

    /**
     * Dropdown placeholder
     *
     * @return placeholder
     */
    @ValueMapValue(name = "placeholder",
    injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPlaceholder();

    /**
     * CTA label
     *
     * @return ctaLabel
     */
    @ValueMapValue(name = "ctaLabel",
    injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCtaLabel();

    /**
     * CTA ariaLabel
     *
     * @return ariaLabel
     */
    @ValueMapValue(name = "ariaLabel",
    injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAriaLabel();

    /**
     * List of dropdown options
     *
     * @return dropdown options
     */
    @ChildResource(name = "dropdownOptions",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<DropdownOptions> getDropdownOptions();

    /**
     * DropdownOptions Class contains cta fields for options
     */
    @Model(adaptables = {Resource.class})
    interface DropdownOptions {

        /**
         * Optional link in each dropdown item
         * @return dropdown Link
         */
        @CTAProperty
        @Optional CTA getLink();

    }

}
