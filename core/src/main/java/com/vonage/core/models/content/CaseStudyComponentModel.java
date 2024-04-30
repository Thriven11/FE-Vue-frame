package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Case Study Component
 */
@Model(adaptables = Resource.class)
public interface CaseStudyComponentModel {

    /**
     *
     * @return logoImage
     */
    @ValueMapValue(name = "logoImage",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLogoImage();

    /**
     *
     * @return altText
     */
    @ValueMapValue(name = "altText",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAltText();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     *
     * @return name
     */
    @ValueMapValue(name = "name",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getName();

    /**
     *
     * @return jobTitle
     */
    @ValueMapValue(name = "jobTitle",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getJobTitle();

}
