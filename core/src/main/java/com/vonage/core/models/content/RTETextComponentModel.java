package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

/**
 *Sling Model for RTE Text Component
 *
 */
@Model(adaptables = Resource.class)
public interface RTETextComponentModel {

    /**
     *
     * @return SubHeadline
     */
    @ValueMapValue(name = "subHeadline",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSubHeadline();

    /**
     *
     * @return subHeadlineSize
     */
    @ValueMapValue(name = "subHeadlineSize",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSubHeadlineSize();

    /**
     *
     * @return End Note
     */
    @ValueMapValue(name = "endNote",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEndNote();

    /**
     * CTA
     * @return CTA Link
     */
    @CTAProperty
    @Optional CTA getLink();

 }

