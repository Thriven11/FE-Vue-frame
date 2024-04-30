package com.vonage.design.sitemap;

import com.day.cq.commons.Externalizer;
import com.vonage.core.constants.AppConstants;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * SitemapFactory which produce two type of site maps - AssetSitemap and
 * PageSitemap.
 *
 * @author Vonage
 *
 */
public final class SitemapFactory {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SitemapFactory.class);

    /**
     * private constructor
     */
    private SitemapFactory() {
        // Blank
    }

    /**
     * Generates site map xml
     *
     * @param resourceResolver   - Service resource resolver with rewrite permission
     * @param externalizr        - URL Externalizer
     * @param sitemapContentRoot - Root Path
     * @param sitemapLocation    - destination location
     * @param urlRewriteValues   - Map of urlrewrites from config
     * @param mimeTypes          - Different mimetypes supported
     * @param excludePaths       - excluded path pages
     * @return Sitemap - Sitemap object if provided with valid paths otherwise
     *         return null
     * @throws ParserConfigurationException - ParserConfigurationException
     * @throws IOException                  - IOException
     */
    public static Sitemap getSitemapInstance(final ResourceResolver resourceResolver, final Externalizer externalizr,
            final String sitemapContentRoot, final String sitemapLocation, final Map<String, String> urlRewriteValues,
            final List<String> mimeTypes, final List<String> excludePaths) {
        if (sitemapContentRoot.startsWith(AppConstants.CONTENT_PATH)) {
            if (mimeTypes.isEmpty()) {
                return new PageSitemap(resourceResolver, externalizr, sitemapContentRoot, sitemapLocation,
                        urlRewriteValues, excludePaths);
            } else {
                return new AssetSitemap(resourceResolver, externalizr, sitemapContentRoot, sitemapLocation,
                        urlRewriteValues, mimeTypes, excludePaths);
            }
        } else {
            LOGGER.error("Sitemap Root path {} should start with '{}'", sitemapContentRoot, AppConstants.CONTENT_PATH);
            return null;
        }
    }
}
