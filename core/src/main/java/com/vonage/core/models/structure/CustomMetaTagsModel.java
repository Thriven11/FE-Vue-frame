package com.vonage.core.models.structure;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.day.cq.commons.Externalizer;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.resource.details.AssetDetails;
import com.google.gson.JsonObject;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.constants.VonageImageConstants;
import com.vonage.core.services.CareersService;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Sling Model for Swiftype Tags
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class CustomMetaTagsModel {

    /** string to remove constant */
    private static final String PREFIX_TO_REMOVE = "vonage:";

    /** og default type constant */
    private static final String DEFAULT_OG_TYPE = "website";

    /**
    * tagsArray variable
    */
    @ValueMapValue(name = "cq:tags", injectionStrategy = InjectionStrategy.OPTIONAL)
    private final List<String> pageTagsList = new ArrayList<>();

    /**
     * log variable
     */
    private static final Logger LOG = LoggerFactory.getLogger(CustomMetaTagsModel.class);

    /**
     * multimap variable
     */
    private final MultiMap multiMap = new MultiValueMap();
    /**
     * multiMapResources variable
     */
    private final MultiMap multiMapResources = new MultiValueMap();

    /**
     *
     * @return contentCategory
     */
    @ValueMapValue(name = "contentCategory", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String contentCategory;

    /**
     * @return datePublished
     */
    @ValueMapValue(name = "datePublished", injectionStrategy = InjectionStrategy.OPTIONAL)
    private Date datePublished;

    /**
     *  dateCreated from the main page instead of pageProperties
     */
    private Date dateCreated;

    /**
     * @return eventStartDate
     */
    @ValueMapValue(name = "eventStartDate", injectionStrategy = InjectionStrategy.OPTIONAL)
    private Date eventStartDate;

    /**
     * @return eventEndDate
     */
    @ValueMapValue(name = "eventEndDate", injectionStrategy = InjectionStrategy.OPTIONAL)
    private Date eventEndDate;

    /**
     * @return isTranslatedResource
     */
    @ValueMapValue(name = "isTranslatedResource", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String isTranslatedResource;

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
   * Careers Service
   */
  @OSGiService
  private CareersService careersService;

    /**
     * SlingHttpServletRequest
     */
    @Inject
    private SlingHttpServletRequest request;

    /**
     * category variable
     */
    private String templateCategory;

    /**
     * browser URL variable
     */
    private String browserUrl;

    /**
     * canonical URL variable
     */
    private String canonicalUrl;

    /**
     * Is it using the updated taxonomy and is all resources hosted on this domain
     */
    private String isUsingNewTaxonomy;

    /**
     * Page
     */
    @Inject
    private Page currentPage;

    /**
     * og type variable
     */
    private String ogType;

    /**
     * og type variable
     */
    private String ogTitle;

    /**
     * image resource
     */
    @ChildResource(name = "image", injectionStrategy = InjectionStrategy.OPTIONAL)
    private Resource image;

    /**
     * thumbnail image
     */
    private Map<String, String> thumbnailImage;

    /**
     * alternate pages
     */
    private ArrayList<Map<String, String>> alternatePages;

    /**
     *
     * featurePage variable
     */
    @ValueMapValue(name = "featurePage", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String featurePage;

    /**
    * init method
    */
    @PostConstruct
    protected final void init() {
        updateDateCreated();
        tagMultiMap();
        checkPageCategory();
        setThumbnailImage();
        setUrls();
        if (StringUtils.contains(this.currentPage.getPath(), VonageConstants.SITES_ROOT_PATH)) {
          setAlternatePages();
        }
        handleCareers();

        InheritanceValueMap inheritedProp = new HierarchyNodeInheritanceValueMap(currentPage.getContentResource());
        isUsingNewTaxonomy = inheritedProp.getInherited("loadingXF", String.class);
    }

    /**
     * Method to update the browser URL and og:title for career job positings
     * to populate the appropriate values which are unique for careers - VONDEV-1343
     */
    private void handleCareers() {

      String jobPostingId = request.getRequestPathInfo().getSelectorString();

      if (jobPostingId != null) {

        // Update the browser url with additional job posting id/selector value.
        if (StringUtils.contains(this.browserUrl, "/careers/job-details")) {
          this.browserUrl = this.browserUrl + AppConstants.SLASH + jobPostingId + AppConstants.SLASH;
        }

        // Update the og:title with the Job posting title
        final JsonObject jobDetailObject = careersService.getJobResourceAsJsonObject(jobPostingId);
        if (null != jobDetailObject) {
          this.ogTitle = jobDetailObject.get("title").getAsString();
        } else {
          this.ogTitle = "NO JOB FOUND";
        }
      }
    }

    /**
     * Method to set the browser url and canonical url
     * Based on VONDEV-1234, Use the dotcom url based on the below conditions.
     * 1. ONLY for articles section ( content type = articles )
     * 2. Only for English based sites AND ( language = en )
     * 3. Only if that particular article exists on the US site.
     * 4. IF The Translated Resource checkbox is enabled.
     */
    private void setUrls() {
      this.browserUrl = externalizer.publishLink(resourceResolver, "https", this.currentPage.getPath());
      String dotComResPath = this.currentPage.getPath()
                                .replace(currentPage.getLanguage().getCountry().toLowerCase()
                                + AppConstants.SLASH + currentPage.getLanguage().getLanguage(), "us/en");
      boolean isArticle = false;
      if (contentCategory != null) {
        isArticle = (contentCategory.equals("vonage:resources/article")
        || contentCategory.equals("vonage:content-type/article"));
      }

      Resource resource = resourceResolver.getResource(dotComResPath);

      if (isArticle
      && "en".equals(currentPage.getLanguage().getLanguage())
      && resource != null
      // && StringUtils.equals(isUsingNewTaxonomy, "true")
      && !StringUtils.equals(this.isTranslatedResource, "true")) {

        // this.canonicalUrl = externalizer.publishLink(resourceResolver, "https", dotComResPath);
        this.canonicalUrl = this.browserUrl;

      } else {

        this.canonicalUrl = this.browserUrl;
      }
    }

  /**
   * Find and set list of alternate versions of current page
   */
  private void setAlternatePages() {
      String sitesPath = VonageConstants.SITES_ROOT_PATH;
      Iterator<Resource> countries = resourceResolver.getResource(sitesPath).listChildren();
      ArrayList<Map<String, String>> alternates = new ArrayList<>();
      while (countries.hasNext()) {
        Resource country = countries.next();
        Page countryPage = country.adaptTo(Page.class);
        if (countryPage != null) {
          Iterator<Resource> languages = country.listChildren();
          while (languages.hasNext()) {
            Resource language = languages.next();
            Page languagePage = language.adaptTo(Page.class);
            Boolean isSameLanguage = StringUtils.contains(
              language.getName(),
              this.currentPage.getLanguage().getLanguage().toLowerCase()
            );
            Boolean isSameCountry = StringUtils.contains(
              country.getName(),
              this.currentPage.getLanguage().getCountry().toLowerCase()
            );
            if (languagePage != null) {
              if (!(isSameLanguage && isSameCountry)) {
                String alternatePath = this.currentPage.getPath().replace(
                    this.currentPage.getAbsoluteParent(4).getPath(),
                    language.getPath()
                  );
                if (resourceResolver.getResource(alternatePath) != null) {
                  String alternateUrl = externalizer.publishLink(resourceResolver, "https", alternatePath);
                  Map<String, String> locale = new HashMap<>();
                  locale.put("hreflang", language.getName() + "-" + country.getName());
                  locale.put("href", alternateUrl);
                  alternates.add(locale);
                }

              }
            }
          }
        }
      }
      this.alternatePages = alternates;
    }

    /**
     * thumbnail image
     * @throws RepositoryException
     */
    private void setThumbnailImage() {

      Map<String, String> defaultImgMap = new HashMap<>();
      Map<String, String> imageData = new HashMap<>();

      // Support OLD Tags
      defaultImgMap.put("vonage:resources/article",
      VonageImageConstants.VONAGE_DEFAULT_ARTICLES_IMG);
    defaultImgMap.put("vonage:resources/company-news/from-vonage",
      VonageImageConstants.VONAGE_DEFAULT_COMPANY_NEWS_IMG);
    defaultImgMap.put("vonage:resources/customer-stories",
      VonageImageConstants.VONAGE_DEFAULT_CUSTOMER_STORIES_IMG);
    defaultImgMap.put("vonage:resources/company-news/press-releases",
      VonageImageConstants.VONAGE_DEFAULT_PRESS_RELEASES_IMG);
    defaultImgMap.put("vonage:resources/publications/webinar",
      VonageImageConstants.VONAGE_DEFAULT_WEBINARS_IMG);
    defaultImgMap.put("vonage:resources/publications/whitepaper",
      VonageImageConstants.VONAGE_DEFAULT_WHITEPAPER_IMG);

      defaultImgMap.put("vonage:content-type/article",
        VonageImageConstants.VONAGE_DEFAULT_ARTICLES_IMG);
      defaultImgMap.put("vonage:content-type/vonage-stories",
        VonageImageConstants.VONAGE_DEFAULT_COMPANY_NEWS_IMG);
      defaultImgMap.put("vonage:content-type/customer-stories",
        VonageImageConstants.VONAGE_DEFAULT_CUSTOMER_STORIES_IMG);
      defaultImgMap.put("vonage:content-type/whitepapers-and-guides",
        VonageImageConstants.VONAGE_DEFAULT_WHITEPAPER_IMG);
      defaultImgMap.put("vonage:content-type/webinars",
        VonageImageConstants.VONAGE_DEFAULT_WEBINARS_IMG);

      String thumbnailImgPath = VonageImageConstants.VONAGE_DEFAULT_LOGO_IMG;

      // thumbnailImgPath = "/content/dam/vonage/us-en/resources/iconography/BAD-PATH-FOR-TESTING.png";

      try {
        if (this.image != null && this.image.adaptTo(Node.class).hasProperty("fileReference")) {
          thumbnailImgPath = this.image.getValueMap().get("fileReference").toString();
        } else if (defaultImgMap.containsKey(contentCategory)) {
          thumbnailImgPath = defaultImgMap.get(contentCategory);
        }

        Resource res = resourceResolver.getResource(thumbnailImgPath);
        String resolvedImagePath = "";
        String height = "";
        String width = "";
        if (res != null) {
          AssetDetails assetDetails = new AssetDetails(res);
          height = new Long(assetDetails.getHeight()).toString();
          width = new Long(assetDetails.getWidth()).toString();
          resolvedImagePath = externalizer.publishLink(resourceResolver, "https", thumbnailImgPath);
        }

        imageData.put("path", resolvedImagePath);
        imageData.put("height", height);
        imageData.put("width", width);

        // this.thumbnailHeight = height;
        // this.thumbnailWidth = width;

        LOG.info("---------- Image Dimensions START -----------");
        LOG.info("thumbnailImgPath: " + thumbnailImgPath);
        LOG.info("height: " + height);
        LOG.info("widrh: " + width);
        LOG.info("---------- Image Dimensions END -----------");

        // this.thumbnailImage = externalizer.publishLink(resourceResolver, "https", thumbnailImgPath);
        this.thumbnailImage = imageData;

      } catch (RepositoryException e) {
        LOG.error("There is a repository exception on CustomMetaTagModel", e);
      }
    }

    /**
    * multimap
    */
    private void tagMultiMap() {
        LOG.info("---------- Swiftype Map START -----------");
        /*
        * loop, replace string parts, add tag key and value pairs into multimap from the ArrayList
        */
        if (pageTagsList != null) {

            final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            String tagId;
            String tagValue;
            String tagKey;

            for (int i = 0; i < pageTagsList.size(); i++) {
                String tagString = pageTagsList.get(i);

                // TODO: Revisit the logic below, not sure if we need it
                // Added content-type based on VONDEV-1279 #1
                if (
                  (StringUtils.contains(tagString, AppConstants.SLASH)
                  || StringUtils.contains(tagString, AppConstants.COLON))
                  && !StringUtils.contains(tagString, "content-type")) {

                    final String[] parts = tagString.split("/");
                    final String[] recourceParts = tagString.split(":");
                    if (recourceParts != null && recourceParts.length > 1) {
                      LOG.info("tagString: " + tagString);
                      LOG.info("recourceParts: " + recourceParts[1]);
                    }
                    // for all tags
                    if (parts.length >= 2) {
                        tagId = parts[0] + "/" + parts[1];
                        Tag tag = tagManager.resolve(tagId);
                        tagValue = tag.getTitle();
                        tagKey = parts[0];
                        if (tagKey.startsWith(PREFIX_TO_REMOVE)) {
                            tagKey = tagKey.replace(PREFIX_TO_REMOVE, "");
                        }
                        multiMap.put(tagKey, tagValue);
                      multiMapResources.put(tagKey, recourceParts[1]);
                    }
                    // for the exception where a tag has 3 leafs
                    if (parts.length == 3) {
                        tagId = tagString;
                        Tag tag = tagManager.resolve(tagId);
                        tagValue = tag.getTitle();
                        tagKey = parts[1];
                      multiMap.put(tagKey, tagValue);
                      multiMapResources.put(tagKey, recourceParts[1]);
                    }
                }
            }
        }
        LOG.info("multiMap {}", multiMap);
        LOG.info("multiMapResources {}", multiMapResources);
        LOG.info("---------- Swiftype Map END -----------");
    }

    /**
    * check if we have a content category in the page - we use this for the content_section tag
    */
    private void checkPageCategory() {
        if (contentCategory != null) {
            // get for humage readable format from content category field string
            final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            String catTagId;
            String catTagValue;
            String catTagKey;

            final String[] categoryParts = contentCategory.split("/");
            // get the parent leaf and set as the template category
            if (categoryParts.length >= 1) {
                templateCategory = categoryParts[0];
                if (templateCategory.startsWith(PREFIX_TO_REMOVE)) {
                    templateCategory = templateCategory.replace(PREFIX_TO_REMOVE, "");
                }
            }


            // add a tag based on each leaf of the content category
            if (categoryParts.length >= 2) {
                catTagId = categoryParts[0] + "/" + categoryParts[1];
                Tag tag = tagManager.resolve(catTagId);
                catTagValue = tag.getTitle();
                catTagKey = categoryParts[0];
                if (catTagKey.startsWith(PREFIX_TO_REMOVE)) {
                    catTagKey = catTagKey.replace(PREFIX_TO_REMOVE, "");
                }
                multiMap.put(catTagKey, catTagValue);
            }
            if (categoryParts.length == 3) {
                catTagId = contentCategory;
                Tag tag = tagManager.resolve(catTagId);
                catTagValue = tag.getTitle();
                catTagKey = categoryParts[1];
                multiMap.put(catTagKey, catTagValue);
            }

            /*
            * check if the contentCategory field contains article or company-news
            */
            if (contentCategory.contains("article")) {
                ogType = "article";
            } else if (contentCategory.contains("company-news")) {
                ogType = "article";
            } else {
                ogType = DEFAULT_OG_TYPE;
            }

            /*
            * Support older pages - VONDEV-1323
            */
            if (contentCategory.contains("resources")) {
              multiMap.put("content_section", "resources");
              multiMapResources.put("content_section", "resources");
            }

            /*
            * if the first leaf in the category is resources, add content_section resources
            */
            if (contentCategory.contains("customer-stories")
                || contentCategory.contains("article")
                || contentCategory.contains("vonage-stories")
                || contentCategory.contains("webinars")
                || contentCategory.contains("whitepapers-and-guides")) {
                multiMap.put("content_section", "resources");
                multiMap.put("content-type", contentCategory.replace(PREFIX_TO_REMOVE, ""));
                multiMapResources.put("content_section", "resources");
                multiMapResources.put("content-type", contentCategory.replace(PREFIX_TO_REMOVE, ""));
            }

            /*
            * if the first leaf in the category is product, add content_section product
            */
            if (contentCategory.contains("product")) {
                multiMap.put("content_section", "products");
                multiMapResources.put("content_section", "products");

            }
            /*
            * if the first leaf in the category is partner, add content_section partners
            */
            if (contentCategory.contains("partner")) {
              multiMap.put("content_section", "partners");
              multiMapResources.put("content_section", "partners");
            }
            /*
            * if the first leaf in the category is events, add content_section events
            */
            if (contentCategory.contains("events")) {
              multiMap.put("content_section", "events");
              multiMapResources.put("content_section", "events");
            }
        } else {
            /**
             * Add Default value for og type
             */
            ogType = DEFAULT_OG_TYPE;
        }
    }


    /**
   * contentType
   */
  private void updateDateCreated() {
    if (currentPage != null) {
      dateCreated = resourceResolver.getResource(currentPage.getPath()).getValueMap().get("jcr:created", Date.class);
    }
  }

    /**
     * Getter pageTagsList
     * @return pageTagsList
     */
    public final List<String> getPageTagList() {
        return new ArrayList<>(pageTagsList);
    }

    /**
     * Getter multiMap
     * @return multiMap
     */
    public final Map<String, String> getMultiMap() {
        return multiMap;
    }

      /**
     * Getter multiMap
     * @return multiMap
     */
    public final Map<String, String> getMultiMapResources() {
      return multiMapResources;
  }


    /**
     * Get contentCategory
     *
     * @return contentCategory
     */
    public final String getContentCategory() {
        return contentCategory;
    }

    /**
     * Get templateCategory
     *
     * @return templateCategory
     */
    public final String getTemplateCategory() {
        return templateCategory;
    }

    /**
     * Get datePublished
     *
     * @return datePublished
     */
    public final Date getDatePublished() {
        return datePublished;
    }

    /**
     * Get eventStartDate
     *
     * @return eventStartDate
     */
    public final Date getEventStartDate() {
      return eventStartDate;
    }

     /**
     * Get dateCreated
     * @return dateCreated
     */
    public final Date getDateCreated() {
      return dateCreated;
    }

    /**
     * Get eventEndDate
     *
     * @return eventEndDate
     */
    public final Date getEventEndDate() {
      return eventEndDate;
    }

    /**
     * Get ogType
     *
     * @return ogType
     */
    public final String getOgType() {
        return ogType;
    }

    /**
     * Get ogType
     *
     * @return ogType
     */
    public final String getOgTitle() {
      return ogTitle;
  }

    /**
     * Image Resource getter
     * @return image resource
     */
    public final Map<String, String> getThumbnailImage() {
      return this.thumbnailImage;
    }

  /**
   * Alternate Pages getter
   * @return alternate pages array
   */
  public final ArrayList<Map<String, String>> getAlternatePages() {
      return this.alternatePages;
    }

    /**
     * Get Browser URL
     * @return browser url
     */
    public final String getBrowserUrl() {
        return this.browserUrl;
    }

     /**
     * Get canonical URL for the same page
     * @return canonical url
     */
    public final String getCanonicalUrl() {
      return this.canonicalUrl;
    }

    /**
     * Get featurePage
     * @return featurePage
     */
    public final String getFeaturePage() {
        return featurePage;
    }

}
