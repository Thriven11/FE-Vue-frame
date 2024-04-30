package com.vonage.core.models.content;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Hero Alternative Component
 */
@Model(adaptables = Resource.class)
public interface HeroAlternativeComponentModel {

    /**
     *
     * @return textColor
     */
    @Inject @Default(values = "hero-alt--white-text")
    String getTextColor();

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

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

}
