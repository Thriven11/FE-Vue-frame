package com.vonage.core.beans;

/**
 * Helper bean to get a CTA
 *
 * @author Vonage
 *
 */
public interface CTA {

    /** @return url */
    String getHref();

    /**
     * Used to get original link path
     *
     * @return path
     */
    String getPath();

    /** @return label */
    String getLabel();

    /**
     * Used for Analytics purpose
     *
     * @return tagLabel
     **/
    String getTagLabel();

    /** @return target */
    String getTarget();

    /**
     * Used for social icons
     *
     * @return type
     */
    String getType();

    /**
     * Used for Aria Label
     *
     * @return ariaLabel
     */
    String getAriaLabel();

    /**
     * Used for Static Label
     *
     * @return staticLabel
     */
    String getStaticLabel();

    /**
     * Used for Youtube Video ID
     *
     * @return dataVideoId
     */
    String getDataVideoId();

    /** @return values are empty */
    boolean isEmpty();
}
