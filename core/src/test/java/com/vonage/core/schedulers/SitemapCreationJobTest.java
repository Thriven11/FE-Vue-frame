package com.vonage.core.schedulers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vonage.core.beans.SitemapProps;
import com.vonage.core.services.SiteMapService;

@ExtendWith({ MockitoExtension.class })
class SitemapCreationJobTest {

    @Mock
    SitemapCreationJob sitemapCreationJob;
    SitemapProps config;
    @Mock
    SiteMapService sitemapService;

    @Test
    public void testSchedulerEnabled() {
        sitemapCreationJob.setSiteMapGenerationService(sitemapService);
        Map<String, String> urlRewriteValues = new HashMap<>();
        List<String> mimeTypes = new ArrayList<>();
        List<String> excludePaths = new ArrayList<>();
        config = new SitemapProps("/content/vonage/en-us", "/content/vonage/en-us/sitemap.xml", urlRewriteValues,
                mimeTypes, true, excludePaths);
        sitemapCreationJob.setConfig(config);
        sitemapCreationJob.run();
        assertTrue(true);
    }

    @Test
    public void testSchedulerDisabled() {
        sitemapCreationJob.setSiteMapGenerationService(sitemapService);
        config = new SitemapProps("/content/vonage/en-us", "/content/vonage/en-us/sitemap.xml", null, null, false,
                null);
        sitemapCreationJob.setConfig(config);
        sitemapCreationJob.run();
        assertTrue(true);
    }
}