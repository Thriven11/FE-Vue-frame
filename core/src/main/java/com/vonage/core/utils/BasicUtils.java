package com.vonage.core.utils;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.sling.api.resource.Resource;

import com.day.cq.replication.ReplicationStatus;

/**
 * BasicUtils class to contain generic methods can be utilized across the
 * project
 *
 * @author Vonage
 *
 */
public final class BasicUtils {

    /**
     * Static class should not be initialized.
     */
    private BasicUtils() {
        // No implementation
    }

    /**
     * checks if the path belongs to matching paths list
     *
     * @param matchingPaths - matchingPaths
     * @param path          - path
     * @return boolean value of whether path is present in list or not
     */
    public static boolean isMatchingPath(final List<String> matchingPaths, final String path) {
        boolean isMatching = false;
        for (String matchingPath : matchingPaths) {
            if (matchAPath(matchingPath, path)) {
                return true;
            }
        }
        return isMatching;
    }

    /**
     * Match a single path
     *
     * @param matchingPath regex or absolute path
     * @param path         the path
     * @return boolean true or false
     */
    public static boolean matchAPath(final String matchingPath, final String path) {
        Pattern p = Pattern.compile(matchingPath);
        return (p.matcher(path).matches() || path.contains(matchingPath));
    }

    /**
     * Check if a resource is activated
     *
     * @param resource - Resource
     * @return boolean - true if resource is an activated resource
     */
    public static boolean isActivatedResource(final Resource resource) {
        ReplicationStatus status = resource.adaptTo(ReplicationStatus.class);
        return (null != status && status.isActivated());
    }
}
