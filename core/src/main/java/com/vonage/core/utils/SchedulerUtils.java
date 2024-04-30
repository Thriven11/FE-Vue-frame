package com.vonage.core.utils;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vonage.core.beans.SitemapProps;
import com.vonage.core.schedulers.SitemapCreationJob;
import com.vonage.core.schedulers.SitemapSchedulerFactory;
import com.vonage.core.services.SiteMapService;

/**
 * SchedulerUtils class
 *
 * @author Vonage
 *
 */
public final class SchedulerUtils {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerUtils.class);

    /**
     * Static class should not be initialized.
     */
    private SchedulerUtils() {
    }

    /**
     * Create a new scheduler job
     *
     * @param scheduler                scheduler
     * @param siteMapGenerationService siteMapService
     * @param repository               repository
     * @param config                   SitemapSchedulerFactory
     */
    public static void addSchedulerJob(final Scheduler scheduler, final SiteMapService siteMapGenerationService,
            final SlingRepository repository, final SitemapSchedulerFactory config) {
        if (isMasterRepository(repository)) {
            LOGGER.info("A new sitemap job scheduled with ID- {}", config.getId());
            final SitemapCreationJob job = new SitemapCreationJob();
            SitemapProps sitemapConfig = new SitemapProps(config.getSitemapContentRoot(), config.getSitemapTargetPath(),
                    config.getUrlRewriteValues(), config.getMimeTypes(), config.isSchedulerEnabled(),
                    config.getExcludePaths());
            job.setConfig(sitemapConfig);
            job.setSiteMapGenerationService(siteMapGenerationService);
            final String schedulingExpression = config.getSchedulerExpression();
            ScheduleOptions so = scheduler.EXPR(schedulingExpression);
            so.name(config.getId());
            so.canRunConcurrently(true);
            scheduler.schedule(job, so);
            LOGGER.debug("Scheduled Job: path- {}, Expression- {}, isEnabled- {}, excludePaths- {}",
                    config.getSitemapTargetPath(), schedulingExpression, config.isSchedulerEnabled(),
                    config.getExcludePaths());
        } else {
            LOGGER.debug("Not a master author instance, skip sitemap generation!");
        }
    }

    /**
     * Removes an existing scheduler job
     *
     * @param scheduler scheduler
     * @param config    SitemapSchedulerFactory
     */
    public static void removeSchedulerJob(final Scheduler scheduler, final SitemapSchedulerFactory config) {
        LOGGER.info("Removed Sitemap Scheduler Job: {}", config.getId());
        scheduler.unschedule(config.getId());
    }

    /**
     * is master instance
     *
     * @param repository repository
     * @return boolean
     */
    public static boolean isMasterRepository(final SlingRepository repository) {
        final String isMaster = repository.getDescriptor("crx.cluster.master");
        LOGGER.debug("isMaster.. {}", isMaster);
        return isMaster != null && !isMaster.equals("") && Boolean.parseBoolean(isMaster);
    }
}
