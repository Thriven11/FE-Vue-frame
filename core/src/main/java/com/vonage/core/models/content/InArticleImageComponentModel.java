package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 *Sling Model for In Article Image Component
 */
@Model(adaptables = Resource.class)
public interface InArticleImageComponentModel {

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

 }

