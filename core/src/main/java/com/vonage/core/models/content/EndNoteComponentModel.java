package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 *Sling Model for End Note Component
 *
 */
@Model(adaptables = Resource.class)
public interface EndNoteComponentModel {

    /**
     *
     * @return End Note
     */
    @ValueMapValue(name = "endNote",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEndNote();

    /**
     *
     * @return keyLine
     */
    @ValueMapValue(name = "keyLine",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    boolean getKeyLine();

    /**
     *
     * @return background
     */
    @ValueMapValue(name = "background",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getBackground();

}

