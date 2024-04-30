package com.vonage.design.sitemap;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.vonage.core.utils.XMLUtils;

/**
 * PageSitemap class
 *
 * @author Vonage
 *
 */
public class PageSitemap extends Sitemap {

    /**
     * Constructor
     *
     * @param resourceResolver   Resolver
     * @param externalizer       externalizer
     * @param sitemapContentRoot sitemapContentRoot
     * @param sitemapLocation    sitemapLocation
     * @param urlRewriteRules    urlRewriteRules
     * @param excludePaths       excludePaths
     */
    public PageSitemap(final ResourceResolver resourceResolver, final Externalizer externalizer,
            final String sitemapContentRoot, final String sitemapLocation, final Map<String, String> urlRewriteRules,
            final List<String> excludePaths) {
        super(resourceResolver, externalizer, sitemapContentRoot, sitemapLocation, urlRewriteRules, excludePaths);
    }

    @Override
    protected final void addContent(final Page page, final Document document, final Element rootElement) {
        rootElement.appendChild(XMLUtils.createURLElement(this.getResourceResolver(), this.getExternalizer(), document,
                this.getRewriteRules(), null, this.getExcludePaths(), page));
    }

    @Override
    protected final Element getRootElement(final Document document) {
        return XMLUtils.createRootElement(document, false);
    }

}
