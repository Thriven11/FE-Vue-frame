package com.vonage.core.models.content.forms;

import com.day.cq.wcm.api.Page;
import com.vonage.core.constants.VonageConstants;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vonage.core.services.PageContextService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for Form Container Component
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FormContainerComponentModel {

    /**
    * uuid String
    */
    private String uuid = StringUtils.EMPTY;

    /**
     * pageContext String
     */
    private String pageContext = StringUtils.EMPTY;

    /**
     * inject CurrentPage
     */
    @Inject
    private Page currentPage;

    /**
     * pageContextService
     */
    @Inject
    private transient PageContextService pageContextService;

    /**
     * title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String title;

    /**
     * eyebrow
     */
    @ValueMapValue(name = "eyebrow", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String eyebrow;

    /**
     * errorMessage
     */
    @ValueMapValue(name = "errorMessage", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String errorMessage;

    /**
     * formHandler
     */
    @ValueMapValue(name = "formHandler", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String formHandler;

    /**
     * formType
     */
    @ValueMapValue(name = "formType", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String formType;

    /**
     * eventInstance
     */
    @ValueMapValue(name = "eventInstance", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String eventInstance;

    /**
     * eventId
     */
    @ValueMapValue(name = "eventId", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String eventId;

     /**
     * webinarType
     */
    @ValueMapValue(name = "webinarType", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String webinarType;

    /**
     * showCode
     */
    @ValueMapValue(name = "showCode", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String showCode;

    /**
    * init method
    */
    @PostConstruct
    protected final void init() {
        uuid = UUID.randomUUID().toString();
        pageContext = pageContextService.getPageContext(currentPage);
    }

    /**
     *
     * @return title
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     *
     * @return eyebrow
     */
    public final String getEyebrow() {
        return this.eyebrow;
    }

    /**
     *
     * @return errorMessage
     */
    public final String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     *
     * @return formHandler
     */
    public final String getFormHandler() {
        return this.formHandler;
    }

    /**
     *
     * @return formType
     */
    public final String getFormType() {
        return this.formType;
    }

    /**
     *
     * @return eventInstance
     */
    public final String getEventInstance() {
        return this.eventInstance;
    }

    /**
     *
     * @return eventId
     */
    public final String getEventId() {
        return this.eventId;
    }

    /**
     *
     * @return showCode
     */
    public final String getShowCode() {
        return this.showCode;
    }

    /**
     *
     * @return webinarType
     */
    public final String getWebinarType() {
        return this.webinarType;
    }

    /**
     *
     * @return referrerUrl
     */
    public final String getDefaultCampaignId() {
        return VonageConstants.ATTRIBUTION_DEFAULT_CAMPAIGN_ID;
    }

    /**
    *
    * @return uuid
    */
    public final String getUuid() {
        return uuid;
    }

    /**
     * @return pageContext
     */
    public final String getPageContext() {
        return pageContext;
      }

}
