package com.vonage.core.constants;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * Common Constants Class for Vonage specific Constants.
 */
public final class VonageConstants {

    /**
     * Static class should not be initialized.
     */
    private VonageConstants() {
    }

    /**
     * The Constant WRITE_SUBSERVICE- used for getting service resource resolver.
     */
    public static final String WRITE_SUBSERVICE = "write-sub-service";

    /**
     * The Constant READ_SUBSERVICE- used for getting service resource resolver with
     * read only access.
     */
    public static final String READ_SUBSERVICE = "read-sub-service";

    /**
     * Long Date Format (Do not change it, create another format if required)
     */
    public static final FastDateFormat DATE_FORMAT_LONG = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * Protocol for replication agent transport URI that triggers this transport
     * handler.
     */
    public static final String CF_PROTOCOL = "cloudflare://";

    /**
     * Key parameter name
     */
    public static final String CF_PARAM_KEY = "X-Auth-Key";

    /**
     * email parameter name
     */
    public static final String CF_PARAM_EMAIL = "X-Auth-Email";

    /**
     * files parameter name
     */
    public static final String CF_PARAM_FILES = "files";

    /**
     * CF test endpoint
     */
    public static final String CF_TEST_ENDPOINT = "https://%s/client/v4/zones";

    /**********************************************************
     ******************* Constants for Sitemap ****************
     **********************************************************/

    /**
     * Factory ID
     */
    public static final String SITEMAP_FACTORY_PID = "com.vonage.core.schedulers.impl.SitemapSchedulerFactoryImpl";

    /**
     * Target Path Prop name
     */
    public static final String PN_TARGETPATH = "targetPath";

    /**
     * Sitemap root Prop name
     */
    public static final String PN_SITEMAP_ROOT = "sitemapRoot";

    /**
     * URL rewrites Prop name
     */
    public static final String PN_URL_REWRITES = "urlRewrites";

    /**
     * mime types Prop name
     */
    public static final String PN_MIMETYPES = "mimeTypes";

    /**
     * Exclude paths Prop name
     */
    public static final String PN_EXCLUDEPATHS = "excludePaths";

    /**
     * Param name for re-generating image site-map(s).
     */
    public static final String PARAM_IMAGE = "image";

    /**
     * Param name for default Title.
     */
    public static final String AUTHOR_DEFAULT_TITLE = "TITLE";

    /**
     * Param name for default Description.
     */
    public static final String AUTHOR_DEFAULT_DESCRIPTION = "";

    /**
     * The constant for default user group to act as reviewers for workflow approval
     * process
     */
    public static final String DEFAULT_REVIEW_GROUP = "content-approvers";

    /**
     * Constant for acs commons country list path
     */
    public static final String COUNTRIES_LIST_PATH = "/etc/acs-commons/lists/vonage/countries-list";

    /**
     * Constant for acs commons languages list path
     */
    public static final String LANGUAGES_LIST_PATH = "/etc/acs-commons/lists/vonage/languages-list";

    /**
     * Timeout for http requests
     */
    public static final int HTTP_TIMEOUT = 15;

    /**
     * sites root path
     */
    public static final String SITES_ROOT_PATH = "/content/vonage/live-copies";

     /**
     * staging path
     */
    public static final String SITES_STAGING_PATH = "staging-www";

    /**
     * XF root path
     */
    public static final String XF_ROOT_PATH = "/content/experience-fragments/vonage";

    /**
     * API Contact US Form
     */
    public static final String API_CONTACT_US_FORM = "/communications-apis/contact-api.html";

    /**
     * UC Contact US Form
     */
    public static final String UC_CONTACT_US_FORM = "/unified-communications/contact-uc.html";

    /**
     * CC Contact US Form
     */
    public static final String CC_CONTACT_US_FORM = "/contact-centers/contact-cc.html";

    /**
     * UK UC Contact US Form
     */
    public static final String UK_UC_CONTACT_US_FORM = "/unified-communications/contact-uc.html";

    /**
     * API Nexmo
     */
    public static final String API_NEXMO_SIGN_UP_LINK = "https://dashboard.nexmo.com/sign-up";

    /**
     * API Tokbox
     */
    public static final String API_TOKBOX_SIGN_UP_LINK = "https://tokbox.com/account/user/signup";

    /**
     * API Tokbox identifier path
     */
    public static final String API_TOKBOX_ID_PATH = "/communications-apis/video";


    /**
     * Landing Page Template Path
     */
    public static final String CAMPAIGN_PAGE_TEMPLATE_PATH = "/conf/vonage/settings/wcm/templates/campaign-page";

    /**
     * Ecomm Page Template Path
     */
    public static final String ECOMM_PAGE_TEMPLATE_PATH = "/conf/vonage/settings/wcm/templates/ecommerce-page";

    /**
     * Attribution campaign path
     */
    public static final String ATTRIBUTION_CAMPAIGNS_PATH = "/var/commerce/products/vonage/attribution-campaigns";

    /**
     * Press Release Home Page path
     */
    public static final String PRESS_RELEASE_HOME_PAGE_PATH =
      "/content/vonage/live-copies/us/en/about-us/press-releases/";

    /**
     * Attribution default campaign ID
     */
    public static final String ATTRIBUTION_DEFAULT_CAMPAIGN_ID = "7011O00000290qe";

    /**
     * Search engine URL patterns- <b>Is it Used anywhere?</b>
     */
    protected static final String[] SEARCH_ENGINE_URL_PATTERNS = {
            "^https?://www\\.google\\.(?:(?:(?:com|org|net|co|it|edu)\\.)*[^\\.]+)/", "^https?://search\\.yahoo\\.com/",
            "^https?://www\\.bing\\.com/" };

    /**
     * Attribution campaign name for direct traffic
     */
    public static final String ATTRIBUTION_CAMPAIGN_DIRECT = "bizdirect";

    /**
     * Attribution campaign name for search engine traffic
     */
    public static final String ATTRIBUTION_CAMPAIGN_SEO = "vonageseo";

    /**
     * Standard wait time for multiple Vonage external API calls
     */
    public static final int THREAD_WAIT_TIME = 500;

    /**
     * Standard wait time for multiple Vonage external API calls
     */
    public static final int THREAD_WAIT_TIME_ST = 15000;


    /**
     * Replication job path - used on custom sling job
     */
    public static final String REPLICATION_JOB_PATH = "sample/replication/job";

}
