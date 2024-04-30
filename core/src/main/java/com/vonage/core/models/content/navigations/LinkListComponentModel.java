package com.vonage.core.models.content.navigations;


import com.vonage.core.beans.CTA;
import com.vonage.core.models.CTAModel;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling model for Link List Navigations
 *
 * @author Vonage
 */
@Model(adaptables = {Resource.class})
public interface LinkListComponentModel {

    /**
     * Link List Title
     *
     * @return title
     */
    @ValueMapValue(name = "title",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     * Link List staticLabel
     *
     * @return staticLabel
     */
    @ValueMapValue(name = "staticLabel",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getStaticLabel();

    /**
     *
     * @return cta
     */
    @CTAProperty
    @Optional
    CTA getLink();


    /**
     * Link List
     *
     * @return Link List
     */
    @ChildResource(name = "links",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<CTAModel> getLinks();
}
