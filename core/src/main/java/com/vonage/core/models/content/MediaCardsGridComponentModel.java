package com.vonage.core.models.content;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 *Sling Model for Media Cards Grid Component
 */
@Model(adaptables = Resource.class)
public interface MediaCardsGridComponentModel {

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDescription();

    /**
     *
     * @return fileReference
     */
    @ValueMapValue(name = "fileReference",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFileReference();
    /**
     *
     * @return cardColumns
     */
    @ValueMapValue(name = "cardColumns",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCardColumns();
    /**
     *
     * @return cta
     */
    @CTAProperty
    @Optional
    CTA getLink();

    /**
     *
     * @return altText
     */
    @ValueMapValue(name = "altText",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAltText();

    /**
     *
     * @return Aria Label
     */
    @ValueMapValue(name = "ariaLabel",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAriaLabel();
    /**
     *
     * @return anchor
     */
    @ValueMapValue(name = "anchor",
                   injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAnchor();
 }
