package com.vonage.core.models.content;


import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.msm.api.LiveRelationshipManager;

import com.vonage.core.services.XFPathPerLanguage;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Sling Model for Hero Form
 */
@Model(adaptables = { Resource.class })
public class LightboxCtaComponentModel {

    /**
     * The Constant LOGGER.
     */

    public static final Logger LOGGER = LoggerFactory.getLogger(LightboxFormComponentModel.class);

    /**
     * Slash Delimiter
     */
    private static final String PATH_DELIMITER = "/";
    /**
     * Path Delimiter
     */
    private static final char PATH_DELIMITER_CHAR = '/';

    /**
     * relationshopManager
     */
    @OSGiService
    private LiveRelationshipManager relationshipManager;

    /**
     * currentResource
     */
    @Self
    private Resource resource;

     /**
     * relationshopManager
     */
    @OSGiService
    private XFPathPerLanguage xfFPathPerLanguage;

      /**
     *
     * resourceResolver
     */
    @Self
    @Inject
    @Source("sling-object")
    private ResourceResolver  resourceResolver;

    /**
     * formPath
     */
    @ValueMapValue(name = "formPath", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String formPath;

    /**
     * confMessagePath
     */
    @ValueMapValue(name = "confMessagePath", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String confMessagePath;

     /**
     * lightboxColor
     */
    @ValueMapValue(name = "lightboxColor", injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "default")
    private String lightboxColor;

    /** checkDefault */
    private Boolean lightboxDefault = false;



    /**
     * init method
     * @throws WCMException exception
     */
    @PostConstruct
    protected final void init() throws WCMException {
      //Do all below logic only for internal form links.
      //For external smply return the form path
      if (this.formPath.startsWith("/content/")) {
        HashMap<String, String> xfPaths = xfFPathPerLanguage.getLocaleSpecificXFPath(formPath,
        confMessagePath,
        resource);
        this.formPath = xfPaths.get("formPath");
        this.confMessagePath = xfPaths.get("confMessagePath");

        // Update the lightbox form path to exact node to enable copy paste functionality - VONDEV-1110
        String rootPath = this.formPath + "/jcr:content/root";
        final String resoucreType = "vonage/components/utils/forms/container/lightboxForm";
        resource = resourceResolver.getResource(rootPath);
        if (resource != null) {
          Iterator<Resource> children = resource.listChildren();
          while (children.hasNext()) {
            Resource child = children.next();
            String resourceType = child.getValueMap().get("sling:resourceType", (String) null);
            if (StringUtils.equals(resourceType, resoucreType)) {
              this.formPath = child.getPath();
            }
          }
        }
     }
      if (lightboxColor.equals("default")) {
          lightboxDefault = true;
      }
    }


    /**
     * returns formPath
     * @return formPath
     */
    public final String getFormPath() {
        return this.formPath;
    }


    /**
     *
     * @return confMessagePath
     */
    public final String getConfMessagePath() {
        return this.confMessagePath;
    }
    /**
     *
     * @return lightboxCOlor
     */
    public final String getLightboxColor() {
      return lightboxColor;
    }
    /**
     *
     * @return lightboxDefault
     */
    public final Boolean getLightboxDefault() {
      return lightboxDefault;
    }

}
