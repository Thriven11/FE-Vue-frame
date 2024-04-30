package com.vonage.core.models.content;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vonage.core.services.XFPathPerLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.day.cq.wcm.api.Page;

import com.day.cq.wcm.msm.api.LiveRelationshipManager;
/**
 * Sling Model for Hero Form
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class },
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroForm {

    /** Text Fields - Left Column/Dialog tab */

     /**
     * Logger variable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HeroForm.class);

     /**
     * Slash Delimiter
     */
    private static final String PATH_DELIMITER = "/";
    /**
     * Path Delimiter
     */
    private static final char PATH_DELIMITER_CHAR = '/';


     /**
     * List
     */
    private ArrayList<BulletPoints> bulletPoints = new ArrayList<>();

    /**
     *
     * eyebrowContent
     */
    @ValueMapValue(name = "eyebrowContent", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String eyebrowContent;

     /**
     *
     * title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String title;

     /**
     *
     * desc
     */
    @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String description;

    /**
     *
     *  confMessagePath
     */
    @ValueMapValue(name = "confMessagePath", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String confMessagePath;

    /**
     * inject CurrentPage
     */
    @Inject
    private Page currentPage;

    /**
     *
     * @return page
     */
    public final Page getCurrentPage() {
      return currentPage;
    }

    /**
     * inject resoureResolver
     */
    @Inject
    private ResourceResolver resourceResolver;

    /**
     * relationshopManager
     */
    @OSGiService
    private LiveRelationshipManager relationshipManager;

    /**
     * currentResource
     */
    @Inject
    private Resource resource;



    /**
     *
     * @return ResourceResolver
     */
    public final ResourceResolver getResourceResolver() {
      return resourceResolver;
    }

    /**
      * childResource
      */

    @ChildResource(name = "bulletPoints")
    private Resource bulletPointss;


    /**
     * relationshopManager
     */
    @OSGiService
    private XFPathPerLanguage xfFPathPerLanguage;


    /**
     *
     *  init
     */
    @PostConstruct
    protected final void init() {
      if (this.confMessagePath.startsWith("/content/")) {
        HashMap<String, String> xfPaths = xfFPathPerLanguage.getLocaleSpecificXFPath(null,
                                          confMessagePath,
                                          resource);
        this.confMessagePath = xfPaths.get("confMessagePath");

          if (bulletPointss != null) {
            Iterator<Resource> iterator = bulletPointss.listChildren();
            while (iterator.hasNext()) {
                Resource child = iterator.next();
                BulletPoints bp = child.adaptTo(BulletPoints.class);
                bulletPoints.add(bp);

            }
          }
        }

    }

    /**
     *
     * @return eyebrow
     */

    public final String getEyebrowContent() {
        return eyebrowContent;
    }


     /**
     *
     * @return conf
     */
    public final String getConfMessagePath() {
        return confMessagePath;
    }

     /**
     *
     * @return title
     */
    public final String getTitle() {
      return title;
    }
    /**
     *
     * @return description
     */
    public final String getDescription() {
      return description;
    }

     /**
     *
     * @return bulletPoints
     */
    public final ArrayList<BulletPoints> getBulletPoints() {
      return bulletPoints;
    }

    /**
     *
     * @return bulletPointss
     */

    public final Resource getBulletPointss() {
      return bulletPointss;
    }
}
