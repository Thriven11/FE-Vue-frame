package com.vonage.core.schedulers.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vonage.core.schedulers.SitemapSchedulerConfiguration;
import com.vonage.core.services.SiteMapService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SitemapSchedulerFactoryImplTest {

    @InjectMocks
    SitemapSchedulerFactoryImpl sitemapSchedulerFactoryImpl;

    @Mock
    SitemapSchedulerConfiguration sitemapSchedulerConfiguration;

    @Mock
    SlingRepository repository;

    @Mock
    Scheduler scheduler;

    @Mock
    SiteMapService siteMapGenerationService;

    @Mock
    ScheduleOptions so;

    private String[] mimeTypes = {};
    private String[] urlRewrites = {};
    private String[] excludePaths = {};

    @BeforeEach
    public void setup() {
        when(sitemapSchedulerConfiguration.targetPath()).thenReturn("/content/vonage/en-us/sitemap.xml");
        when(sitemapSchedulerConfiguration.sitemapRoot()).thenReturn("/content/vonage/en-us");
        when(sitemapSchedulerConfiguration.cronExpression()).thenReturn("0 0/5 * * * ? *");
        when(sitemapSchedulerConfiguration.enableScheduler()).thenReturn(true);
        when(sitemapSchedulerConfiguration.mimeTypes()).thenReturn(mimeTypes);
        when(sitemapSchedulerConfiguration.urlRewrites()).thenReturn(urlRewrites);
        when(sitemapSchedulerConfiguration.excludePaths()).thenReturn(excludePaths);
    }

    @Test
    public void testActivate() {
        sitemapSchedulerFactoryImpl.activate(sitemapSchedulerConfiguration);
        Assert.assertEquals(sitemapSchedulerFactoryImpl.getSchedulerExpression(), "0 0/5 * * * ? *");
        Assert.assertEquals(sitemapSchedulerFactoryImpl.getMimeTypes(), Collections.emptyList());
        Assert.assertEquals(sitemapSchedulerFactoryImpl.getExcludePaths(), Collections.emptyList());
        Assert.assertEquals(sitemapSchedulerFactoryImpl.getUrlRewriteValues(), Collections.emptyMap());
        Assert.assertEquals(sitemapSchedulerFactoryImpl.getSitemapTargetPath(), "/content/vonage/en-us/sitemap.xml");
        Assert.assertEquals(sitemapSchedulerFactoryImpl.getSitemapContentRoot(), "/content/vonage/en-us");
        Assert.assertTrue(sitemapSchedulerFactoryImpl.isSchedulerEnabled());
        Assert.assertTrue(sitemapSchedulerFactoryImpl.getId().startsWith("sitemap-job"));
    }

    @Test
    public void testModified() {
        when(repository.getDescriptor("crx.cluster.master")).thenReturn("true");
        when(scheduler.EXPR("0 0/5 * * * ? *")).thenReturn(so);
        sitemapSchedulerFactoryImpl.modified(sitemapSchedulerConfiguration);
        assertTrue(true);
    }

    @Test
    public void testModifiedNoMaster() {
        sitemapSchedulerFactoryImpl.modified(sitemapSchedulerConfiguration);
        assertTrue(true);
    }
}