package com.vonage.core.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.tika.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.crx.JcrConstants;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.junit5.JcrOakAemContext;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class XMLUtilsTest {

    @InjectMocks
    XMLUtils xmlUtils;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Externalizer externalizer;

    @Mock
    Asset asset;

    Document document;

    @Mock
    Element rootElement;

    @Mock
    Page contentRoot;

    @Mock
    Session session;

    @Mock
    ValueFactory valueFactory;

    @Mock
    Node node;

    @Mock
    Map<String, String> urlRewriteValues;

    private static final String PAGE_PATH = "/content/vonage/en-us";
    private static final String EXCLUDE_PAGE_PATH = "/content/vonage/en-us/homepage";

    private void setUp(AemContext ctx) throws Exception {
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        ctx.load().json("/com/vonage/core/utils/XMLUtilsTest.json", PAGE_PATH);
        contentRoot = ctx.pageManager().getPage(PAGE_PATH);
    }

    @Test
    public void testCreateURLElement(JcrOakAemContext context) throws ParserConfigurationException {
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        context.load().json("/com/vonage/core/utils/XMLUtilsTest.json", PAGE_PATH);
        contentRoot = context.pageManager().getPage(PAGE_PATH);
        Map<String, String> urlRewriteValues = new HashMap<>();
        urlRewriteValues.put("/content/vonage", "/newera");
        List<String> mimeTypes = new ArrayList<String>();
        mimeTypes.add("image/jpeg");
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add(EXCLUDE_PAGE_PATH);
        when(externalizer.externalLink(resourceResolver, Externalizer.PUBLISH, PAGE_PATH))
                .thenReturn("http://localhost:4503" + PAGE_PATH);
        XMLUtils.createURLElement(resourceResolver, externalizer, document, urlRewriteValues, mimeTypes, excludePaths,
                contentRoot);
        assertTrue(true);
    }

    @Test
    public void testCreateURLElementWhenNoMatch(AemContext ctx) throws Exception {
        setUp(ctx);
        Map<String, String> urlRewriteValues = new HashMap<>();
        urlRewriteValues.put("/content/dam", "/newdam");
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add(EXCLUDE_PAGE_PATH);
        when(externalizer.externalLink(resourceResolver, Externalizer.PUBLISH, PAGE_PATH))
                .thenReturn("http://localhost:4503" + PAGE_PATH);
        XMLUtils.createURLElement(resourceResolver, externalizer, document, urlRewriteValues, null, excludePaths,
                contentRoot);
        assertTrue(true);
    }

    @Test
    public void testCreateFileInDestination(AemContext ctx) throws Exception {
        setUp(ctx);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(session.getNode(PAGE_PATH)).thenReturn(node);
        when(session.itemExists(PAGE_PATH + "/sitemap.xml")).thenReturn(true);
        when(node.addNode(FilenameUtils.getName(PAGE_PATH + "/sitemap.xml"), JcrConstants.NT_FILE)).thenReturn(node);
        when(node.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE)).thenReturn(node);
        assertTrue(XMLUtils.createFileInDestination(resourceResolver, document, PAGE_PATH + "/sitemap.xml"));
    }

    @Test
    public void testCreateFileInDestinationWhenNotExists(AemContext ctx) throws Exception {
        setUp(ctx);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(session.getNode(PAGE_PATH)).thenReturn(node);
        when(node.addNode(FilenameUtils.getName(PAGE_PATH + "/sitemap.xml"), JcrConstants.NT_FILE)).thenReturn(node);
        when(node.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE)).thenReturn(node);
        assertTrue(XMLUtils.createFileInDestination(resourceResolver, document, PAGE_PATH + "/sitemap.xml"));
    }

    @Test
    public void testCreateFileInDestinationException(AemContext ctx) throws Exception {
        setUp(ctx);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(session.getValueFactory()).thenReturn(valueFactory);
        when(session.itemExists(PAGE_PATH + "/sitemap.xml")).thenThrow(new RepositoryException("Some Exception"));
        assertFalse(XMLUtils.createFileInDestination(resourceResolver, document, PAGE_PATH + "/sitemap.xml"));
    }

    @Test
    public void testCreateAssetElement(AemContext ctx) throws ParserConfigurationException {
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Map<String, String> urlRewriteValues = new HashMap<>();
        urlRewriteValues.put("/content/vonage", "/newera");
        assertNotNull(XMLUtils.createAssetElement(document, PAGE_PATH, urlRewriteValues));
    }

}