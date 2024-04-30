package com.vonage.core.models.structure.header;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * Sling model for Campaign Telephone Number.
 */
@Model(adaptables = Resource.class)
public interface CampaignTfnModel {

    /**
     *
     * @return number
     */
    @ValueMapValue(name = "number",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNumber();

    /**
     *
     * @return number
     */
    @ValueMapValue(name = "copy",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCopy();

}
