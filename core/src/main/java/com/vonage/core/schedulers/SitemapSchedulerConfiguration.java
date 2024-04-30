package com.vonage.core.schedulers;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Default Configuration for Sitemap Config Service.
 */

@ObjectClassDefinition(name = "Vonage - Sitemap Scheduler Factory", description = "Sitemap scheduler Configuration")
public @interface SitemapSchedulerConfiguration {

    /**
     * Attribute for Page Sitemap Destination Path
     *
     * @return targetPath
     */
    @AttributeDefinition(name = "Target sitemap path",
            description = "Complete sitemap path e.g. /content/vonage/en-us/sitemap.xml")
    String targetPath();

    /**
     * Attribute for root path.
     *
     * @return sitemapRoot
     */
    @AttributeDefinition(name = "Sitemap Root", description = "Sitemap root path e.g./content/vonage/en-us/articles")
    String sitemapRoot();

    /**
     * Attribute for cronExpression.
     *
     * @return cronExpression
     */
    @AttributeDefinition(name = "Cron Expression",
            description = "Cron Expression to run the scheduler to re-create the sitemap" + " e.g. 0 0/5 * * * ? *")
    String cronExpression();

    /**
     * Attribute for enabling/ disabling the scheduler job
     *
     * @return enableScheduler
     */
    @AttributeDefinition(name = "Enabled",
            description = "Check to enable the scheduler, Uncheck to disable the scheduler job run")
    boolean enableScheduler() default true;

    /**
     * Attribute for URL Rewrites
     *
     * @return urlRewrites
     */
    @AttributeDefinition(name = "Rewrite Rules", description = "Colon separated URL rewrites to adjust the <loc>,"
            + " to match your dispatcher's apache rewrites e.g /content/vonage/en-us:/newera")
    String[] urlRewrites();

    /**
     * Attribute for excluding page paths.
     *
     * @return excludePath
     */
    @AttributeDefinition(name = "Exclude Path",
            description = "Paths to be excluded from sitemap e.g. /content/vonage/en-us/articles/some-hidden-page")
    String[] excludePaths();

    /**
     * Attribute for URL Rewrites
     *
     * @return mimeTypes
     */
    @AttributeDefinition(name = "Mime Types for Asset Sitemap",
            description = "Specify Mime Types only for Asset sitemap. E.g. image/png,"
                    + "image/jpg, image/jpeg. MimeType is picked from Asset's metdata node with dam:MIMEtype "
                    + "property name.")
    String[] mimeTypes();

}
