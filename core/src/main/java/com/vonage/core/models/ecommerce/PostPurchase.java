package com.vonage.core.models.ecommerce;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Plan Summary
 */
@Model(adaptables = Resource.class)
public interface PostPurchase {

    /**
     *
     * @return Headline
     */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

    /**
     *
     * @return subHeadline
     *
     */
    @ValueMapValue(name = "subHeadline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSubHeadline();

    /**
     *
     * @return orderNumber
     */
    @ValueMapValue(name = "orderNumber", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getOrderNumber();

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return leftSubtitle
     */
    @ValueMapValue(name = "leftSubtitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLeftSubtitle();

    /**
     *
     * @return leftDescription
     */
    @ValueMapValue(name = "leftDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLeftDescription();

    /**
     *
     * @return rightSubtitle
     */
    @ValueMapValue(name = "rightSubtitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRightSubtitle();

    /**
     *
     * @return rightDescription
     */
    @ValueMapValue(name = "rightDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRightDescription();
}
