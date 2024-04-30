package com.vonage.core.models.structure.header;

import com.day.cq.wcm.api.Page;
import com.vonage.core.beans.CTA;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.models.CTAModel;
import com.vonage.core.models.injectors.annotations.CTAProperty;
import com.vonage.core.services.PageContextService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
//import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * Sling model for Main Navigation.
 */
@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MainNavigationComponentModel {
  /**
   * current page
   *
   */
  @Inject
  private Page currentPage;
  /**
   * Request
   */
  @Inject
  private SlingHttpServletRequest request;
  /**
   * pageContextService
   */
  @Inject
  private transient PageContextService pageContextService;
  /**
   * log var
   */
  private static final Logger LOG = LoggerFactory.getLogger(MainNavigationComponentModel.class);
  /**
   *
   * String isApiPage
   */
  private boolean isApiPage = false;
  /**
   *
   * String isUcPage
   */
  private boolean isUcPage = false;
  /**
   *
   * String isCcPage
   */
  private boolean isCcPage = false;
  /**
   *
   * page contentCategory var
   */
  private String pageContentCategory;
  /**
   *
   * page language var
   */
  private String pageLanguage;
  /**
   *
   * page country var
   */
  private String pageCountry;
  /**
   *
   * currentResource
   */
  @Self
  private Resource resource;
  /**
   * Link List
   *
   */
  @ChildResource(name = "primaryNavLinks", injectionStrategy = InjectionStrategy.OPTIONAL)
  private List<PrimaryNavigationComponentModel> primaryNavList;
  /**
   *
   * cta
   */
  @CTAProperty
  @Via("resource")
  @Optional
  private CTA link;
  /**
   * Tokbox condition path
   */
  private static final String API_TOKBOX_ID_PATH = VonageConstants.API_TOKBOX_ID_PATH;
  /**
   * secondary Cta
   */
  @ChildResource(name = "secondaryCta")
  private Resource secondaryCta;
  /**
   * UC secondary
   */
  @ChildResource(name = "ucSecondaryCta")
  private Resource ucSecondaryCta;
  /**
   * CC secondary
   */
  @ChildResource(name = "ccSecondaryCta")
  private Resource ccSecondaryCta;
  /**
   * API Tokbox secondary
   */
  @ChildResource(name = "apiTokboxSecondaryCta")
  private Resource apiTokboxSecondaryCta;
  /**
   * API Nexmo secondary
   */
  @ChildResource(name = "apiNexmoSecondaryCta")
  private Resource apiNexmoSecondaryCta;
  /**
   * UC Contact Cta Label
   */
  @ChildResource(name = "ucCtaLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String ucCtaLabel;
  /**
   * CC Contact Cta Label
   */
  @ChildResource(name = "ccCtaLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String ccCtaLabel;
  /**
   * API Contact Cta Label
   */
  @ChildResource(name = "apiCtaLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String apiCtaLabel;
  /**
   * UC Primary
   */
  @ChildResource(name = "ucPrimary", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String ucPrimary;
  /**
   * CC Primary
   */
  @ChildResource(name = "ccPrimary", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String ccPrimary;
  /**
   * Api Primary
   */
  @ChildResource(name = "apiPrimary", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String apiPrimary;

  /**
   * init method
   */
  @PostConstruct
  protected final void init() {
    this.isApiPage = pageContextService.getIsApiPage(currentPage);
    this.isUcPage = pageContextService.getIsUcPage(currentPage);
    this.isCcPage = pageContextService.getIsCcPage(currentPage);
    /**
     * Page Language and country
     */
    if (currentPage.getLanguage().getLanguage() != null) {
      this.pageLanguage = currentPage.getLanguage().getLanguage();
      LOG.info("pageLanguage: " + this.pageLanguage);
    }
    if (currentPage.getLanguage(false).getCountry() != null) {
      this.pageCountry = currentPage.getLanguage(false).getCountry();
      LOG.info("pageCountry: " + this.pageCountry);
    }
  }

  /**
   * Link List
   *
   * @return primaryNavList
   */
  public final List<PrimaryNavigationComponentModel> getPrimaryNavList() {
    return primaryNavList;
  }

  /**
   *
   * @return cta
   */
  public final CTA getLink() {
    return link;
  }

  /**
   * Getter alternativeCtaLabel Used on primary contact label
   *
   * @return alternativeCtaLabel
   */
  public final String getAlternativeCtaLabel() {
    if (this.isApiPage && this.apiCtaLabel != null) {
      return this.apiCtaLabel;
    } else if (this.isCcPage && this.ccCtaLabel != null) {
      return this.ccCtaLabel;
    } else if (this.isUcPage && this.ucCtaLabel != null) {
      return this.ucCtaLabel;
    } else {
      return null;
    }
  }

  /**
   * @return contactUsFormUrl
   */
  public final String getContactUsFormUrl() {
    if (this.isApiPage) {
      if (apiPrimary != null) {
        return apiPrimary;
      } else {
        return currentPage.getAbsoluteParent(4).getPath() + VonageConstants.API_CONTACT_US_FORM;
      }
    } else if (this.isUcPage && this.pageCountry == "US") {
      if (ucPrimary != null) {
        return ucPrimary;
      } else {
        return currentPage.getAbsoluteParent(4).getPath() + VonageConstants.UC_CONTACT_US_FORM;
      }
    } else if (this.isCcPage && this.pageCountry == "US") {
      if (ccPrimary != null) {
        return ccPrimary;
      } else {
        return currentPage.getAbsoluteParent(4).getPath() + VonageConstants.CC_CONTACT_US_FORM;
      }
    } else if (this.isUcPage && this.pageCountry == "GB") {
      return currentPage.getAbsoluteParent(4).getPath() + VonageConstants.UK_UC_CONTACT_US_FORM;
    } else {
      return this.getLink().getHref();
    }
  }

  /**
   * Getter finalSecondaryCta
   *
   * @return finalSecondaryCta
   */
  public final CTAModel getFinalSecondaryCta() {
    if (this.isApiPage) {
      if (StringUtils.contains(this.currentPage.getPath(), API_TOKBOX_ID_PATH)) {
        if (this.apiTokboxSecondaryCta != null) {
          return this.apiTokboxSecondaryCta.adaptTo(CTAModel.class);
        } else {
          return null;
        }
      } else {
        if (this.apiNexmoSecondaryCta != null) {
          return this.apiNexmoSecondaryCta.adaptTo(CTAModel.class);
        } else {
          return null;
        }
      }
    } else if (this.isCcPage && this.ccSecondaryCta != null) {
      return this.ccSecondaryCta.adaptTo(CTAModel.class);
    } else if (this.isUcPage && this.ucSecondaryCta != null) {
      return this.ucSecondaryCta.adaptTo(CTAModel.class);
    } else {
      if (this.secondaryCta != null) {
        return this.secondaryCta.adaptTo(CTAModel.class);
      } else {
        return null;
      }
    }
  }

  /**
   * Getter secondaryCta
   *
   * @return secondaryCta
   */
  public final CTAModel getSecondaryCta() {
    if (this.secondaryCta != null) {
      return this.secondaryCta.adaptTo(CTAModel.class);
    } else {
      return null;
    }
  }

  /**
   * Getter ucSecondaryCta
   *
   * @return ucSecondaryCta
   */
  public final CTAModel getUcSecondaryCta() {
    if (this.ucSecondaryCta != null) {
      return this.ucSecondaryCta.adaptTo(CTAModel.class);
    } else {
      return null;
    }
  }

  /**
   * Getter ccSecondaryCta
   *
   * @return ccSecondaryCta
   */
  public final CTAModel getCcSecondaryCta() {
    if (this.ccSecondaryCta != null) {
      return this.ccSecondaryCta.adaptTo(CTAModel.class);
    } else {
      return null;
    }
  }

  /**
   * Getter apiNexmoSecondaryCta
   *
   * @return apiNexmoSecondaryCta
   */
  public final CTAModel getApiNexmoSecondaryCta() {
    if (this.apiNexmoSecondaryCta != null) {
      return this.apiNexmoSecondaryCta.adaptTo(CTAModel.class);
    } else {
      return null;
    }
  }

  /**
   * Getter apiTokboxSecondaryCta
   *
   * @return apiTokboxecondaryCta
   */
  public final CTAModel getApiTokboxSecondaryCta() {
    if (this.apiTokboxSecondaryCta != null) {
      return this.apiTokboxSecondaryCta.adaptTo(CTAModel.class);
    } else {
      return null;
    }
  }

  /**
   * returns abbandonedCartLinkHref from request attributes
   *
   * @return abbandonedCartLinkHref
   */
  public final String getAbbandonedCartLinkHref() {
    return (String) request.getAttribute("abbandonedCartLinkHref");
  }
}
