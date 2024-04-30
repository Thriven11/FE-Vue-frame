package com.vonage.core.services.impl;

import com.day.cq.wcm.api.Page;
import com.vonage.core.services.PageContextService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation class for Page Context Service
 *
 * @author Vonage
 *
 */
@Component(immediate = true, service = PageContextService.class)
public class PageContextServiceImpl implements PageContextService {

  /** api tag string constant */
  private static final String API_TAG_STRING = "vonage:product/communications-apis";

  /** unified communications tag string constant */
  private static final String UC_TAG_STRING = "vonage:product/unified-communications";

  /** contact centers tag string constant */
  private static final String CC_TAG_STRING = "vonage:product/contact-centers";

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(PageContextServiceImpl.class);


  /**
   * @param currentPage current Page object
   * Get page taxonomy tags
   * @return List pageTaxTags
   */
  private List getPageTaxTags(final Page currentPage) {
    ValueMap pageProperties = currentPage.getProperties();
    List<String> pageTaxTags = null;
    String[] tagsString = (String[]) pageProperties.get("cq:tags");

    if (tagsString != null) {
      for (int i = 0; i < tagsString.length; i++) {
        LOGGER.debug("tag: " + tagsString[i]);
      }
      /** add the tags to pageTaxTags var */
      pageTaxTags = Arrays.asList(tagsString);
      LOGGER.debug("pageTaxTags: " + pageTaxTags);
    } else {
      LOGGER.debug("tags are empty");
    }
    return pageTaxTags;
  }

  /**
   * @param currentPage current Page object
   * @return Boolean getIsApiPage
   */
  @Override
  public final Boolean getIsApiPage(final Page currentPage) {
    List pageTaxTags = getPageTaxTags(currentPage);
    Boolean isApiPage = false;
    if (
      StringUtils.contains(currentPage.getPath(), "/communications-apis")
        || (pageTaxTags != null && pageTaxTags.contains(API_TAG_STRING))
    ) {
      isApiPage = true;
    }
    return isApiPage;
  }

  /**
   * @param currentPage current Page object
   * @return Boolean getIsUcPage
   */
  @Override
  public final Boolean getIsUcPage(final Page currentPage) {
    List pageTaxTags = getPageTaxTags(currentPage);
    Boolean isUcPage = false;
    if (
      StringUtils.contains(currentPage.getPath(), "/unified-communications")
        || (pageTaxTags != null && pageTaxTags.contains(UC_TAG_STRING))
    ) {
      isUcPage = true;
    }
    return isUcPage;
  }

  /**
   * @return Boolean getIsCcPage
   */
  @Override
  public final Boolean getIsCcPage(final Page currentPage) {
    List pageTaxTags = getPageTaxTags(currentPage);
    Boolean isCcPage = false;
    if (
      StringUtils.contains(currentPage.getPath(), "/contact-centers")
        || (pageTaxTags != null && pageTaxTags.contains(CC_TAG_STRING))
    ) {
      isCcPage = true;
    }
    return isCcPage;
  }

  /**
   *
   * @param currentPage current Page object
   * @return String
   */
  @Override
  public final String getPageContext(final Page currentPage) {
    if (this.getIsApiPage(currentPage)) {
      return "API";
    } else if (this.getIsUcPage(currentPage)) {
      return "UC";
    } else if (this.getIsCcPage(currentPage)) {
      return "CC";
    } else {
      return "generic";
    }
  }
}
