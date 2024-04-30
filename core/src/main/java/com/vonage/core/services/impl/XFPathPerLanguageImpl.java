package com.vonage.core.services.impl;

import java.util.HashMap;



import com.day.cq.wcm.msm.api.LiveRelationshipManager;
import com.vonage.core.utils.ServiceUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.day.text.Text;

import com.vonage.core.constants.VonageConstants;

import com.vonage.core.services.XFPathPerLanguage;

/**
 * Implementation class for Careers Data Connector
 *
 * @author Vonage
 *
 */
@Component(service = XFPathPerLanguage.class)
public class XFPathPerLanguageImpl implements XFPathPerLanguage {

  /**
     * log variable
     */
    private static final Logger LOG = LoggerFactory.getLogger(XFPathPerLanguageImpl.class);

     /**
     * Slash Delimiter
     */
    private static final String PATH_DELIMITER = "/";


  /**
   * Resource Resolver Factory
   */
  @Reference
  private ResourceResolverFactory resolverFactory;

  /**
   * Resource Resolver
   */
  private ResourceResolver resourceResolver;


    /**
     * relationshopManager
     */
    @Reference
    private LiveRelationshipManager relationshipManager;

  /**
   * Activate method
   */
  @Activate
  @Modified
  protected final void activate() {
    resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
  }



  @Override
  public final HashMap<String, String> getLocaleSpecificXFPath(final String formPath, final String confMessagePath,
      final Resource resource) {
        HashMap<String, String> xfPaths = new HashMap<>();
        PageManager pm = resourceResolver.adaptTo(PageManager.class);
            Page containingPage = pm.getContainingPage(resource);
            int xfRootDepth = 4;
            final int arraysize = 25;
            String[] xfPathTokens = new String[arraysize];
            if (formPath != null) {
              xfPathTokens = Text.explode(formPath, '/');
              if (StringUtils.contains(formPath, "live-copies")) {
                xfRootDepth = 6;
              }
            } else {
              xfPathTokens = Text.explode(confMessagePath, '/');
              if (StringUtils.contains(confMessagePath, "live-copies")) {
                xfRootDepth = 6;
              }
            }

            String localeXFFormPath = null;
            String localeXFconfMessagePath = null;
            String countryCode = containingPage.getLanguage().getCountry().toLowerCase();
            String languageCode = containingPage.getLanguage().getLanguage().toLowerCase();
            String locale = languageCode + "-" + countryCode;
            String[] xfRootTokens = new String[xfRootDepth];
            System.arraycopy(xfPathTokens, 0, xfRootTokens, 0, xfRootDepth);
            String xfRoot = StringUtils.join(PATH_DELIMITER, Text.implode(xfRootTokens, PATH_DELIMITER));
            try {
              if (relationshipManager.hasLiveRelationship(resource)
              && !relationshipManager.getLiveRelationship(resource, true).getStatus().isCancelled()
              && StringUtils.isNotEmpty(xfRoot)) {
                if (formPath != null) {
                  final String xfRelativePath = StringUtils.substring(formPath, xfRoot.length());
                  localeXFFormPath = StringUtils.join(
                    VonageConstants.XF_ROOT_PATH
                    + "/"
                    + containingPage.getLanguage().getLanguage()
                    + "-"
                    + containingPage.getLanguage().getCountry().toLowerCase(),
                    xfRelativePath);
                }
                if (confMessagePath != null) {
                  final String xfRelativeConfPath = StringUtils.substring(confMessagePath, xfRoot.length());
                  localeXFconfMessagePath = StringUtils.join(
                    VonageConstants.XF_ROOT_PATH
                    + "/"
                    + containingPage.getLanguage().getLanguage()
                    + "-"
                    + containingPage.getLanguage().getCountry().toLowerCase(),
                    xfRelativeConfPath);
                }
              }
            } catch (WCMException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            if (localeXFFormPath != null) {
              xfPaths.put("formPath", localeXFFormPath);
            } else {
              xfPaths.put("formPath", formPath);
            }
            if (localeXFconfMessagePath != null) {
              xfPaths.put("confMessagePath", localeXFconfMessagePath);
            } else {
              xfPaths.put("confMessagePath", confMessagePath);
            }


        return xfPaths;
  }
}
