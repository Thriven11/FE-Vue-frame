package com.vonage.core.schedulers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vonage.core.schedulers.SitemapSchedulerConfiguration;
import com.vonage.core.schedulers.SitemapSchedulerFactory;
import com.vonage.core.services.SiteMapService;
import com.vonage.core.utils.SchedulerUtils;

/**
 * SitemapSchedulerFactory Implementation class
 *
 * @author Vonage
 *
 */
@Component(service = SitemapSchedulerFactory.class)
@Designate(ocd = SitemapSchedulerConfiguration.class, factory = true)
public class SitemapSchedulerFactoryImpl implements SitemapSchedulerFactory {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SitemapSchedulerFactoryImpl.class);
    /**
     * Scheduler Job name prefix
     */
    private static final String JOBNAME_PREFIX = "sitemap-job-";

    /**
     * repository service
     */
    @Reference
    private SlingRepository repository;

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
     * Target Path
     */
    private String sitemapTargetPath;
    /**
     * Content Root
     */
    private String sitemapContentRoot;
    /**
     * Exclude Path
     */
    private List<String> excludePaths = Collections.emptyList();
    /**
     * Expression
     */
    private String schedulerExpression;
    /**
     * Scheduler Enabled
     */
    private boolean schedulerEnabled;
    /**
     * Scheduler Enabled
     */
    private Map<String, String> urlRewrites;
    /**
     * Mime Types
     */
    private List<String> mimeTypes = Collections.emptyList();

    /**
     * Activate method
     *
     * @param config configuration
     */
    @Activate
    protected final void activate(final SitemapSchedulerConfiguration config) {
        init(config);
    }

    /**
     * Modified method
     *
     * @param config configuration
     */
    @Modified
    protected final void modified(final SitemapSchedulerConfiguration config) {
        LOGGER.debug("Modified!");
        init(config);
        SchedulerUtils.removeSchedulerJob(scheduler, this);
        SchedulerUtils.addSchedulerJob(scheduler, siteMapGenerationService, repository, this);
    }

    /**
     * Init variables
     *
     * @param config - config object
     */
    private void init(final SitemapSchedulerConfiguration config) {
        sitemapTargetPath = config.targetPath().trim();
        sitemapContentRoot = config.sitemapRoot().trim();
        schedulerExpression = config.cronExpression().trim();
        schedulerEnabled = config.enableScheduler();
        excludePaths = Arrays.stream(config.excludePaths()).filter(StringUtils::isNotBlank).map(String::trim)
                .collect(Collectors.toList());
        mimeTypes = Arrays.stream(config.mimeTypes()).filter(StringUtils::isNotBlank).map(String::trim)
                .collect(Collectors.toList());
        urlRewrites = Arrays.stream(config.urlRewrites()).filter(s -> s.contains(":")).map(s -> s.split(":"))
                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
        LOGGER.debug(
                "Read the sitemapTargetPath, imageSitemapTargetPath : {}, sitemapRoot : {}, ContentRoot : {}, "
                        + "SchedulerEnabledValue : {} ",
                sitemapTargetPath, sitemapContentRoot, sitemapContentRoot, schedulerEnabled);
    }

    /**
     * Get Target Path
     */
    @Override
    public final String getSitemapTargetPath() {
        return sitemapTargetPath;
    }

    /**
     * Get Content Root
     */
    @Override
    public final String getSitemapContentRoot() {
        return sitemapContentRoot;
    }

    /**
     * Get Exclude Path
     */
    @Override
    public final List<String> getExcludePaths() {
        return new ArrayList<>(excludePaths);
    }

    /**
     * Get Expression
     */
    @Override
    public final String getSchedulerExpression() {
        return schedulerExpression;
    }

    /**
     * Get Unique Identifier
     */
    @Override
    public final String getId() {
        return JOBNAME_PREFIX + this.hashCode();
    }

    /**
     * Get Is Scheduler enabled or not
     */
    @Override
    public final boolean isSchedulerEnabled() {
        return schedulerEnabled;
    }

    /**
     * Map of urls and replacable values matching with dispatcher Apache rewrites
     */
    @Override
    public final Map<String, String> getUrlRewriteValues() {
        return urlRewrites;
    }

    /**
     *
     * @return mimetypes List
     */
    @Override
    public final List<String> getMimeTypes() {
        return new ArrayList<>(mimeTypes);
    }
}
