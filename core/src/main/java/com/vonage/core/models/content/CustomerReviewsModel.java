package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Customer Reviews Module
 */
@Model(adaptables = Resource.class)
public interface CustomerReviewsModel {
    /**
     *
     * @return pageSize
     */
    @ValueMapValue(name = "pageSize",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPageSize();

    /**
     *
     * @return defaultProduct
     */
    @ValueMapValue(name = "defaultProduct",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDefaultProduct();

    /**
     *
     * @return displayType
     */
    @ValueMapValue(name = "displayType",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDisplayType();

    /**
     *
     * @return widgetHeadline
     */
    @ValueMapValue(name = "widgetHeadline",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getWidgetHeadline();

    /**
     *
     * @return widgetSubHeadline
     */
    @ValueMapValue(name = "widgetSubHeadline",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getWidgetSubHeadline();

    /**
     *
     * @return maxReviewTitle
     */
    @ValueMapValue(name = "maxReviewTitle",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMaxReviewTitle();

}
