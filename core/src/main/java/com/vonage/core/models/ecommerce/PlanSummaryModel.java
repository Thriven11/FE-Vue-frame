package com.vonage.core.models.ecommerce;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * Sling Model for Plan Summary
 */
@Model(adaptables = Resource.class)
public interface PlanSummaryModel {

    /**
     *
     * @return section Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

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
    * @return account section title
    */
    @ValueMapValue(name = "accountTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAccountTitle();

    /**
     *
    * @return shipping section title
    */
    @ValueMapValue(name = "shippingTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getShippingTitle();

    /**
     *
    * @return payment section title
    */
    @ValueMapValue(name = "paymentTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPaymentTitle();

    /**
     *
    * @return payment section label
    */
    @ValueMapValue(name = "paymentLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPaymentLabel();

    /**
     * @return Change plan cta
    */
    @CTAProperty
    CTA getChangePlanCta();

    /**
     * @return Monthly price by line
    */
    @ValueMapValue(name = "lineMonthlyPrice", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLineMonthlyPrice();

    /**
     *
     * @return Abandoned Cart Modal
     */
    @ChildResource(name = "abandonCart", injectionStrategy = InjectionStrategy.OPTIONAL)
    CheckoutAbandonCartModel getAbandonCart();

    /**
     *
     * @return subAgent Modal
     */
    @ChildResource(name = "subAgent", injectionStrategy = InjectionStrategy.OPTIONAL)
    SubAgentModel getSubAgent();
}
