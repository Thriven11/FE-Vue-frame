package com.vonage.core.models.structure.header;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.content.CardComponentModel;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling model for Primary Navigation.
 */
@Model(adaptables = Resource.class)
public interface PrimaryNavigationComponentModel {

    /**
     *
     * @return showSecondaryLevel
     */
    @ValueMapValue(name = "showSecondaryLevel",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getShowSecondaryLevel();

    /**
     *
     * @return Link
     */
    @CTAProperty
    @Optional
    CTA getLink();

    /**
     *
     * @return secondaryNavigationList
     */
    @ChildResource(name = "secondaryNavLinks",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<CardComponentModel> getSecondaryNavigationList();
}
