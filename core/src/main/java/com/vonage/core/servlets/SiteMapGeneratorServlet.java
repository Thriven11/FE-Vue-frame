package com.vonage.core.servlets;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.xss.XSSAPI;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;

import com.vonage.core.beans.SitemapProps;
import com.vonage.core.constants.VonageConstants;
import com.vonage.core.schedulers.SitemapCreationJob;
import com.vonage.core.services.SiteMapService;

/**
 * The servlet class for refreshing the sitemap(s) manually. Use query string
 * <code>"?image=true"</code> to re-generate image sitemaps. If no param is
 * passed then it only re-generates the page sitemap(s).
 *
 * @author Vonage
 *
 */
@Component(service = Servlet.class, property = { "description=Sitemap Generation Servlet",
        SLING_SERVLET_RESOURCE_TYPES + "=vonage/servlets/sitemap", SLING_SERVLET_METHODS + "=GET" })
public class SiteMapGeneratorServlet extends SlingSafeMethodsServlet {

    /**
     * UUID
     */
    private static final long serialVersionUID = 1799160511844608566L;

    /**
     * XSS API
     */
    @Reference
    private transient XSSAPI xssAPI;

    /**
     * SiteMapGeneration Service
     */
    @Reference
    private transient SiteMapService sitemapService;

    /** Service to get OSGi configurations */
    @Reference
    private transient ConfigurationAdmin configAdmin;

    /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapGeneratorServlet.class);

    /**
     * Success message
     */
    private static final String SUCCESS_MESSAGE = "<h1>Success</h1><div>Sitemap re-generation job triggered"
            + " successfully for the following paths: %s.<p>You can close this page now. The job will keep"
            + " running in background and may take 5 to 15 minutes to finish.</p></div>";
    /**
     * Failure message
     */
    private static final String ERROR_MESSAGE = "<h1>Error</h1><div>Some error occured, Kindly try again after some"
            + " time or contact Vonage IT support team if problem persists.</div>";

    @Override
    public final void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        String filter = '(' + ConfigurationAdmin.SERVICE_FACTORYPID + '=' + VonageConstants.SITEMAP_FACTORY_PID + ')';
        try {
            Configuration[] allConfigs = configAdmin.listConfigurations(filter);
            List<String> sitemapRefreshList = new ArrayList<>();
            for (Configuration config : allConfigs) {
                if (triggerSitemapJob(config,
                        xssAPI.encodeForHTMLAttr(request.getParameter(VonageConstants.PARAM_IMAGE)))) {
                    sitemapRefreshList.add(config.getProperties().get(VonageConstants.PN_TARGETPATH).toString());
                }
            }
            if (sitemapRefreshList.isEmpty()) {
                response.getWriter().write("Nothing to refresh!");
            } else {
                response.getWriter().write(String.format(SUCCESS_MESSAGE, sitemapRefreshList));
            }
        } catch (InvalidSyntaxException | DOMException e) {
            LOGGER.error("Error : {}", e.getMessage(), e);
            response.getWriter().write(ERROR_MESSAGE);
        }
    }

    /**
     * Trigger the job
     *
     * @param config     - Config
     * @param imageParam - Param
     * @return true or false
     */
    private boolean triggerSitemapJob(final Configuration config, final String imageParam) {
        LOGGER.debug("Config found-- {}", config.getPid());
        Dictionary<String, Object> properties = config.getProperties();
        String[] rewrites = PropertiesUtil.toStringArray(properties.get(VonageConstants.PN_URL_REWRITES),
                new String[] {});
        String[] types = PropertiesUtil.toStringArray(properties.get(VonageConstants.PN_MIMETYPES), new String[] {});
        List<String> mimeTypes = Arrays.stream(types).filter(s -> StringUtils.isNotBlank(s)).map(String::trim)
                .collect(Collectors.toList());
        String[] excludes = PropertiesUtil.toStringArray(properties.get(VonageConstants.PN_EXCLUDEPATHS),
                new String[] {});
        List<String> excludePaths = Arrays.stream(excludes).filter(s -> StringUtils.isNotBlank(s)).map(String::trim)
                .collect(Collectors.toList());
        Map<String, String> urlRewrites = Arrays.stream(rewrites).filter(s -> s.contains(":")).map(s -> s.split(":"))
                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
        boolean isImage = Boolean.parseBoolean(imageParam);
        if ((isImage && mimeTypes.size() > 0) || (!isImage && mimeTypes.size() < 1)) {
            LOGGER.debug("Re-generating SITEMAP manually for target path: {}",
                    properties.get(VonageConstants.PN_TARGETPATH));
            final SitemapCreationJob job = new SitemapCreationJob();
            job.setSiteMapGenerationService(sitemapService);
            SitemapProps sitemapConfig = new SitemapProps(properties.get(VonageConstants.PN_SITEMAP_ROOT).toString(),
                    properties.get(VonageConstants.PN_TARGETPATH).toString(), urlRewrites, mimeTypes, true,
                    excludePaths);
            job.setConfig(sitemapConfig);
            job.run();
            return true;
        }
        return false;
    }
}
