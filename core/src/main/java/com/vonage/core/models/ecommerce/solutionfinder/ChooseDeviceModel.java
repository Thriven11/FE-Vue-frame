package com.vonage.core.models.ecommerce.solutionfinder;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for choose device section
 */
@Model(adaptables = Resource.class)
public interface ChooseDeviceModel {
    /**
     * Title
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     * Eyebrow
     * @return title
     */
    @ValueMapValue(name = "eyebrow", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();

    /**
     * List of device cards
     * @return Device Card list
     */
    @ChildResource(name = "deviceCardList", injectionStrategy = InjectionStrategy.OPTIONAL)
    List<DeviceCard> getDeviceCardList();

    /**
     * Model for the device cards
     */
    @Model(adaptables = {Resource.class})
    interface DeviceCard {
        /**
         * Title
         * @return title
         */
        @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
        String getTitle();

        /**
         * Image File Reference
         * @return imageFileReference
         */
        @ValueMapValue(name = "imageFileReference", injectionStrategy = InjectionStrategy.OPTIONAL)
        String getImageFileReference();

        /**
         * Image alt text
         * @return imageAltText
         */
        @ValueMapValue(name = "imageAltText", injectionStrategy = InjectionStrategy.OPTIONAL)
        String getImageAltText();

        /**
         * Category Id
         * @return categoryId
         */
        @ValueMapValue(name = "categoryId", injectionStrategy = InjectionStrategy.OPTIONAL)
        String getCategoryId();
    }
}
