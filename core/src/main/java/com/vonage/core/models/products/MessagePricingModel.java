package com.vonage.core.models.products;

import com.day.cq.commons.jcr.JcrConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Sling Model for SMS Pricing
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "commerce/components/product")
@Exporter(name = "jackson", selector = "messaging", extensions = "json")
public interface MessagePricingModel {

    /**
     * @return title
     */
    @Inject
    @Named(JcrConstants.JCR_TITLE)
    String getTitle();

    /**
     * @return restricted
     */
    @Inject
    @Named("restricted")
    String getRestricted();

    /**
     * @return exchange rates
     */
    @Inject
    @Via("parent")
    @Named("../USD")
    String getUSD();

    /**
     *
     * @return toll-free
     */
    @ChildResource(name = "toll-free", injectionStrategy = InjectionStrategy.OPTIONAL)
    Subscription getTollFree();

    /**
     *
     * @return virtual-number
     */
    @ChildResource(name = "virtual-number", injectionStrategy = InjectionStrategy.OPTIONAL)
    Subscription getVirtualNumber();

    /**
     *
     * @return outgoing
     */
    @ChildResource(name = "outgoing", injectionStrategy = InjectionStrategy.OPTIONAL)
    Outgoing getOutgoing();

    /**
     * Sling model for Virtual Number/ TollFree Price Variant.
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public interface Subscription {

        /**
         * @return incoming price
         */
        @Inject
        String getIncoming();

      /**
       * @return incoming price
       */
      @Inject
      String getOutgoing();

      /**
       * @return rental price
       */
      @Inject
      String getRental();

      /**
       * @return rental price sms
       */
      @Inject
      @Named("rental.sms")
      @JsonProperty(value = "rental.sms")
      String getSmsRental();

      /**
       * @return rental price voice
       */
      @Inject
      @Named("rental.voice")
      @JsonProperty(value = "rental.voice")
      String getVoiceRentall();

    }

    /**
     * Sling model for Outgoing Price Variant.
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public interface Outgoing {

        /**
         * @return outgoing price
         */
        @Inject
        String getOutgoing();
    }
}

