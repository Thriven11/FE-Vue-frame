package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Quantity Selector Component
 *
 */
@Model(adaptables = Resource.class)
public interface QuantitySelectorComponentModel {

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
     * @return maximum
     */
    @ValueMapValue(name = "maximum", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMaximum();

    /**
     *
     * @return minimum
     */
    @ValueMapValue(name = "minimum", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMinimum();

    /**
     *
     * @return segment1Label
     */
    @ValueMapValue(name = "segment1Label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment1Label();

    /**
     *
     * @return segment1Maximum
     */
    @ValueMapValue(name = "segment1Maximum", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment1Maximum();

    /**
     *
     * @return segment2Label
     */
    @ValueMapValue(name = "segment2Label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment2Label();

    /**
     *
     * @return segment2Maximum
     */
    @ValueMapValue(name = "segment2Maximum", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment2Maximum();

    /**
     *
     * @return segment3Label
     */
    @ValueMapValue(name = "segment3Label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment3Label();

    /**
     *
     * @return segment3Maximum
     */
    @ValueMapValue(name = "segment3Maximum", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment3Maximum();

    /**
     *
     * @return anchor
     */
    @ValueMapValue(name = "anchor", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAnchor();

}
