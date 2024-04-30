package com.vonage.core.schedulers;

import java.util.List;
import java.util.Map;

/**
 * SitemapSchedulerFactory Interface
 *
 * @author Vonage
 *
 */
public interface SitemapSchedulerFactory {

    /**
     * Target Path
     *
     * @return String
     */
    String getSitemapTargetPath();

    /**
     * Content Root
     *
     * @return String
     */
    String getSitemapContentRoot();

    /**
     * Exclude Path
     *
     * @return String
     */
    List<String> getExcludePaths();

    /**
     * Scheduler Expression
     *
     * @return String
     */
    String getSchedulerExpression();

    /**
     * Scheduler Id
     *
     * @return String
     */
    String getId();

    /**
     * Enable a scheduler
     *
     * @return boolean
     */
    boolean isSchedulerEnabled();

    /**
     * URL Rewrites
     *
     * @return map
     */
    Map<String, String> getUrlRewriteValues();

    /**
     * MIME Types
     *
     * @return mimetypes
     */
    List<String> getMimeTypes();
}
