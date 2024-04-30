package com.vonage.core.models.structure;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

/**
 * Sling Model for Ecommerce Header
 */
@Model(adaptables = Resource.class)
public interface EcommerceHeader {


    /**
     *
     * @return contact button label
     */
    @ValueMapValue(name = "contactLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getContactLabel();

    /**
     *
     * @return contact button url
     */
    @ValueMapValue(name = "contactHref", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getContactHref();

    /**
     *
     * @return Link
     */
    @CTAProperty
    @Optional
    CTA getLink();

    /**
     *
     * @return Cart Details Disclaimer text
     */
    @ValueMapValue(name = "cartDetailsDisclaimer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCartDetailsDisclaimer();

    /**
     *
     * @return Share Cart Label
     */
    @ValueMapValue(name = "shareCartLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getShareCartLabel();

}
