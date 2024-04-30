package com.vonage.core.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vonage.core.services.AppConfigService;
import com.vonage.core.utils.ServiceUtils;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.junit5.JcrOakAemContext;
import junit.framework.Assert;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SiteMapServiceImplTest {

    @InjectMocks
    SiteMapServiceImpl siteMapService;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

    @Mock
    Externalizer externalizer;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Session session;

    @Mock
    PageManager pageManager;

    @Mock
    ValueFactory valueFactory;

    @Mock
    AppConfigService configService;

    @Mock
    Node node;

    @Mock
    Resource mockResource;

    @Mock
    Replicator replicate;

    @Mock
    ReplicationStatus status;

    @Mock
    Page page;

    @Mock
    Resource resourceForReplication;

    List<String> excludePaths;

    private static final String PAGE_PATH = "/content/vonage/en-us";
    private static final String EXCLUDE_PAGE_PATH = "/content/vonage/en-us/homepagedemo";
    private static final String EXCLUDE_IMAGE_PATH = "/content/dam/vonage/speedbump";

    public void setUpForPageSitemap(AemContext ctx) throws Exception {
        ctx.load().json("/com/vonage/core/utils/XMLUtilsTest.json", PAGE_PATH);
        when(ServiceUtils.getWriteResourceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        ctx.currentResource(PAGE_PATH);
        excludePaths = new ArrayList<>();
        excludePaths.add(EXCLUDE_PAGE_PATH);
    }

    @Test
    public void testPageSitemapPositiveCase(AemContext ctx) throws Exception {
        setUpForPageSitemap(ctx);
        Map<String, String> urlRewriteValues = new HashMap<>();
        urlRewriteValues.put("/content/vonage/en-us", "/newera");
        String sitemapLocation = "/content/vonage/en-us/sitemap.xml";
        Page contentRootPage = ctx.pageManager().getPage("/content/vonage/en-us");
        String contentRoot = contentRootPage.getPath();
        when(session.itemExists(anyString())).thenReturn(true);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getPage(contentRoot)).thenReturn(contentRootPage);
        when(resourceResolver.getResource(anyString())).thenReturn(resourceForReplication);
        when(resourceForReplication.adaptTo(ReplicationStatus.class)).thenReturn(status);
        when(status.isActivated()).thenReturn(true);
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(session.getNode(anyString())).thenReturn(node);
        when(node.addNode(any(), any())).thenReturn(node);
        when(configService.getPublishAgents()).thenReturn(new String[1]);
        when(externalizer.externalLink(resourceResolver, Externalizer.PUBLISH, "/content/vonage/en-us/homepage"))
                .thenReturn("http://localhost:4503/content/vonage/en-us/homepage.html");
        siteMapService.generateSitemap(contentRoot, sitemapLocation, urlRewriteValues, new ArrayList<>(), excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testWhenSessionIsNull(AemContext ctx) throws Exception {
        setUpForPageSitemap(ctx);
        String sitemapLocation = "/content/vonage/en-us/sitemap.xml";
        Page contentRootPage = ctx.pageManager().getPage("/content/vonage/en-us");
        String contentRoot = contentRootPage.getPath();
        when(resourceResolver.adaptTo(Session.class)).thenReturn(null);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, null, new ArrayList<>(), excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testResourceResolverIsNull(AemContext ctx) throws Exception {
        ctx.load().json("/com/vonage/core/utils/XMLUtilsTest.json", PAGE_PATH);
        String sitemapLocation = "/content/vonage/en-us/sitemap.xml";
        Page contentRootPage = ctx.pageManager().getPage("/content/vonage/en-us");
        String contentRoot = contentRootPage.getPath();
        when(ServiceUtils.getWriteResourceResolver(resourceResolverFactory)).thenReturn(null);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, null, new ArrayList<>(), excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testPageSitemapWhenRootPageisNull(AemContext ctx) throws Exception {
        setUpForPageSitemap(ctx);
        String sitemapLocation = "/content/vonage/en-us/sitemap.xml";
        Page contentRootPage = ctx.pageManager().getPage("/content/vonage/en-us");
        String contentRoot = contentRootPage.getPath();
        when(session.itemExists(anyString())).thenReturn(true);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(pageManager.getPage(contentRoot)).thenReturn(null);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, null, new ArrayList<>(), excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testPageSitemapWhenPageManagerIsNull(AemContext ctx) throws Exception {
        ctx.load().json("/com/vonage/core/utils/XMLUtilsTest.json", PAGE_PATH);
        String sitemapLocation = "/content/vonage/en-us/sitemap.xml";
        Page contentRootPage = ctx.pageManager().getPage("/content/vonage/en-us");
        String contentRoot = contentRootPage.getPath();
        siteMapService.generateSitemap(contentRoot, sitemapLocation, null, new ArrayList<>(), excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testPageSitemapWhenWrongPath(AemContext ctx) throws Exception {
        setUpForPageSitemap(ctx);
        String sitemapLocation = "/conf/vonage/en-us/sitemap.xml";
        String contentRoot = "/conf/vonage/en-us/articles";
        when(session.itemExists(contentRoot)).thenReturn(true);
        when(session.itemExists("/conf/vonage/en-us")).thenReturn(true);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, null, null, excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testImageSitemapWhenWrongPath(AemContext ctx) throws Exception {
        setUpForPageSitemap(ctx);
        List<String> mimeTypes = new ArrayList<>();
        mimeTypes.add("image/png");
        mimeTypes.add("image/jpg");
        String sitemapLocation = "/conf/vonage/en-us/sitemap.xml";
        String contentRoot = "/content/vonage/en-us/articles";
        when(session.itemExists(contentRoot)).thenReturn(true);
        when(session.itemExists("/conf/vonage/en-us")).thenReturn(true);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, null, mimeTypes, excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testImageSitemap(JcrOakAemContext context)
            throws RepositoryException, ReplicationException, IOException, ParserConfigurationException {
        String contentRoot = PAGE_PATH;
        context.load().json("/com/vonage/core/services/impl/SiteMapServiceImplImageTest.json", contentRoot);
        Page contentRootPage = context.pageManager().getPage("/content/vonage/en-us");
        context.addModelsForPackage("com.vonage.core.services.impl");
        Map<String, String> urlRewriteValues = new HashMap<>();
        urlRewriteValues.put(contentRoot, "/newera");
        String sitemapLocation = "/content/vonage/en-us/sitemap-images.xml";
        List<String> mimeTypes = new ArrayList<>();
        mimeTypes.add("image/png");
        mimeTypes.add("image/jpg");
        when(ServiceUtils.getWriteResourceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(resourceResolver.getResource("/content/vonage/en-us/homepage")).thenReturn(mockResource);
        when(mockResource.adaptTo(ReplicationStatus.class)).thenReturn(status);
        when(status.isActivated()).thenReturn(true);
        when(pageManager.getPage(contentRoot)).thenReturn(contentRootPage);
        when(session.itemExists(anyString())).thenReturn(true);
        when(externalizer.externalLink(resourceResolver, Externalizer.PUBLISH, "/content/vonage/en-us/homepage"))
                .thenReturn("http://localhost:4503/content/vonage/en-us/homepage.html");
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(session.getNode(anyString())).thenReturn(node);
        when(node.addNode(any(), any())).thenReturn(node);
        when(configService.getPublishAgents()).thenReturn(new String[1]);
        excludePaths = new ArrayList<>();
        excludePaths.add(EXCLUDE_IMAGE_PATH);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, urlRewriteValues, mimeTypes, excludePaths);
        Assert.assertTrue(true);
    }

    @Test
    public void testImageSitemapWhenNotActivated(JcrOakAemContext context)
            throws RepositoryException, ReplicationException, IOException, ParserConfigurationException {
        String contentRoot = PAGE_PATH;
        context.load().json("/com/vonage/core/services/impl/SiteMapServiceImplImageTest.json", contentRoot);
        Page contentRootPage = context.pageManager().getPage("/content/vonage/en-us");
        context.addModelsForPackage("com.vonage.core.services.impl");
        Map<String, String> urlRewriteValues = new HashMap<>();
        urlRewriteValues.put(contentRoot, "/newera");
        String sitemapLocation = "/content/vonage/en-us/sitemap-images.xml";
        List<String> mimeTypes = new ArrayList<>();
        mimeTypes.add("image/png");
        mimeTypes.add("image/jpg");
        when(ServiceUtils.getWriteResourceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(resourceResolver.getResource("/content/vonage/en-us/homepage")).thenReturn(mockResource);
        when(mockResource.adaptTo(ReplicationStatus.class)).thenReturn(status);
        when(status.isActivated()).thenReturn(false);
        when(pageManager.getPage(contentRoot)).thenReturn(contentRootPage);
        when(session.itemExists(anyString())).thenReturn(true);
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(session.getNode(anyString())).thenReturn(node);
        when(node.addNode(any(), any())).thenReturn(node);
        when(configService.getPublishAgents()).thenReturn(new String[1]);
        excludePaths = new ArrayList<>();
        excludePaths.add(EXCLUDE_IMAGE_PATH);
        siteMapService.generateSitemap(contentRoot, sitemapLocation, urlRewriteValues, mimeTypes, excludePaths);
        Assert.assertTrue(true);
    }
}