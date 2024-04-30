package com.vonage.core.models.structure.resources;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.resource.details.AssetDetails;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.commons.Externalizer;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.commons.lang.StringUtils;

import com.vonage.core.constants.VonageImageConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sling Model for PageSchema
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class PageSchemaModel {

    /**
     * log variable
     */
    private static final Logger LOG = LoggerFactory.getLogger(PageSchemaModel.class);

    /** string to remove constant */
    private static final String PREFIX_TO_REMOVE = "vonage:";

    /**
     * pageName variable
     */
    private String pageName;

    /**
     * browserUrl variable
     */
    private String browserUrl;

    /**
     *
     * String isBlogPage
     */
    private boolean blogPage = false;

    /**
     * Page
     */
    @Inject
    private Page currentPage;

    /**
     * resource resolver
     */
    @Inject
    private ResourceResolver resourceResolver;

    /**
     * Externalizer
     */
    @Inject // OSGi Service
    private Externalizer externalizer;

    /**
     * Published Date
     */
    @ValueMapValue(name = "datePublished", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String datePublished;

    /**
     * Author
     */
    @ValueMapValue(name = "author", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String author;

    /**
     * image resource
     */
    @ChildResource(name = "image", injectionStrategy = InjectionStrategy.OPTIONAL)
    private Resource image;

    /**
     * thumbnail image
     */
    private String thumbnailImgPath;

    /**
    * init method
    */
    @PostConstruct
    protected final void init() {
        setPageName();
        setBrowserUrl();
        // check if blog page
        // We only need date published, author and image for the article schema
        if (StringUtils.contains(currentPage.getPath(), "/resources/articles")) {
            this.blogPage = true;
            setDatePublished();
            setAuthor();
            setThumbnailImgPath();
        }
    }

    /**
    * Set the page name
    */
    private void setPageName() {

        try {
            Node currentPageNode = this.currentPage.adaptTo(Node.class);
            if (currentPageNode != null) {
                pageName = currentPageNode.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * browser url
     */
    private void setBrowserUrl() {
        this.browserUrl = externalizer.publishLink(resourceResolver, "https", this.currentPage.getPath());
    }

    /**
     * Set formatted publish date
     */
    private void setDatePublished() {
        if (datePublished != null && datePublished.length() >= 10) {
        datePublished = datePublished.substring(0, 10);
        String[] parts = datePublished.split("-");
        datePublished = parts[1] + "/" + parts[2] + "/" + parts[0];
        } else {
        datePublished = "";
        }
    }

    /**
     * Set Author
     */
    private void setAuthor() {
        if (author != null) {
            final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
            Tag tag = tagManager.resolve(author);
            author = tag.getTitle();
        }
    }

    /**
     * Set Thumbnail Image Path
     */
    private void setThumbnailImgPath() {
        thumbnailImgPath = VonageImageConstants.VONAGE_DEFAULT_LOGO_IMG;
        try {
            if (this.image != null && this.image.adaptTo(Node.class).hasProperty("fileReference")) {
                thumbnailImgPath = this.image.getValueMap().get("fileReference").toString();
            }

            Resource res = resourceResolver.getResource(thumbnailImgPath);
            String resolvedImagePath = "";

            if (res != null) {
              AssetDetails assetDetails = new AssetDetails(res);
              resolvedImagePath = externalizer.publishLink(resourceResolver, "https", thumbnailImgPath);
            }
            if (resolvedImagePath != null) {
                thumbnailImgPath = resolvedImagePath;
            }

        } catch (RepositoryException e) {
            LOG.error("There is a repository exception on Page Schema Model - setting thumbnail image", e);
        }
    }

    /**
    * Get pageName
    * @return pageName
    */
    public final String getPageName() {
        return pageName;
    }

    /**
     * Get Browser URL
     * @return browser url
     */
    public final String getBrowserUrl() {
        return this.browserUrl;
    }

   /**
     * Get Date Published
     * @return date published
     */
    public final String getDatePublished() {
        return this.datePublished;
    }

    /**
     * Get Author
     * @return author
     */
    public final String getAuthor() {
        return this.author;
    }

    /**
    * @return blogPage
    */
    public final boolean isBlogPage() {
        return blogPage;
    }

    /**
     * Thumbnail Image Path
     * @return thumbnailImgPath
     */
    public final String getThumbnailImgPath() {
        return this.thumbnailImgPath;
      }

}
