package com.vonage.core.models.content.videos;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Video
 */
@Model(adaptables = Resource.class)
public interface VideoCardComponentModel {

    /**
     *
     * @return eyebrow
     */
   @ValueMapValue(name = "eyebrow",
                  injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();

    /**
     *
     * *@return Title
     */
    @ValueMapValue(name = "title",
                    injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * * @return DataVideoID
     */
    @ValueMapValue(name = "dataVideoId",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDataVideoId();

    /**
     *
     **  @return VideoPlayLabel
     */
    @ValueMapValue(name = "videoPlayLabel",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getVideoPlayLabel();

    /**
     *
     * @return TileTheme
     */
    @ValueMapValue(name = "tileTheme",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTileTheme();

    /**
     *
     * * @return Description
     */
    @ValueMapValue(name = "description",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     *
     **  @return fileReference
     */
    @ValueMapValue(name = "fileReference",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFileReference();

    /**
     *
     **  @return ariaLabel
     */
    @ValueMapValue(name = "ariaLabel",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAriaLabel();

}
