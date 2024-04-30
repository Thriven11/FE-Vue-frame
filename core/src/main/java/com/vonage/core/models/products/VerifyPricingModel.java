package com.vonage.core.models.products;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.day.cq.commons.jcr.JcrConstants;

/**
 * Sling Model for Verify Pricing
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "commerce/components/product")
@Exporter(name = "jackson", selector = "verify", extensions = "json")
public interface VerifyPricingModel {

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
     * @return flatPrice
     */
    @Inject
    String getFlatPrice();

    /**
     *
     * @return flatPushPrice
     */
    @Inject
    String getFlatPushPrice();

    /**
     *
     * @return flatFailPrice
     */
    @Inject
    String getFlatFailPrice();

}
