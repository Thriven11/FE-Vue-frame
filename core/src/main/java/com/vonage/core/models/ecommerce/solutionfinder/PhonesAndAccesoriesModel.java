package com.vonage.core.models.ecommerce.solutionfinder;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Model for lan recommendation section
 */
@Model(adaptables = Resource.class)
public interface PhonesAndAccesoriesModel {

    /**
     * Eyebrow
     * @return eyebrow
     */
    @ValueMapValue(name = "eyebrow", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEyebrow();

    /**
     * Title
     * @return title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTitle();

    /**
     * Yes Checkbox Label
     * @return yesCheckboxLabel
     */
    @ValueMapValue(name = "yesCheckboxLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getYesCheckboxLabel();

    /**
     * No Checkbox Label
     * @return noCheckboxLabel
     */
    @ValueMapValue(name = "noCheckboxLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNoCheckboxLabel();

    /**
     * Default answer
     * @return defaultAnswer
     */
    @ValueMapValue(name = "defaultAnswer", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getDefaultAnswer();

    /**
     * Left Image File Reference
     * @return leftImageFileReference
     */
    @ValueMapValue(name = "leftImageFileReference", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLeftImageFileReference();

    /**
     * Left Image alt text
     * @return leftImageAltText
     */
    @ValueMapValue(name = "leftImageAltText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getLeftImageAltText();


    /**
     * Right Image File Reference
     * @return rightImageFileReference
     */
    @ValueMapValue(name = "rightImageFileReference", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRightImageFileReference();

    /**
     * Right Image alt text
     * @return leftImageAltText
     */
    @ValueMapValue(name = "rightImageAltText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRightImageAltText();
}
