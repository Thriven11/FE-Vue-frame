package com.vonage.core.models;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * The Class CTA.
 */
@Model(adaptables = {Resource.class})
public interface CTAModel {

    /**
     *
     * @return cta
     */
    @CTAProperty
    @Optional
    CTA getLink();

}
