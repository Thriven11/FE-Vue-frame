package com.vonage.core.schedulers;

import java.util.LinkedList;
import java.util.List;

import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vonage.core.constants.AppConstants;
import com.vonage.core.services.SiteMapService;
import com.vonage.core.utils.SchedulerUtils;

/**
 * Scheduler for sitemap creation
 *
 * @author Vonage
 *
 */
@Component(service = CreateSitemapScheduler.class, immediate = true)
public class CreateSitemapScheduler {

    /**
     * repository service
     */
    @Reference
    private SlingRepository repository;

    /**
     * settingsService service
     */
    @Reference
    private SlingSettingsService settingsService;

    /**
     * scheduler service
     */
    @Reference
    private Scheduler scheduler;

    /**
     * SiteMapGeneration Service
     */
    @Reference
    private SiteMapService siteMapGenerationService;

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSitemapScheduler.class);

    /**
     * PROVIDERS list
     */
    private static final List<SitemapSchedulerFactory> PROVIDERS = new LinkedList<>();

    /**
     * This is service method of this class. It schedules the job.
     *
     */
    protected final void activate() {
        LOGGER.debug("Activate called!");
        if (PROVIDERS.isEmpty()) {
            LOGGER.info("No Sitemap schedulers configured!!!");
        } else {
            for (SitemapSchedulerFactory config : PROVIDERS) {
                LOGGER.debug("Scheduling job via activate: {}", config.getId());
                SchedulerUtils.addSchedulerJob(scheduler, siteMapGenerationService, repository, config);
            }
        }
    }

    /**
     * Bind PROVIDERS
     *
     * @param config SitemapSchedulerFactory
     * @throws Exception Exception
     */
    @Reference(cardinality = ReferenceCardinality.MULTIPLE, name = "SitemapSchedulerFactory",
            policy = ReferencePolicy.DYNAMIC)
    protected final void bindProviders(final SitemapSchedulerFactory config) {
        LOGGER.debug("Bind new config: {}", config.getId());
        if (config.getSitemapTargetPath().endsWith(AppConstants.EXTENSION_XML)) {
            PROVIDERS.add(config);
            if (null != scheduler) {
                // This is required for new configurations directly added from OSGi console
                LOGGER.debug("Scheduling job via bind: {}", config.getId());
                SchedulerUtils.addSchedulerJob(scheduler, siteMapGenerationService, repository, config);
            }
        } else {
            LOGGER.error("Can't bind new config. SitemapTargetPath must end with '.xml'. Please fix configuration.");
        }
    }

    /**
     * Unbind PROVIDERS
     *
     * @param config SitemapSchedulerFactory
     */
    protected final void unbindProviders(final SitemapSchedulerFactory config) {
        LOGGER.debug("Unbind called: {}", config.getId());
        SchedulerUtils.removeSchedulerJob(scheduler, config);
        PROVIDERS.remove(config);
    }

}
