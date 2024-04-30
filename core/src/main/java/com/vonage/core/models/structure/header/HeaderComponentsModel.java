package com.vonage.core.models.structure.header;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.vonage.core.beans.CTA;
import com.vonage.core.models.injectors.annotations.CTAProperty;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for The header components
 */
@Model(adaptables = Resource.class)
public class HeaderComponentsModel {

    /**
     *
     * skip button label
     */
    @ValueMapValue(name = "skipButtonLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String skipButtonLabel;

    /**
     *
     * main menu label
     */
    @ValueMapValue(name = "mainMenuLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String mainMenuLabel;

    /**
     *
     * abandoned cart link
     */
    @CTAProperty
    private CTA link;

    /**
     * abandonedCartModalHeader
     */
    @ValueMapValue(name = "abandonedCartModalHeader", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String abandonedCartModalHeader;

    /**
     * abandonedCartModalText
     */
    @ValueMapValue(name = "abandonedCartModalText", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String abandonedCartModalText;

    /**
     * abandonedCartModalLinkText
     */
    @ValueMapValue(name = "abandonedCartModalLinkText", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String abandonedCartModalLinkText;

    /**
     * abandonedCartModalLinkUrl
     */
    @ValueMapValue(name = "abandonedCartModalLinkUrl", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String abandonedCartModalLinkUrl;

    /**
     * parameters for sending via requestAttributes
     */
    private Map<String, String> parameters;

    /**
     * Initialize component
     */
    @PostConstruct
    public final void init() {
        parameters = new HashMap<>();
        parameters.put("abbandonedCartLinkHref", link.getHref());
    }

    /**
     *
     * @return skipButtonLabel
     */
    public final String getSkipButtonLabel() {
        return skipButtonLabel;
    }

    /**
     *
     * @return mainMenuLabel
     */
    public final String getMainMenuLabel() {
        return mainMenuLabel;
    }

    /**
     *
     * @return link
     */
    public final CTA getLink() {
        return link;
    }

    /**
     *
     * abandonedCartModalHeader
     * @return abandonedCartModalHeader
     */
    public final String getAbandonedCartModalHeader() {
        return abandonedCartModalHeader;
    }

    /**
     *
     * abandonedCartModalText
     * @return abandonedCartModalText
     */
    public final String getAbandonedCartModalText() {
        return abandonedCartModalText;
    }

    /**
     *
     * linkCtaText
     * @return linkCtaText
     */
    public final String getAbandonedCartModalLinkText() {
        return abandonedCartModalLinkText;
    }

    /**
     *
     * linkCtaUrl
     * @return linkCtaUrl
     */
    public final String getAbandonedCartModalLinkUrl() {
        return abandonedCartModalLinkUrl;
    }

    /**
     *
     * @return parameters
     */
    public final Map<String, String> getParameters() {
        return parameters;
    }
}
