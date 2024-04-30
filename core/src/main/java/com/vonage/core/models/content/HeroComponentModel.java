package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Hero
 */
@Model(adaptables = Resource.class)
public interface HeroComponentModel {

    /**
     *
     * @return eyebrow
     */
    @ValueMapValue(name = "eyebrow",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return dataVideoId
     */
    @ValueMapValue(name = "dataVideoId",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDataVideoId();

    /**
     *
     * @return videoPlayLabel
     */
    @ValueMapValue(name = "videoPlayLabel",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getVideoPlayLabel();

    /**
     *
     * @return tile
     */
    @ValueMapValue(name = "tileTheme",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTileTheme();

    /**
     *
     * @return tile
     */
    @ValueMapValue(name = "ariaLabel",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAriaLabel();

}
