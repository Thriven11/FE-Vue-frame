package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

import javax.inject.Inject;

/**
 * Sling Model for Hero Form
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface HeroFormModel {

    /** Text Fields - Left Column/Dialog tab */

    /**
     *
     * @return eyebrowContent
     */
    @Inject
    String getEyebrowContent();

    /**
     *
     * @return title
     */
    @Inject
    String getTitle();

    /**
     *
     * @return description
     */
    @Inject
    String getDescription();

    /**
     *
     * @return confMessagePath
     */
    @Inject
    String getConfMessagePath();

    /**
     *
     * @return bulletPoints
     */
    @Inject
    List<BulletPoint> getBulletPoints();

    /**
     * Sling Sub-Model for Hero Form Bullet Points
     */
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface BulletPoint {

      /**
         *
         * @return label
         */
        // @Inject
        @ValueMapValue(name = "label", injectionStrategy = InjectionStrategy.OPTIONAL)
        String getLabel();
    }
}

