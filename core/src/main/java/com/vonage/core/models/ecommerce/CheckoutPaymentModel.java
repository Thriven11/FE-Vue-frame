package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the checkout form component, payment section
 */
@Model(adaptables = Resource.class)
public interface CheckoutPaymentModel {

    /**
     *
     * @return Payment Title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return acceptTermsOfServiceCheck
     */
    @ValueMapValue(name = "acceptTermsOfServiceCheck", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAcceptTermsOfServiceCheck();

    /**
     *
     * @return emergencyServicePolicyText
     */
    @ValueMapValue(name = "emergencyServicePolicyText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEmergencyServicePolicyText();

    /**
     *
     * @return aceptEmergencyServicePolicyCheck
     */
    @ValueMapValue(name = "aceptEmergencyServicePolicyCheck", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAceptEmergencyServicePolicyCheck();

    /**
     *
     * @return oneYearCommitmentCheck
     */
    @ValueMapValue(name = "oneYearCommitmentCheck", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getOneYearCommitmentCheck();

    /**
    *
    * @return cardNotCharged
    */
   @ValueMapValue(name = "cardNotCharged", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getCardNotCharged();

   /**
    *
    * @return reviewSelection
    */
   @ValueMapValue(name = "reviewSelection", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getReviewSelection();

    /**
     *
     * @return oneYearCommitmentCheck
     */
    @ValueMapValue(name = "zuoraDisclaimer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getZuoraDisclaimer();

}
