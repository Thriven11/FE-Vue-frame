package com.vonage.core.models.content;

import com.day.cq.wcm.api.Page;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * Sling model for Career Map Component
 */
@Model(adaptables = { Resource.class, Page.class })
public interface CareerMapComponentModel {

    /**
     * Map Headline
     * @return headline
     */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

    /**
     * Map description
     * @return description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     * Open positions text
     * @return open positions text
     */
    @ValueMapValue(name = "openpositions", injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Open Positions")
    String getOpenPositions();

     /**
     * No Jobs at Location Message
     * @return no jobs at location message
     */
    @ValueMapValue(name = "nojobsmsg", injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Sorry, but there are currently no openings available at this location")
    String getNoJobsMsg();

}
