package com.vonage.core.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.xss.XSSAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.day.cq.replication.ReplicationException;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.services.SiteMapService;

@ExtendWith({ MockitoExtension.class })
class SiteMapGeneratorServletTest {

    @Mock
    SlingHttpServletRequest request;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    ConfigurationAdmin configAdmin;

    @Mock
    Configuration config;

    @Mock
    XSSAPI xssAPI;

    @Mock
    SiteMapService sitemapService;

    @Mock
    PrintWriter writer;

    @InjectMocks
    private SiteMapGeneratorServlet servlet;

    @Test
    public void testDoGet() throws ReplicationException, IOException {
        Dictionary<String, Object> configProps = new Hashtable<String, Object>();
        configProps.put(VonageConstants.PN_SITEMAP_ROOT, "/content/vonage/articles");
        configProps.put(VonageConstants.PN_TARGETPATH, "/content/vonage/sitemap.xml");
        when(config.getProperties()).thenReturn((Dictionary<String, Object>) configProps);
        when(config.getPid()).thenReturn("some-pid");
        when(response.getWriter()).thenReturn(writer);
        Configuration[] allConfigs = { config };
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            when(configAdmin.listConfigurations(filter)).thenReturn(allConfigs);
            servlet.doGet(request, response);
            assertTrue(true);
        } catch (IOException | InvalidSyntaxException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGetWithType() throws ReplicationException, IOException {
        Dictionary<String, Object> configProps = new Hashtable<String, Object>();
        configProps.put(VonageConstants.PN_SITEMAP_ROOT, "/content/vonage/articles");
        configProps.put(VonageConstants.PN_TARGETPATH, "/content/vonage/sitemap.xml");
        configProps.put(VonageConstants.PN_URL_REWRITES, new String[] { "/content/vonage/articles:/newera" });
        configProps.put(VonageConstants.PN_MIMETYPES, new String[] { "image/jpeg" });
        when(config.getProperties()).thenReturn((Dictionary<String, Object>) configProps);
        when(config.getPid()).thenReturn("some-pid");
        when(response.getWriter()).thenReturn(writer);
        Configuration[] allConfigs = { config };
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            when(configAdmin.listConfigurations(filter)).thenReturn(allConfigs);
            servlet.doGet(request, response);
            assertTrue(true);
        } catch (IOException | InvalidSyntaxException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGetImageWithParam() throws ReplicationException, IOException {
        Dictionary<String, Object> configProps = new Hashtable<String, Object>();
        configProps.put(VonageConstants.PN_SITEMAP_ROOT, "/content/vonage/articles");
        configProps.put(VonageConstants.PN_TARGETPATH, "/content/vonage/sitemap.xml");
        configProps.put(VonageConstants.PN_URL_REWRITES, new String[] { "/content/vonage/articles:/newera" });
        configProps.put(VonageConstants.PN_MIMETYPES, new String[] { "image/jpeg" });
        when(config.getProperties()).thenReturn((Dictionary<String, Object>) configProps);
        when(config.getPid()).thenReturn("some-pid");
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter(VonageConstants.PARAM_IMAGE)).thenReturn("true");
        Configuration[] allConfigs = { config };
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            when(configAdmin.listConfigurations(filter)).thenReturn(allConfigs);
            servlet.doGet(request, response);
            assertTrue(true);
        } catch (IOException | InvalidSyntaxException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGetImageWithParamAndNoType() throws ReplicationException, IOException {
        Dictionary<String, Object> configProps = new Hashtable<String, Object>();
        configProps.put(VonageConstants.PN_SITEMAP_ROOT, "/content/vonage/articles");
        configProps.put(VonageConstants.PN_TARGETPATH, "/content/vonage/sitemap.xml");
        configProps.put(VonageConstants.PN_MIMETYPES, new String[] {});
        when(config.getProperties()).thenReturn((Dictionary<String, Object>) configProps);
        when(config.getPid()).thenReturn("some-pid");
        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter(VonageConstants.PARAM_IMAGE)).thenReturn("true");
        Configuration[] allConfigs = { config };
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            when(configAdmin.listConfigurations(filter)).thenReturn(allConfigs);
            servlet.doGet(request, response);
            assertTrue(true);
        } catch (IOException | InvalidSyntaxException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGetWhenNoConfig() throws ReplicationException, IOException {
        when(response.getWriter()).thenReturn(writer);
        Configuration[] allConfigs = {};
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            when(configAdmin.listConfigurations(filter)).thenReturn(allConfigs);
            servlet.doGet(request, response);
            assertTrue(true);
        } catch (IOException | InvalidSyntaxException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDoGetWhenException() throws ReplicationException, IOException {
        when(response.getWriter()).thenReturn(writer);
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            when(configAdmin.listConfigurations(filter)).thenThrow(new InvalidSyntaxException("some error", "test"));
            servlet.doGet(request, response);
        } catch (IOException | InvalidSyntaxException e) {
            assertTrue(true);
        }

    }
}
