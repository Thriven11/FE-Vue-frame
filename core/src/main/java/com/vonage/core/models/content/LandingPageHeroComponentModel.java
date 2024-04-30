package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Landing Page Hero Component
 */
@Model(adaptables = Resource.class)
public interface LandingPageHeroComponentModel {

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     *
     * @return backgroundImage
     */
    @ValueMapValue(name = "backgroundImage", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getBackgroundImage();

    /**
    *
    * @return backgroundColor
    */
   @ValueMapValue(name = "backgroundColor", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getBackgroundColor();

    /**
     *
     * @return hideBreadcrumbs
     */
    @ValueMapValue(name = "hideBreadcrumbs", injectionStrategy = InjectionStrategy.OPTIONAL)
    Boolean getHideBreadcrumbs();

    /**
     *
     * @return alternateClouds
     */
    @ValueMapValue(name = "alternateClouds", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAlternateClouds();

    /**
     *
     * @return mediaContained
     */
    @ValueMapValue(name = "mediaContained", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMediaContained();

    /**
     *
     * @return mediaFloating
     */
    @ValueMapValue(name = "mediaFloating", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMediaFloating();

    /**
     *
     * @return mediaLandscape
     */
    @ValueMapValue(name = "mediaLandscape", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMediaLandscape();

    /**
     *
     * @return noSpace
     */
    @ValueMapValue(name = "noSpace", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNoSpace();

    /**
    *
    * @return addPadding
    */
   @ValueMapValue(name = "addPadding", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getAddPadding();

    /**
     *
     * @return mediaType
     */
    @ValueMapValue(name = "mediaType", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMediaType();

    /**
     *
     * @return priceCurrency
     */
    @ValueMapValue(name = "priceCurrency", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPriceCurrency();

    /**
     *
     * @return subcopy
     */
    @ValueMapValue(name = "subcopy", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSubcopy();

    /**
     *
     * @return priceUnit
     */
    @ValueMapValue(name = "priceUnit", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPriceUnit();

    /**
     *
     * @return priceDescription
     */
    @ValueMapValue(name = "priceDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPriceDescription();

    /**
     *
     * @return priceDescription
     */
    @ValueMapValue(name = "href", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHref();

}
