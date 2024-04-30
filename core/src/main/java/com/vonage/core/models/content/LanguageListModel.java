package com.vonage.core.models.content;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.vonage.core.constants.VonageConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sling Model for Language
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LanguageListModel {

  /**
   * inject CurrentPage
   */
  @Inject
  private Page currentPage;

  /**
   * Languages list
   */
  private ArrayList<Map<String, String>> languageList = new ArrayList<>();

  /**
   * resource resolver
   */
  @Inject
  private ResourceResolver resourceResolver;

  /**
   * Externalizer
   */
  @Inject // OSGi Service
  private Externalizer externalizer;

  /**
   * current language
   */
  private String currentLanguage;

  /**
   * PostConstruct
   * @return
   */
  @PostConstruct
  protected final void init() {
    if (this.currentPage != null && StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH)) {
      Resource country = this.currentPage.getAbsoluteParent(3).adaptTo(Resource.class);
      if (country != null && country.hasChildren()) {
        Iterable<Resource> languages = country.getChildren();
        for (Resource language : languages) {
          Page languagePage = language.adaptTo(Page.class);
          if (languagePage != null) {
            String languagePath = languagePage.getPath();
            String homepagePath = languagePath + "/homepage";
            String pagePath = this.currentPage.getPath().replace(
              this.currentPage.getAbsoluteParent(4).getPath(),
              languagePath
            );
            String languageLink;
            if (resourceResolver.getResource(pagePath) != null) {
              languageLink = externalizer.publishLink(resourceResolver, "https", pagePath);
            } else {
              languageLink = externalizer.publishLink(resourceResolver, "https", homepagePath);
            }

            String displayLanguage = languagePage.getLanguage().getDisplayLanguage();
            String languageCode = languagePage.getLanguage().getLanguage().toLowerCase();
            if (this.currentPage.getPath().contains(language.getPath())) {
              currentLanguage = languageCode;
            }
            Map<String, String> site = new HashMap<>();
            site.put("name", "vng-lang-" + displayLanguage.toLowerCase());
            site.put("url", languageLink);
            site.put("language", languageCode);
            this.languageList.add(site);
          }
        }
      }
    }
  }

  /**
   * Get Languages list
   * @return languages array
   */
  public final ArrayList<Map<String, String>> getLanguageList() {
    return this.languageList;
  }

  /**
   * Boolean used to show the language dropdown
   * @return Language Mappings
   */
  public final boolean showLanguageDropdown() {
    return (this.languageList.size() > 1);
  }

  /**
   * Boolean used to show the language dropdown
   * @return Selected Language
   */
  public final String getCurrentLanguage() {
    return this.currentLanguage;
  }

}
