package com.vonage.core.models.ecommerce.solutionfinder;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for company size section
 */
@Model(adaptables = Resource.class)
public interface CompanySizeModel {
    /**
     * Title
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     * Eyebrow
     * @return eyebrow
     */
    @ValueMapValue(name = "eyebrow", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();

    /**
     * Input label
     * @return label
     */
    @ValueMapValue(name = "label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLabel();

    /**
     * Slider range start
     * @return rangeStart
     */
    @ValueMapValue(name = "rangeStart", injectionStrategy = InjectionStrategy.OPTIONAL)
    int getRangeStart();

    /**
     * Slider range end
     * @return rangeEnd
     */
    @ValueMapValue(name = "rangeEnd", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRangeEnd();

    /**
     * segment 1 Label
     * @return segment1Label
     */
    @ValueMapValue(name = "segment1Label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment1Label();

    /**
     * segment 1 max value
     * @return segment1MaxValue
     */
    @ValueMapValue(name = "segment1MaxValue", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment1MaxValue();

    /**
     * segment 2 Label
     * @return segment1Label
     */
    @ValueMapValue(name = "segment2Label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment2Label();

    /**
     * segment 2 max value
     * @return segment2MaxValue
     */
    @ValueMapValue(name = "segment2MaxValue", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment2MaxValue();

    /**
     * segment 3 Label
     * @return segment3Label
     */
    @ValueMapValue(name = "segment3Label", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSegment3Label();

    /**
     * contact sales CTA Text
     * @return contactSalesCtaText
     */
    @ValueMapValue(name = "contactSalesCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getContactSalesCtaText();

    /**
     * contact sales CTA URL
     * @return contactSalesCtaURL
     */
    @ValueMapValue(name = "contactSalesCtaURL", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getContactSalesCtaURL();
}
