package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

/**
 * Sling Model for the checkout form component
 */
@Model(adaptables = Resource.class)
public interface CheckoutFormModel {

    /**
     *
     * @return Component Account section
     */
    @ChildResource(name = "account", injectionStrategy = InjectionStrategy.OPTIONAL)
    CheckoutAccount getAccount();

    /**
     *
     * @return Component Shipping section
     */
    @ChildResource(name = "shipping", injectionStrategy = InjectionStrategy.OPTIONAL)
    CheckoutShippingModel getShipping();

    /**
     *
     * @return Component Payment section
     */
    @ChildResource(name = "payment", injectionStrategy = InjectionStrategy.OPTIONAL)
    CheckoutPaymentModel getPayment();

    /**
     *
     * @return Component Review section
     */
    @ChildResource(name = "review", injectionStrategy = InjectionStrategy.OPTIONAL)
    CheckoutReviewModel getReview();

    /**
     *
     * @return Abandoned Cart Modal
     */
    @ChildResource(name = "abandonCart", injectionStrategy = InjectionStrategy.OPTIONAL)
    CheckoutAbandonCartModel getAbandonCart();

     /**
     *
     * @return Get Shared Cart Content
     */
    @ChildResource(name = "shareCart", injectionStrategy = InjectionStrategy.OPTIONAL)
    ShareCartModel getShareCart();
}
