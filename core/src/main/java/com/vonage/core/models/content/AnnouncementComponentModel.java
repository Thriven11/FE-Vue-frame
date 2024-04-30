package com.vonage.core.models.content;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/**
 * Sling model for List of Announcement
 *
 * @author Vonage
 */
@Model(adaptables = {Resource.class})
public interface AnnouncementComponentModel {

    /**
     * List of announcements
     *
     * @return announcements
     */
    @ChildResource(name = "announcements",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<Announcement> getAnnouncements();

    /**
     * Announcement Class contains announcement text with associated cta
     */
    @Model(adaptables = {Resource.class})
    interface Announcement {

        /**
         * Announcement headline
         *
         * @return headline
         */
        @ValueMapValue(name = "headline",
                injectionStrategy = InjectionStrategy.OPTIONAL)
        String getHeadline();

        /**
         * Optional link in each announcement item
         * @return announcement Link
         */
        @CTAProperty
        @Optional CTA getLink();

    }

}
