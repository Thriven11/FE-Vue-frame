package com.vonage.core.schedulers;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;

import com.day.cq.replication.ReplicationException;
import com.vonage.core.beans.SitemapProps;
import com.vonage.core.services.SiteMapService;

/**
 * This is a thread class to generate sitemap
 */
@Component(immediate = true, service = Runnable.class)
public class SitemapCreationJob implements Runnable {

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SitemapCreationJob.class);
    /**
     * sitemapService
     */
    private SiteMapService sitemapService;
    /**
     * sitemapConfig
     */
    private SitemapProps config;

    /**
     * Sets config.
     *
     * @param sitemapConfig config object
     */
    public final void setConfig(final SitemapProps sitemapConfig) {
        this.config = sitemapConfig;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public final void run() {
        try {
            if (config.isEnabled()) {
                // define the Job code here
                LOGGER.info("JOB is RUNNING : {}, {}", config.getTargetPath(), config.getContentRoot());
                sitemapService.generateSitemap(config.getContentRoot(), config.getTargetPath(),
                        config.getRewriteRules(), config.getMimeTypes(), config.getExcludePaths());
            } else {
                LOGGER.debug("JOB is not RUNNING as Scheduler is disabled: , {}, {}", config.getTargetPath(),
                        config.getContentRoot());
            }
        } catch (DOMException | RepositoryException | ParserConfigurationException | IOException
                | ReplicationException e) {
            LOGGER.error("Error: {}", e.getMessage(), e);
        }
    }

    /**
     * Set siteMapGenerationService
     *
     * @param siteMapGenerationService service
     */
    public final void setSiteMapGenerationService(final SiteMapService siteMapGenerationService) {
        this.sitemapService = siteMapGenerationService;
    }
}
