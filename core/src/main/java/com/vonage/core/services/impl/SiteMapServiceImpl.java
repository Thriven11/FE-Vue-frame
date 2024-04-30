package com.vonage.core.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.vonage.core.constants.AppConstants;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.services.AppConfigService;
import com.vonage.core.services.SiteMapService;
import com.vonage.core.utils.ServiceUtils;
import com.vonage.design.sitemap.Sitemap;
import com.vonage.design.sitemap.SitemapFactory;

/**
 * Service to generate Sitemap XML
 * <p>
 * This service is called to generate sitemap XML
 * </p>
 *
 * @author Vonage
 */
@Component(immediate = true, service = SiteMapService.class)
public class SiteMapServiceImpl implements SiteMapService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapServiceImpl.class);

    /**
     * Replicate service
     */
    @Reference
    private Replicator replicate;

    /**
     * Resource Resolver Factory
     */
    @Reference
    private ResourceResolverFactory resolverFactory;

    /**
     * Vonage configuration service
     */
    @Reference
    private AppConfigService configService;

    /**
     * externalizer
     */
    @Reference
    private Externalizer externalizer;

    /**
     * Generation Sitemap
     *
     * @param rootPath        - rootPath
     * @param destinationPath - The site map file generation location
     * @param urlRewriteRules - Map of url rewrite rules from config
     * @param mimeTypes       - Different mimetypes supported
     * @param excludePaths    - excluded path pages regex
     * @throws IOException                  - IOException
     * @throws ParserConfigurationException - ParserConfigurationException
     * @throws RepositoryException          - RepositoryException
     * @throws ReplicationException
     */
    @Override
    public final void generateSitemap(final String rootPath, final String destinationPath,
            final Map<String, String> urlRewriteRules, final List<String> mimeTypes, final List<String> excludePaths)
            throws RepositoryException, ParserConfigurationException, IOException, ReplicationException {
        ResourceResolver resourceResolver = ServiceUtils.getWriteResourceResolver(resolverFactory);
        if (null != resourceResolver) {
            Session session = resourceResolver.adaptTo(Session.class);
            String sitemapFolder = destinationPath.substring(0, destinationPath.lastIndexOf(AppConstants.SLASH));
            if (null != session && session.itemExists(sitemapFolder) && session.itemExists(rootPath)) {
                LOGGER.debug("Inside xml generation");
                Sitemap sitemap = SitemapFactory.getSitemapInstance(resourceResolver, externalizer, rootPath,
                        destinationPath, urlRewriteRules, mimeTypes, excludePaths);
                if (null != sitemap && sitemap.generate()) {
                    ServiceUtils.replicatePaths(resourceResolver, replicate, ReplicationActionType.ACTIVATE,
                            configService.getPublishAgents(), Arrays.asList(destinationPath));
                }
            } else {
                LOGGER.error("Can't create sitemap! Reason: Following paths must exist- {}, {}", rootPath,
                        sitemapFolder);
            }
        } else {
            LOGGER.error("Can't create sitemap! Reason: ResourceResolver- null, Check serviceuser for subservice- {}",
                    VonageConstants.WRITE_SUBSERVICE);
        }
    }

}
