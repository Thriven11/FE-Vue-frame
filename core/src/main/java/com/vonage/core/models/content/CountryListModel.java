package com.vonage.core.models.content;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.vonage.core.constants.VonageConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Sling Model for Country List
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CountryListModel {

    /**
     * inject CurrentPage
     */
    @Inject
    private Page currentPage;

    /**
     * resourceResolver variable
     */
    @Inject
    private ResourceResolver resourceResolver;

    /**
     * Externalizer
     */
    @Inject // OSGi Service
    private Externalizer externalizer;

    /**
     * List of Countries of type generic List
     */
    private ArrayList<Map<String, String>> countriesList = new ArrayList<>();

    /**
     * current locale
     */
    private String currentLocale;

    /**
     * init method
     */
    @PostConstruct
    protected final void init() {
      String sitesPath = VonageConstants.SITES_ROOT_PATH;
      if (StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH)) {
        Iterator<Resource> countries = resourceResolver.getResource(sitesPath).listChildren();
        while (countries.hasNext()) {
          Resource country = countries.next();
          Page countryPage = country.adaptTo(Page.class);
          if (countryPage != null) {
            Iterator<Resource> languages = country.listChildren();
            while (languages.hasNext()) {
              Resource language = languages.next();
              Page languagePage = language.adaptTo(Page.class);
              if (languagePage != null) {
                String languagePath = language.getPath();
                String homepagePath = languagePath + "/homepage";
                String pagePath = this.currentPage.getPath().replace(
                  this.currentPage.getAbsoluteParent(4).getPath(),
                  languagePath
                );
                String url;
                if (resourceResolver.getResource(pagePath) != null) {
                  url = externalizer.publishLink(resourceResolver, "https", pagePath);
                } else {
                  url = externalizer.publishLink(resourceResolver, "https", homepagePath);
                }

                String countryCode = languagePage.getLanguage().getCountry();
                String languageName = languagePage.getLanguage().getDisplayLanguage().toLowerCase();
                String languageCode = languagePage.getLanguage().getLanguage().toLowerCase();
                String locale = languageCode + "-" + countryCode;

                if (this.currentPage.getPath().contains(language.getPath())) {
                  this.currentLocale = locale;
                }
                Map<String, String> localSite = new HashMap<>();

                localSite.put("url", url);
                localSite.put("country", "vng-" + countryCode);
                localSite.put("languageName", "vng-lang-" + languageName);
                localSite.put("language", languageCode);
                localSite.put("locale", locale);
                countriesList.add(localSite);
              }
            }
          }
        }
      }
    }

    /**
     * get Countries List
     *
     * @return countriesList
     */
    public final ArrayList<Map<String, String>> getCountriesList() {
        return countriesList;
    }

  /**
   * locale of the current page
   * @return currentLocal String
   */
  public final String getCurrentLocale() {
      return this.currentLocale;
    }

}
