package com.vonage.core.models.ecommerce;

import java.util.Collection;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


/**
 * Sling Model for The Best next steps reschedule
 */
@Model(adaptables = Resource.class)
public interface BestNextStepsReschedule {

    /**
     *
     * @return confirm Title
     */
    @ValueMapValue(name = "confirmTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfirmTitle();

    /**
     *
     * @return confirmMessage
     */
    @ValueMapValue(name = "confirmMessage", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfirmMessage();

    /**
     *
     * @return soonConfirmMessage
     */
    @ValueMapValue(name = "soonConfirmMessage", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSoonConfirmMessage();

    /**
     *
     * @return best Time To Call
     */
    @ValueMapValue(name = "bestTimeToCall", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getBestTimeToCall();

    /**
     *
     * @return confirm Cancel Title
     */
    @ValueMapValue(name = "confirmCancelTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getConfirmCancelTitle();

    /**
     *
     * @return keep Appointment Cta Text
     */
    @ValueMapValue(name = "keepAppointmentCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getKeepAppointmentCtaText();

    /**
     *
     * @return cancel Cta Text
     */
    @ValueMapValue(name = "cancelCtaText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCancelCtaText();

    /**
     *
     * @return cancel Confirmed Title
     */
    @ValueMapValue(name = "cancelConfirmedTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCancelConfirmedTitle();

    /**
     *
     * @return cancelledMessage
     */
    @ValueMapValue(name = "cancelledMessage", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCancelledMessage();

    /**
     *
     * @return cancelledTitle
     */
    @ValueMapValue(name = "cancelledTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getCancelledTitle();

    /**
     *
     * @return soon cancel Confirmed Title
     */
    @ValueMapValue(name = "soonCancelConfirmedTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSoonCancelConfirmedTitle();

    /**
     *
     * @return reschedule A Call
     */
    @ValueMapValue(name = "rescheduleACall", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRescheduleACall();

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
     * @return not found Title
     */
    @ValueMapValue(name = "notfoundTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNotfoundTitle();

    /**
     *
     * @return not found Message
     */
    @ValueMapValue(name = "notfoundMessage", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNotfoundMessage();

    /**
     *
     * @return notfoundPrimaryCTAText
     */
    @ValueMapValue(name = "notfoundPrimaryCTAText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNotfoundPrimaryCTAText();

    /**
     *
     * @return notfoundPrimaryCTAUrl
     */
    @ValueMapValue(name = "notfoundPrimaryCTAUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNotfoundPrimaryCTAUrl();

    /**
     *
     * @return notfoundSecondaryCTAText
     */
    @ValueMapValue(name = "notfoundSecondaryCTAText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNotfoundSecondaryCTAText();

    /**
     *
     * @return notfoundSecondaryCTAUrl
     */
    @ValueMapValue(name = "notfoundSecondaryCTAUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getNotfoundSecondaryCTAUrl();

    /**
     *
     * @return rescheduleSecondaryText
     */
    @ValueMapValue(name = "rescheduleSecondaryText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRescheduleSecondaryText();

    /**
     *
     * @return rescheduleSecondaryUrl
     */
    @ValueMapValue(name = "rescheduleSecondaryUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getRescheduleSecondaryUrl();

    /**
     *
     * @return endCTAText
     */
    @ValueMapValue(name = "endCTAText", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEndCTAText();

    /**
     *
     * @return endCTAUrl
     */
    @ValueMapValue(name = "endCTAUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getEndCTAUrl();

    /**
     * Excuded dates
     * @return excludedDates
     */
    @ValueMapValue(name = "excludedDates", injectionStrategy = InjectionStrategy.OPTIONAL)
    Collection<String> getExcludedDates();
}
