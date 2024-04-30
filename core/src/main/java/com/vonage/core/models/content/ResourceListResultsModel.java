package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model for Resource List Results
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface ResourceListResultsModel {

    /**
     *
     * @return noResultsTitle
     */
    @ValueMapValue(name = "noResultsTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNoResultsTitle();

    /**
     *
     * @return noResultsDescription
     */
    @ValueMapValue(name = "noResultsDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNoResultsDescription();

}
