package com.vonage.core.models.ecommerce.solutionfinder;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for the plan recommendation section
 */
@Model(adaptables = Resource.class)
public interface PlanRecommendationModel {

    /**
     * Eyebrow
     * @return eyebrow
     */
    @ValueMapValue(name = "eyebrow", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();

    /**
     * Features text
     * @return featuresText
     */
    @ValueMapValue(name = "featuresText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFeaturesText();

    /**
     * Addidtional features text
     * @return featuresText
     */
    @ValueMapValue(name = "additionalFeaturesText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAdditionalFeaturesText();

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
     * More info headline
     * @return moreInfoHeadline
     */
    @ValueMapValue(name = "moreInfoHeadline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMoreInfoHeadline();

    /**
     * More info sub headline
     * @return moreInfoSubHeadline
     */
    @ValueMapValue(name = "moreInfoSubHeadline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMoreInfoSubHeadline();

    /**
     * More info Cta Text
     * @return moreInfoCtaText
     */
    @ValueMapValue(name = "moreInfoCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMoreInfoCtaText();

    /**
     * More info Cta URL
     * @return moreInfoCtaUrl
     */
    @ValueMapValue(name = "moreInfoCtaUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMoreInfoCtaUrl();

    /**
     * Cost Title
     * @return title
     */
    @ValueMapValue(name = "costTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCostTitle();

    /**
     * Cost description
     * @return description
     */
    @ValueMapValue(name = "costDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCostDescription();

    /**
     * Cost disclaimer
     * @return disclaimer
     */
    @ValueMapValue(name = "costDisclaimer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCostDisclaimer();


    /**
     * Copy Url Cta Text
     * @return copyUrlCtaText
     */
    @ValueMapValue(name = "copyUrlCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCopyUrlCtaText();

    /**
     * Add Plan Cta Text
     * @return addPlanCtaText
     */
    @ValueMapValue(name = "addPlanCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAddPlanCtaText();

    /**
     * Add Plan Cta Url
     * @return addPlanCtaURL
     */
    @ValueMapValue(name = "addPlanCtaURL", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAddPlanCtaURL();
}
