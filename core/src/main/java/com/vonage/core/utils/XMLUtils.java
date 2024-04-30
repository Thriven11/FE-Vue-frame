package com.vonage.core.utils;

import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.commons.util.AssetReferenceSearch;
import com.day.cq.wcm.api.Page;
import com.day.crx.JcrConstants;
import com.vonage.core.constants.AppConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.tika.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.jcr.Session;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFactory;
import javax.jcr.Binary;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * XMLUtils class
 *
 * @author Vonage
 *
 */
public final class XMLUtils {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtils.class);

    /**
     * Static class should not be initialized.
     */
    private XMLUtils() {
        // No implementation
    }

    /**
     * Create url element
     *
     * @param resourceResolver - Service resource resolver with rewrite permission
     * @param externalizer     - URL Externalizer
     * @param document         - XML Document
     * @param urlRewriteValues - Map of urlrewrites from config
     * @param mimeTypes        - Mimetypes for image sitemap
     * @param excludePaths     - excluded path pages
     * @param page             - page
     * @return Element - URL Element
     */
    public static Element createURLElement(final ResourceResolver resourceResolver, final Externalizer externalizer,
            final Document document, final Map<String, String> urlRewriteValues, final List<String> mimeTypes,
            final List<String> excludePaths, final Page page) {

        String pagePath = page.getPath();
        String locPath = externalizer.externalLink(resourceResolver, Externalizer.PUBLISH
          + "_" + pagePath.split(AppConstants.SLASH)[4] + "-" + pagePath.split(AppConstants.SLASH)[5], pagePath);
        locPath = locPath.replaceAll("/articles/[a-zA-z]/", "/articles/").replaceAll("/articles/0/", "/articles/");
        Element urlElement = document.createElement(AppConstants.URL);
        Element locElement = document.createElement(AppConstants.LOC);
        locPath = applyUrlRewrites(locPath, urlRewriteValues);
        locElement.appendChild(document.createTextNode(locPath + AppConstants.SLASH));
        urlElement.appendChild(locElement);
//        Calendar cal = page.getLastModified();
//        if (null != cal) {
//            Element lastModifiedElement = document.createElement("lastModified");
//            lastModifiedElement.appendChild(document.createTextNode(VonageConstants.DATE_FORMAT_LONG.format(cal)));
//            urlElement.appendChild(lastModifiedElement);
//        }
        if (null != mimeTypes) {
            AssetReferenceSearch referenceSearch = new AssetReferenceSearch(
                    page.getContentResource().adaptTo(Node.class), DamConstants.MOUNTPOINT_ASSETS, resourceResolver);
            for (Map.Entry<String, Asset> entry : referenceSearch.search().entrySet()) {
                Asset asset = entry.getValue();
                if ((null != asset) && BasicUtils.isActivatedResource(asset.adaptTo(Resource.class))
                        && !BasicUtils.isMatchingPath(excludePaths, entry.getKey())
                        && mimeTypes.contains(asset.getMimeType())) {
                    String assetLocPath = externalizer.externalLink(resourceResolver, Externalizer.PUBLISH,
                            entry.getKey());
                    urlElement.appendChild(createAssetElement(document, assetLocPath, urlRewriteValues));
                }
            }
        }
        return urlElement;
    }

    /**
     * Apply rewrite to URL
     *
     * @param locPath          - Page url
     * @param urlRewriteValues - Map of urlrewrites from config
     * @return Page URL
     */
    public static String applyUrlRewrites(final String locPath, final Map<String, String> urlRewriteValues) {
        String path = URI.create(locPath).getPath() + AppConstants.SLASH;
        if (StringUtils.isNotBlank(path)) {
            for (Map.Entry<String, String> rewrite : urlRewriteValues.entrySet()) {
                if (path.startsWith(rewrite.getKey())) {
                    return locPath.replaceFirst(rewrite.getKey(), rewrite.getValue());
                }
            }
        }
        return locPath;
    }

    /**
     * Generate XML file
     *
     * @param resourceResolver - The resolver
     * @param document         - The XML document
     * @param destination      - The File destination
     * @return boolean - true if operation was successful
     * @throws IOException - Exception
     */
    public static boolean createFileInDestination(final ResourceResolver resourceResolver, final Document document,
            final String destination) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(document);
            Result outputTarget = new StreamResult(outputStream);

            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlSource, outputTarget);
            Session session = resourceResolver.adaptTo(Session.class);
            // create file at file location
            ValueFactory valueFactory = session.getValueFactory();
            Binary contentValue = valueFactory.createBinary(new ByteArrayInputStream(outputStream.toByteArray()));
            String parentPath = destination.substring(0, destination.lastIndexOf(AppConstants.SLASH));

            // Delete existing file
            if (session.itemExists(destination)) {
                session.removeItem(destination);
            }

            // Create new file
            Node sitemapNode = session.getNode(parentPath).addNode(FilenameUtils.getName(destination),
                    JcrConstants.NT_FILE);
            Node jcrContentNode = sitemapNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
            jcrContentNode.setProperty(JcrConstants.JCR_DATA, contentValue);
            jcrContentNode.setProperty(JcrConstants.JCR_MIMETYPE, "text/xml");
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(lastModified.getTimeInMillis());
            jcrContentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
            ServiceUtils.commit(resourceResolver);
            LOGGER.info("Sitemap is successfully created at {}", destination);
            return true;
        } catch (RepositoryException | PersistenceException | TransformerException e) {
            LOGGER.error("Error: {}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * Adding Assets to XML
     *
     * @param document         - document element
     * @param assetLocPath     - assetLocPath
     * @param urlRewriteValues - urlRewriteValues
     * @return Element - Asset Element
     */
    public static Element createAssetElement(final Document document, final String assetLocPath,
            final Map<String, String> urlRewriteValues) {
        Element imageElement = document.createElement("image:image");
        Element locElement = document.createElement("image:loc");
        locElement.appendChild(document.createTextNode(applyUrlRewrites(assetLocPath, urlRewriteValues)));
        imageElement.appendChild(locElement);
        return imageElement;
    }

    /**
     * creating XML Root element
     *
     * @param document       - Document element
     * @param isAssetSitemap - true for asset sitemap
     * @return Root element
     */
    public static Element createRootElement(final Document document, final boolean isAssetSitemap) {
        document.setXmlStandalone(true);
        Element rootElement = document.createElement(AppConstants.URLSET);
        rootElement.setAttribute(AppConstants.XMLNS, AppConstants.SCHEMA_URL);
        if (isAssetSitemap) {
            rootElement.setAttribute(AppConstants.XMLNS_IMAGE, AppConstants.IMAGE_SCHEMA_URL);
        }
        document.appendChild(rootElement);
        return rootElement;
    }

}
