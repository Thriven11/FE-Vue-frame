package com.vonage.core.constants;

/**
 * Common Constant Class to define all AEM OOTB Constants. This class only
 * contain system level constants which are same for all projects. <br>
 * <br>
 * <b>WARNING:</b> Do not define project specific constants here. Instead use
 * project constant file e.g. VonageConstants.java
 */
public final class AppConstants {

    /**
     * Static class should not be initialized.
     */
    private AppConstants() {
    }

    /**
     * The Constant CONTENT_PATH.
     */
    public static final String CONTENT_PATH = "/content/";

    /**
     * The Constant DAM_PATH.
     */
    public static final String DAM_PATH = "/content/dam/";

    /**
     * The Constant HTML_EXTENSION.
     */
    public static final String HTML_EXTENSION = ".html";

    /**
     * The Constant HYPEN.
     */
    public static final String HYPHEN = "-";

    /**
     * The Constant COLON.
     */

    public static final String COLON = ":";

    /**
     * The constant for Prinicipal email property
     */
    public static final String PROP_USER_EMAIL = "profile/email";

    /**
     * The constant for Prinicipal first name property
     */
    public static final String PROP_USER_FIRST_NAME = "profile/givenName";

    /**
     * The Constant for XML file extension
     */
    public static final String EXTENSION_XML = ".xml";

    /**
     * URLSET Tag
     */
    public static final String URLSET = "urlset";

    /**
     * Forward Slash
     */
    public static final String SLASH = "/";

    /**
     * XMLNS Schema Name
     */
    public static final String XMLNS = "xmlns";

    /**
     * XMLNS Schema Name
     */
    public static final String XMLNS_IMAGE = "xmlns:image";

    /**
     * XML Schema URL
     */
    public static final String SCHEMA_URL = "http://www.sitemaps.org/schemas/sitemap/0.9";

    /**
     * XML Schema URL
     */
    public static final String IMAGE_SCHEMA_URL = "http://www.google.com/schemas/sitemap-image/1.1";

    /**
     * Disallow doctype decl in XML processing
     */
    public static final String XML_DISALLOW_DOCTYPE_URL = "http://apache.org/xml/features/disallow-doctype-decl";

    /**
     * Disallow external entities in XML processing
     */
    public static final String XML_EXTERNAL_ENTITIES_URL = "http://xml.org/sax/features/external-general-entities";

    /**
     * URL element
     */
    public static final String URL = "url";

    /**
     * LOC Element
     */
    public static final String LOC = "loc";

}
