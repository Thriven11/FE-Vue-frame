package com.vonage.core.models.content;

import com.vonage.core.beans.CTA;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 *Sling Model for CardComponent
 */
@Model(adaptables = Resource.class)
public interface CardComponentModel {

    /**
     *
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     *
     * @return statictitle
     */
    @ValueMapValue(name = "staticTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getStaticTitle();

    /**
     *
     * @return description
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = VonageConstants.AUTHOR_DEFAULT_DESCRIPTION)
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
     * @return icon
     */
    @ValueMapValue(name = "icon",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getIcon();

    /**
     *
     * @return theme
     */
    @ValueMapValue(name = "theme",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTheme();

    /**
     *
     * @return positon
     */
    @ValueMapValue(name = "position",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPosition();

    /**
     *
     * @return headerType
     */
    @ValueMapValue(name = "headerType",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeaderType();

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
     * @return size
     */
    @ValueMapValue(name = "size",
                injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSize();

    /**
     *
     * @return textAlignemnt
     */
    @ValueMapValue(name = "textAlignemnt",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    boolean getTextAlignemnt();

    /**
     *
     * @return Margin Bottom
     */
    @ValueMapValue(name = "marginBottom",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getMarginBottom();

    /**
     *
     * @return Margin Bottom
     */
    @ValueMapValue(name = "noMarginBottom",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNoMarginBottom();

    /**
     *
     * @return text top Alignemnt
     */
    @ValueMapValue(name = "topAllignment",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTopAllignment();

    /**
    *
    * @return headline
    */
   @ValueMapValue(name = "headline",
           injectionStrategy = InjectionStrategy.OPTIONAL)
   String getHeadline();

    /**
    *
    * @return promotionalIconType
    */
    @ValueMapValue(name = "promotionalIconType",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPromotionalIconType();

 }

