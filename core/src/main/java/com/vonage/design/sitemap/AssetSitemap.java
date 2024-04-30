package com.vonage.design.sitemap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.vonage.core.utils.XMLUtils;

/**
 * AssetSitemap class
 *
 * @author Vonage
 *
 */
public class AssetSitemap extends Sitemap {

    /**
     * Mime Types
     */
    private List<String> mimeTypes;

    /**
     * Constructor
     *
     * @param resourceResolver   resourceResolver
     * @param externalizer       externalizer
     * @param sitemapContentRoot sitemapContentRoot
     * @param sitemapLocation    sitemapLocation
     * @param urlRewriteRules    urlRewriteRules
     * @param mimeTypeList       mimeTypeList
     * @param excludePaths       excludePaths
     */
    public AssetSitemap(final ResourceResolver resourceResolver, final Externalizer externalizer,
            final String sitemapContentRoot, final String sitemapLocation, final Map<String, String> urlRewriteRules,
            final List<String> mimeTypeList, final List<String> excludePaths) {
        super(resourceResolver, externalizer, sitemapContentRoot, sitemapLocation, urlRewriteRules, excludePaths);
        this.mimeTypes = Collections.unmodifiableList(mimeTypeList);
    }

    @Override
    protected final void addContent(final Page page, final Document document, final Element rootElement) {
        rootElement.appendChild(XMLUtils.createURLElement(this.getResourceResolver(), this.getExternalizer(), document,
                this.getRewriteRules(), mimeTypes, this.getExcludePaths(), page));
    }

    @Override
    protected final Element getRootElement(final Document document) {
        return XMLUtils.createRootElement(document, true);
    }

}
