package com.vonage.design.sitemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.utils.BasicUtils;
import com.vonage.core.utils.XMLUtils;

/**
 * Sitemap class
 *
 * @author Vonage
 *
 */
public abstract class Sitemap {

    /**
     * Site map tree root
     */
    private String root;

    /**
     * Site map target location
     */
    private String location;

    /**
     * Rewrite rules
     */
    private Map<String, String> rewriteRules;

    /**
     * Exclude paths
     */
    private List<String> excludePaths = Collections.emptyList();

    /**
     * Resource resolver object
     */
    private ResourceResolver resourceResolver;

    /**
     * Externalizer object
     */
    private Externalizer externalizer;

    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(Sitemap.class);

    /**
     * URL rewrite rules
     *
     * @return Rewrite rules
     */
    protected final Map<String, String> getRewriteRules() {
        return rewriteRules;
    }

    /**
     * Get exclude paths
     *
     * @return exclude paths
     */
    protected final List<String> getExcludePaths() {
        return  new ArrayList<>(excludePaths);
    }

    /**
     * Get resource resolver
     *
     * @return ResourceResolver
     */
    protected final ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    /**
     * Get Externalizer
     *
     * @return Externalizer
     */
    protected final Externalizer getExternalizer() {
        return externalizer;
    }

    /**
     * Constructor
     *
     * @param resourceResolver   resourceResolver
     * @param externalizer       externalizer
     * @param sitemapContentRoot sitemapContentRoot
     * @param sitemapLocation    sitemapLocation
     * @param urlRewriteRules    urlRewriteRules
     * @param excludePaths       excludePaths
     */
    protected Sitemap(final ResourceResolver resourceResolver, final Externalizer externalizer,
            final String sitemapContentRoot, final String sitemapLocation, final Map<String, String> urlRewriteRules,
            final List<String> excludePaths) {
        this.resourceResolver = resourceResolver;
        this.externalizer = externalizer;
        this.root = sitemapContentRoot;
        this.location = sitemapLocation;
        this.rewriteRules = urlRewriteRules;
        if (null != excludePaths) {
            this.excludePaths = Collections.unmodifiableList(excludePaths);
        }
    }

    /**
     * Generate sitemap
     *
     * @return true if success otherwise returns false
     * @throws ParserConfigurationException ParserConfigurationException
     */
    public final boolean generate() throws ParserConfigurationException {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (null != pageManager) {
            Page rootPage = pageManager.getPage(root);
            if (null != rootPage) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setExpandEntityReferences(false);
                factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                factory.setFeature(AppConstants.XML_DISALLOW_DOCTYPE_URL, true);
                factory.setFeature(AppConstants.XML_EXTERNAL_ENTITIES_URL, false);
                Document document = factory.newDocumentBuilder().newDocument();
                Element rootElement = getRootElement(document);
                Iterator<Page> children = rootPage.listChildren(new PageFilter(), true);
                while (children.hasNext()) {
                    Page page = children.next();
                    String pagePath = page.getPath();
                    Resource pageResource = this.getResourceResolver().getResource(pagePath);

                    Page pg = pageResource.adaptTo(Page.class);
                    ValueMap pageProperties = pg.getProperties();
                    boolean isDisabledForSEO = false;
                    if (pageProperties.get("disableIndexForSEO") != null) {
                        isDisabledForSEO = true;
                        LOGGER.info(pagePath + " is disabled for SEO indexing and will not be included in sitemap");
                    } else {
                        LOGGER.info(pagePath + " is enabled for SEO indexing and will generate in sitemap");
                    }

                    if (null != pageResource
                            && !isDisabledForSEO
                            && BasicUtils.isActivatedResource(pageResource)
                            && !BasicUtils.isMatchingPath(this.getExcludePaths(), pagePath)) {
                        addContent(page, document, rootElement);
                    }
                }
                return XMLUtils.createFileInDestination(resourceResolver, document, location);
            } else {
                LOGGER.error(" Root page doesn't exist {}", root);
            }
        } else {
            LOGGER.error("Can't adapt resource resolve to Page Manager");
        }
        return false;
    }

    /**
     * Get Root element
     *
     * @param document - document
     * @return Element - root element
     */
    protected abstract Element getRootElement(final Document document);

    /**
     * Add content to the sitemap document
     *
     * @param contentRootPage - root Page
     * @param document        - document
     * @param rootElement     - root Element
     */
    protected abstract void addContent(final Page contentRootPage, final Document document, final Element rootElement);
}
