package com.vonage.core.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Helper bean to get Sitemap Props
 *
 * @author Vonage
 *
 */
public class SitemapProps {

    /** @return contentRoot */
    private String contentRoot;

    /**
     * Used to get target path
     *
     * @return targetPath
     */
    private String targetPath;

    /**
     * @return is this enabled
     */
    private boolean enabled;

    /** @return rewriteRules */
    private Map<String, String> rewriteRules;

    /**
     * Used to get mimetypes
     *
     * @return mimeTypes
     **/
    private List<String> mimeTypes = Collections.emptyList();

    /**
     * Used to get excludePaths
     *
     * @return excludePaths
     **/
    private List<String> excludePaths = Collections.emptyList();

    /**
     * @return the contentRoot
     */
    public final String getContentRoot() {
        return contentRoot;
    }

    /**
     * @return the targetPath
     */
    public final String getTargetPath() {
        return targetPath;
    }

    /**
     * @return the rewriteRules
     */
    public final Map<String, String> getRewriteRules() {
        return rewriteRules;
    }

    /**
     * @return the mimeTypes
     */
    public final List<String> getMimeTypes() {
        return new ArrayList<>(mimeTypes);
    }

    /**
     * @return the excludePaths
     */
    public final List<String> getExcludePaths() {
        return new ArrayList<>(excludePaths);
    }

    /**
     * @return the enabled
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Create SitemapProps object constructor
     *
     * @param contentRoot  - The root path
     * @param targetPath   - The sitemap path
     * @param rewriteRules - rules
     * @param mimeTypes    - file types
     * @param isEnabled    - is enabled
     * @param excludePaths - excludePaths
     */
    public SitemapProps(final String contentRoot, final String targetPath, final Map<String, String> rewriteRules,
            final List<String> mimeTypes, final boolean isEnabled, final List<String> excludePaths) {
        this.contentRoot = contentRoot;
        this.targetPath = targetPath;
        this.rewriteRules = rewriteRules;
        if (null != mimeTypes) {
            this.mimeTypes = Collections.unmodifiableList(mimeTypes);
        }
        this.enabled = isEnabled;
        if (null != excludePaths) {
            this.excludePaths = Collections.unmodifiableList(excludePaths);
        }
    }

}
