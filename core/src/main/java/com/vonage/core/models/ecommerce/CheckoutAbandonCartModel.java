package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

/**
 * Sling Model for the abandoned cart modal
 */
@Model(adaptables = Resource.class)
public interface CheckoutAbandonCartModel {


    /**
     *
     * @return Component Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return  Main text
     */
    @ValueMapValue(name = "text", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getText();
    /**
     *
     * @return Success CTA redirect
     */
    @CTAProperty
    CTA getCta();

    /**
     *
     * @return  close label
     */
    @ValueMapValue(name = "closeLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCloseLabel();
}
