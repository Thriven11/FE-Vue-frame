package com.vonage.core.services;


import com.day.cq.wcm.api.Page;

/**
 * Base class to get page context
 *
 * @author Vonage
 *
 */
public interface PageContextService {
  /**
   * @param currentPage current Page object
   * @return Boolean isApiPage
   */
  Boolean getIsApiPage(Page currentPage);

  /**
   * @param currentPage current Page object
   * @return Boolean isUcPage
   */
  Boolean getIsUcPage(Page currentPage);

  /**
   * @param currentPage current Page object
   * @return Boolean isCcPage
   */
  Boolean getIsCcPage(Page currentPage);

  /**
   * @param currentPage current Page object
   * @return String
   */
  String getPageContext(Page currentPage);
}
