package com.vonage.core.models.ecommerce;

import java.util.Collection;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * Sling Model for Best Next Step Form
 */
@Model(adaptables = Resource.class)
public interface BestNextStepFormModel {

    /**
     *
     * @return contactLeyend
     */
    @ValueMapValue(name = "contactLeyend", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getContactLeyend();

    /**
     *
     * @return phoneLeyend
     */
    @ValueMapValue(name = "phoneLeyend", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPhoneLeyend();

    /**
     *
     * @return soonCallLabel
     */
    @ValueMapValue(name = "soonCallLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSoonCallLabel();

    /**
     *
     * @return scheduledCallLabel
     */
    @ValueMapValue(name = "scheduledCallLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getScheduledCallLabel();

    /**
     *
     * @return footNote
     */
    @ValueMapValue(name = "footNote", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getFootNote();

    /**
     *
     * @return tellUsMoreLeyend
     */
    @ValueMapValue(name = "tellUsMoreLeyend", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTellUsMoreLeyend();

    /**
     *
     * @return privacyPolicySchedule
     */
    @ValueMapValue(name = "privacyPolicySchedule", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPrivacyPolicySchedule();

    /**
     *
     * @return confirm Title
     */
    @ValueMapValue(name = "confirmEmailTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfirmEmailTitle();

    /**
     *
     * @return confirm Title
     */
    @ValueMapValue(name = "confirmPhoneTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfirmPhoneTitle();

    /**
     *
     * @return confirmMessage
     */
    @ValueMapValue(name = "confirmPhoneMessage", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfirmPhoneMessage();

    /**
     *
     * @return add Calendar Title
     */
    @ValueMapValue(name = "addCalendarTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getAddCalendarTitle();

    /**
     *
     * @return calendar Event Title
     */
    @ValueMapValue(name = "calendarEventTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCalendarEventTitle();

    /**
     *
     * @return calendar Event Description
     */
    @ValueMapValue(name = "calendarEventDescription", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCalendarEventDescription();

    /**
     *
     * @return primaryCtaText
     */
    @ValueMapValue(name = "primaryCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPrimaryCtaText();

    /**
     *
     * @return primaryCtaUrl
     */
    @ValueMapValue(name = "primaryCtaUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPrimaryCtaUrl();

    /**
     *
     * @return secondaryCtaText
     */
    @ValueMapValue(name = "secondaryCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSecondaryCtaText();

    /**
     *
     * @return secondaryCtaUrl
     */
    @ValueMapValue(name = "secondaryCtaUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSecondaryCtaUrl();

    /**
     *
     * @return ctaRedscheduleText
     */
    @ValueMapValue(name = "ctaRescheduleText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCtaRescheduleText();

    /**
     *
     * @return ctaRedscheduleUrl
     */
    @ValueMapValue(name = "ctaRescheduleUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCtaRescheduleUrl();

    /**
     * Excuded dates
     * @return excludedDates
     */
    @ValueMapValue(name = "excludedDates", injectionStrategy = InjectionStrategy.OPTIONAL)
    Collection<String> getExcludedDates();
}
