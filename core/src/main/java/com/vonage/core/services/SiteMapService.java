package com.vonage.core.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;

import com.day.cq.replication.ReplicationException;

/**
 * Sitemap Generation Service
 *
 * @author Vonage
 */
public interface SiteMapService {

    /**
     * Abstract method for generating sitemap
     *
     * @param rootPath         - rootPath
     * @param destnPath        - destination path to store sitemap
     * @param urlRewriteValues - Map of urlrewrites from config
     * @param mimeTypes        - mimeTypes
     * @param excludePaths     - excluded path pages regex
     * @throws IOException                  - IOException
     * @throws ParserConfigurationException - ParserConfigurationException
     * @throws RepositoryException          - RepositoryException
     * @throws ReplicationException         - ReplicationException
     */
    void generateSitemap(String rootPath, String destnPath, Map<String, String> urlRewriteValues,
            List<String> mimeTypes, List<String> excludePaths)
            throws RepositoryException, ParserConfigurationException, IOException, ReplicationException;
}
