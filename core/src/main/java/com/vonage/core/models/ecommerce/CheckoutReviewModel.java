package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

/**
 * Sling Model for the checkout form component
 */
@Model(adaptables = Resource.class)
public interface CheckoutReviewModel {

    /**
     *
     * @return section Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return edit plan cta text
     */
    @ValueMapValue(name = "editPlanText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEditPlanText();

    /**
     *
     * @return edit plan cta url
     */
    @ValueMapValue(name = "editPlanUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEditPlanUrl();

    /**
    *
    * @return section PhoneNumberBodyCopy
    */
    @ValueMapValue(name = "phoneNumberBodyCopy", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPhoneNumberBodyCopy();

    /**
     *
     * @return section planTrialMiceText
     */
    @ValueMapValue(name = "planTrialMiceText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPlanTrialMiceText();

    /**
     *
    * @return section ./phoneMiceText
    */
    @ValueMapValue(name = "phoneMiceText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPhoneMiceText();

    /**
     *
     * @return accout section Title
     */
    @ValueMapValue(name = "accountTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAccountTitle();

    /**
     *
     * @return edit account cta text
     */
    @ValueMapValue(name = "editAccountText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEditAccountText();

    /**
     *
     * @return shipping section Title
     */
    @ValueMapValue(name = "shippingTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getShippingTitle();

    /**
     *
     * @return edit Shipping cta text
     */
    @ValueMapValue(name = "editShippingText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEditShippingText();

    /**
     *
     * @return paymment section Title
     */
    @ValueMapValue(name = "paymentTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPaymentTitle();

    /**
     *
     * @return change paymment label
     */
    @ValueMapValue(name = "paymentLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPaymentLabel();

    /**
     *
     * @return complete purchase Button
     */
    @ValueMapValue(name = "completePurchaseLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCompletePurchaseLabel();

    /**
     *
     * @return completePurchaseTextfield
     */
    @ValueMapValue(name = "completePurchaseTextfield", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCompletePurchaseTextfield();

    /**
     *
     * @return Success CTA redirect
     */
    @CTAProperty
    CTA getSuccessRedirect();

    /**
     *
     * @return Pricing Disclaimer text
     */
    @ValueMapValue(name = "pricingDisclaimer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPricingDisclaimer();
}
