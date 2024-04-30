package com.vonage.core.models.structure.analytics;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.util.Text;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//import org.apache.sling.api.resource.ValueMap;

/**
 * AnalyticsDataModel class
 *
 * @author Vonage
 *
 */
@Model(adaptables = { Resource.class, Page.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AnalyticsDataModel {

    /**
     * The Constant LOGGER.
     */

    public static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsDataModel.class);

    /** string to remove constant */
    private static final String PREFIX_TO_REMOVE = "vonage:";

    /**
     * currentPage variable
     */
    @Inject
    private Page currentPage;

    /**
     * resourcePage variable
     */
    @Inject
    private Page resourcePage;

    /**
     * Title variable
     */
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = JcrConstants.JCR_TITLE)
    private String title;

    /**
     * analyticsTitle variable
     */
    private String analyticsTitle;

    /**
     * isNotLandingPage
     */
    @ValueMapValue(name = "isNotLandingPage", injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean isNotLandingPage;

    /**
     * isLandingPage
     */
    @ValueMapValue(name = "isLandingPage", injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean isLandingPage;

    /**
     * page info bean variable
     */
    private PageInfoBean pageInfoBean;

    /**
     * Content Bean Variable
     */
    private ContentBean contentBean;

    /**
     * Page Hierarchy Variable
     */
    private List<String> pageHierarchy;

  /**
   * Published Date
   */
  @ValueMapValue(name = "datePublished", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String datePublished;

  /**
   * Author
   */
  @ValueMapValue(name = "author", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String author;

  /**
   * Content Category
   */
  @ValueMapValue(name = "contentCategory", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String contentCategory;

    /**
     * BIZ Variable
     */
    private static final String BIZ = "biz";

    /**
     * MKTG variable
     */
    private static final String MKTG = "mktg";

    /**
     * BIZ_MKTG variable
     */
    private static final String BIZ_MKTG = "biz:mktg";

    /**
     * ECOM variable
     */
    private static final String ECOM = "ecom";

    /**
     * ECOM variable
     */
    //private static final String ECOM_SITE_IDENTIFIER = "ecom:buy";

    /**
     * ECOM variable
     */
    private static final String ECOM_SITE = "ecommerce";

    /**
     * ECOM APP VERSION
     */
    private static final String ECOM_APP_VERSION = "ecommerce-v1";

    /**
     * MAIN_SITE variable
     */
    private static final String MAIN_SITE = "main site";

    /**
     * LANDING_PAGES_SITE variable
     */
    private static final String LANDING_PAGES_SITE = "landing pages";

    /**
     * new era variable
     */
    private static final String NEW_ERA = "newera";

    /**
     * DESKTOP variable
     */
    private static final String DESKTOP = "desktop";

    /**
     * PROSPECT variable
     */
    private static final String PROSPECT = "prospect";

    /**
     * US_EN_PATH variable
     */
    private static final String US_EN_PATH = "/content/vonage/live-copies/us/en/";

    /**
     * US_EN_DIR variable
     */
    private static final String US_EN_DIR = "/us/en/";

    /**
     * BNS_PATH variable
     */
    private static final String BNS_PATH = "/schedule";

  /**
   * resource resolver
   */
  @Inject
  private ResourceResolver resourceResolver;

  /**
   * tagMap variable
   */
  private HashMap<String, String> tagMap = new HashMap<String, String>();;

  /**
   * Content Tags
   */
  @ValueMapValue(name = "cq:tags", injectionStrategy = InjectionStrategy.OPTIONAL)
  private final List<String> pageTagsList = new ArrayList<>();

  /**
   *
   * currentResource
   */
  @Self
  private Resource resource;

  /**
   * ecommPartnerType
   */
  private String ecommPartnerType;

  /**
   * init method
   */
  @PostConstruct
  protected final void init() {

    InheritanceValueMap inheritedProp = new HierarchyNodeInheritanceValueMap(currentPage.adaptTo(Resource.class));
    ecommPartnerType = inheritedProp.getInherited("ecommPartnerType", String.class);
    if (StringUtils.contains(this.currentPage.getPath(), VonageConstants.SITES_ROOT_PATH)) {
      setAnalyticsTitle();
    }
    createPageInfoBean();
    createContentBean();
    setContentTags();
    setAuthor();
    setContentType();
    setDatePublished();
  }

  /**
   * Get Author
   */
   final void setAuthor() {
    if (author != null) {
      if (author.startsWith(PREFIX_TO_REMOVE)) {
        author = author.replace(PREFIX_TO_REMOVE, "");
      }
      final String[] parts = author.split("/");

      if (parts.length > 1) {
        author = parts[1];
      } else {
        author = "";
      }
    } else {
      author = "";
    }
  }

  /**
   * Get Content Type
   */
  final void setContentType() {
    if (contentCategory != null) {
      if (contentCategory.startsWith(PREFIX_TO_REMOVE)) {
        contentCategory = contentCategory.replace(PREFIX_TO_REMOVE, "");
      }
      final String[] parts = contentCategory.split("/");

      if (parts.length > 1) {
        contentCategory = parts[1];
      } else {
        contentCategory = "";
      }
    } else {
      contentCategory = "";
    }
  }

  /**
   * Set formatted publish date
   */
  final void setDatePublished() {
    if (datePublished != null && datePublished.length() >= 10) {
      datePublished = datePublished.substring(0, 10);
      String[] parts = datePublished.split("-");
      datePublished = parts[1] + "/" + parts[2] + "/" + parts[0];
    } else {
      datePublished = "";
    }
  }

    /**
    * Content Tagging
    */
    final void setContentTags() {
        LOGGER.info("---------- Content Tags START -----------");
        /*
        * loop, replace string parts, add tag key and value pairs into multimap from the ArrayList
        */

        if (pageTagsList != null) {

            final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            String tagId;
            String tagValue;
            String tagKey;

            final String[] tagsToConcatenate = {
                "professional-role",
                "topic",
                "industry",
                "business-size",
                "product",
            };

            for (int i = 0; i < pageTagsList.size(); i++) {
                final String tagString = pageTagsList.get(i);

                final String[] parts = tagString.split("/");
                LOGGER.info("tagString: " + tagString);
                // for all tags
                if (parts.length >= 2) {
                    tagId = parts[0] + "/" + parts[1];
                    final Tag tag = tagManager.resolve(tagId);
                    tagValue = tag.getTitle().toLowerCase();
                    tagKey = parts[0].toLowerCase();
                    if (tagKey.startsWith(PREFIX_TO_REMOVE)) {
                        tagKey = tagKey.replace(PREFIX_TO_REMOVE, "");
                    }

                    boolean concatenate = Arrays.stream(tagsToConcatenate).anyMatch(tagKey::equals);

                    if (!concatenate || !tagMap.containsKey(tagKey)) {
                        if (concatenate) {
                            tagValue = " | " + tagValue + " | ";
                        }
                        tagMap.put(tagKey, tagValue);
                    } else {
                        tagMap.put(tagKey, tagMap.get(tagKey) + tagValue + " | ");
                    }
                }
            }
        }
        LOGGER.info("tagMap {}", tagMap);
        LOGGER.info("---------- Content Tags END -----------");
    }

    /**
     * Set the Analytics title
     */
    final void setAnalyticsTitle() {

      // Local Vars
      String thisNodePath = null;
      String usEnNodePath = null;
      String usEnTitle = null;
      String targetDir = null;

      // get current path
      try {
        Node currentPageNode = this.currentPage.adaptTo(Node.class);
        if (currentPageNode != null) {
            thisNodePath = currentPageNode.getPath();
        }
      } catch (Exception e) {
          e.printStackTrace();
      }

      // If the node path does contain /us/en/
      // replace the path string at the 4th level
      if (thisNodePath.contains(US_EN_PATH)) {
        usEnNodePath = thisNodePath;
      } else {
        String pathToCheck = Text.getAbsoluteParent(thisNodePath, 4);
        usEnNodePath = thisNodePath.replace(pathToCheck, US_EN_PATH);
      }

      // Check if we have a en us page path set
      // Then get the jcr:title
      if (usEnNodePath != null) {
          String pathtoFind = usEnNodePath + "/jcr:content/";

          Resource usEnResource = resourceResolver.getResource(pathtoFind);
          if (usEnResource != null) {
            try {
              Node usEnPageNode = usEnResource.adaptTo(Node.class);
              if (usEnPageNode != null && usEnPageNode.hasProperty(JcrConstants.JCR_TITLE)) {
                  usEnTitle = usEnPageNode.getProperty(JcrConstants.JCR_TITLE).getString();
              }
            } catch (Exception e) {
                e.printStackTrace();
            }
          }
      }

      // If we have a us/en title set, use that lse use current page title
      if (usEnTitle != null && usEnTitle.length() > 0) {
          this.analyticsTitle = usEnTitle;
      } else {
          this.analyticsTitle = title;
      }
      // remove pipe Vonage - request from Caroline
      this.analyticsTitle = this.analyticsTitle.replace(" | Vonage", "");
    }


    /**
     * Create content bean
     */
    final void createContentBean() {
        contentBean = new ContentBean();
        if (this.analyticsTitle != null) {
          contentBean.setName(this.analyticsTitle.toLowerCase());
        } else if (StringUtils.isNotEmpty(title)) {
            contentBean.setName(title.toLowerCase());
        } else {
          LOGGER.info("NO TITLE SET");
        }
    }

    /**
     * Create page info object
     */
    final void createPageInfoBean() {
        pageInfoBean = new PageInfoBean();
        if (null != resourcePage.getAbsoluteParent(1)) {
            pageHierarchy = this.getPageHierarchy();
            pageInfoBean.setSubCategory1(getSubCategoryValues(0));
            pageInfoBean.setSubCategory2(getSubCategoryValues(1));
            pageInfoBean.setSubCategory3(getSubCategoryValues(2));
            pageInfoBean.setPageName(BIZ_MKTG + AppConstants.COLON + String.join(AppConstants.COLON, pageHierarchy));
        }
        pageInfoBean.setLanguage(resourcePage.getLanguage().getLanguage());
        pageInfoBean.setCountry(resourcePage.getLanguage().getCountry());
        pageInfoBean.setLob(BIZ);
        pageInfoBean.setFunctionDept(MKTG);
        pageInfoBean.setPrimaryCategory(BIZ_MKTG);
        // If using the campaign template and the isNotLandingPage property is not set
        // If the isLandingPage property is set
        // Then define as Landing Page, else it is main site
        if ((StringUtils.equalsIgnoreCase(currentPage.getTemplate().getPath(),
          VonageConstants.CAMPAIGN_PAGE_TEMPLATE_PATH)
          && !isNotLandingPage)
          || isLandingPage) {
          pageInfoBean.setSiteIdentifier(LANDING_PAGES_SITE);
        // If Ecomm template
        } else if (StringUtils.equalsIgnoreCase(currentPage.getTemplate().getPath(),
          VonageConstants.ECOMM_PAGE_TEMPLATE_PATH)) {
          pageInfoBean.setSiteIdentifier(ECOM_SITE);
          // If BNS pages
          if (StringUtils.contains(currentPage.getPath(), BNS_PATH)) {
            pageInfoBean.setPageName(BIZ_MKTG + AppConstants.COLON
            + String.join(AppConstants.COLON, pageHierarchy));
            pageInfoBean.setPrimaryCategory(BIZ_MKTG + AppConstants.COLON + getSubCategoryValues(0));
            pageInfoBean.setSubCategory1("");
            pageInfoBean.setSubCategory2(getSubCategoryValues(1));
            //pageInfoBean.setSubCategory3(getSubCategoryValues(3));
          } else {
            pageInfoBean.setPageName(BIZ_MKTG + AppConstants.COLON + ECOM + AppConstants.COLON
            + String.join(AppConstants.COLON, pageHierarchy));
            pageInfoBean.setPrimaryCategory(BIZ_MKTG + AppConstants.COLON
            + ECOM + AppConstants.COLON + getSubCategoryValues(0));
          }
          // Update the App Version if it is partner flow in ecomm
          if (StringUtils.isNotEmpty(this.ecommPartnerType)) {
            pageInfoBean.setAppVersion(ECOM_APP_VERSION + AppConstants.HYPHEN + this.ecommPartnerType.toLowerCase());
          } else {
            pageInfoBean.setAppVersion(ECOM_APP_VERSION);
          }

        } else {
          pageInfoBean.setSiteIdentifier(MAIN_SITE);
        }
    }

    /**
     * Get Page Hierarchy based regular vs partner flow for VONDEV-1053
     * @return page name for ith level
     */
    private List getPageHierarchy() {
      String publishPageUrl = resourcePage.getPath()
      .replaceAll(resourcePage.getAbsoluteParent(1).getPath()
      + "/live-copies/[a-z]{2}/[a-z]{2}/(.*)", "$1")
      .replaceAll(resourcePage.getAbsoluteParent(1).getPath()
      + "/[a-z]{2}-[a-z]{2}/(.*)", "$1");
      // If Partner flow in Ecomm, remove the additional root context from the URL
      if (StringUtils.isNotEmpty(this.ecommPartnerType)) {
        publishPageUrl = publishPageUrl.substring(publishPageUrl.indexOf("/") + 1, publishPageUrl.length());
      }
      pageHierarchy = Arrays.asList(publishPageUrl.split("/"));
      return pageHierarchy;
    }

    /**
     * Get Sub category values
     *
     * @param i page levelnumber
     * @return page name for ith level
     */
    private String getSubCategoryValues(final int i) {
        if (pageHierarchy.size() >= i + 1) {
            return pageHierarchy.get(i);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Getter DESKTOP
     *
     * @return DESKTOP
     */
    public static String getDESKTOP() {
        return DESKTOP;
    }

    /**
     * Getter PROSPECT
     *
     * @return PROSPECT
     */
    public static String getPROSPECT() {
        return PROSPECT;
    }

    /**
     * Getter pageInfoBean
     *
     * @return pageInfoBean
     */
    public final PageInfoBean getPageInfoBean() {
        return pageInfoBean;
    }

    /**
     * Getter contentBean
     *
     * @return contentBean
     */
    public final ContentBean getContentBean() {
        return contentBean;
    }

    /**
     *
     * @return date published
     */
    public final String getDatePublished() {
        return this.datePublished;
    }

    /**
     *
     * @return author
     */
    public final String getAuthor() {
        return this.author;
    }

    /**
     *
     * @return content category
     */
    public final String getContentCategory() {
        return this.contentCategory;
    }

    /**
     * Getter tagMap
     * @return tagMap
     */
    public final HashMap<String, String> getTagMap() {
        return tagMap;
    }

    /**
    * Getter title
    * @return title
    */
    public final String getTitle() {
      return title;
    }

    /**
    * Getter analyticsTitle
    * @return analyticsTitle
    */
    public final String getAnalyticsTitle() {
        return analyticsTitle;
    }

    /**
     * Getter isLandingPage
     *
     * @return isLandingPage
     */
    public final boolean getIsLandingPage() {
      return isLandingPage;
    }

    /**
     * Getter isNotLandingPage
     *
     * @return isNotLandingPage
     */
    public final boolean getIsNotLandingPage() {
      return isNotLandingPage;
    }

    /**
     * Getter ecommPartnerType
     *
     * @return ecommPartnerType
     */
    public final String getEcommPartnerType() {
      return ecommPartnerType;
    }

}
