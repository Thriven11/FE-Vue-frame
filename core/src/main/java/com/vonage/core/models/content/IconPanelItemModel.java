package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

/**
 * Sling model for Icon Panel Item
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface IconPanelItemModel {

    /**
     *
     * @return headline
     */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
    *
    * @return iconSize
    */
    @ValueMapValue(name = "iconSize", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getIconSize();

    /**
     *
     * @return cta
     */
    @CTAProperty
    CTA getLink();

    /**
     *
     * @return fileReference
     */
    @ValueMapValue(name = "fileReference", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFileReference();

    /**
     *
     * @return icon
     */
    @ValueMapValue(name = "icon", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getIcon();

    /**
     *
     * @return altText
     */
    @ValueMapValue(name = "altText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAltText();

}
