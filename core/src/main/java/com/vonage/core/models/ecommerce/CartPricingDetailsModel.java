package com.vonage.core.models.ecommerce;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Pricing Card
 */
@Model(adaptables = Resource.class)
public interface CartPricingDetailsModel {

    /**
     *
     * @return Component Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return Disclaimer text
     */
    @ValueMapValue(name = "disclaimer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDisclaimer();

    /**
     *
     * @return One time fees label text
     */
    @ValueMapValue(name = "oneTimeFeesLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getOneTimeFeesLabel();

    /**
     *
     * @return Monthly fees label text
     */
    @ValueMapValue(name = "monthlyFeesLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMonthlyFeesLabel();

    /**
     *
     * @return Checkout button text
     */
    @ValueMapValue(name = "checkoutText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCheckoutText();

    /**
     *
     * @return Checkout button Url
     */
    @ValueMapValue(name = "checkoutUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCheckoutUrl();

    /**
     *
     * @return Next Step headline
     */
    @ValueMapValue(name = "nextStepHeadline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNextStepHeadline();

    /**
     *
     * @return cta
     */
    @CTAProperty
    CTA getNextStepCta();

     /**
     *
     * @return Get Shared Cart Content
     */
    @ChildResource(name = "shareCart", injectionStrategy = InjectionStrategy.OPTIONAL)
    ShareCartModel getShareCart();

}
