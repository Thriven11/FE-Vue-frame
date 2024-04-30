
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~ Copyright 2019 Adobe
~
~ Licensed under the Apache License, Version 2.0 (the "License");
~ you may not use this file except in compliance with the License.
~ You may obtain a copy of the License at
~
~     http://www.apache.org/licenses/LICENSE-2.0
~
~ Unless required by applicable law or agreed to in writing, software
~ distributed under the License is distributed on an "AS IS" BASIS,
~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
~ See the License for the specific language governing permissions and
~ limitations under the License.
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.vonage.core.models.content.core;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.ExperienceFragment;
import com.adobe.cq.xf.ExperienceFragmentsConstants;
import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.wcm.api.designer.ComponentStyle;
import com.day.cq.wcm.msm.api.LiveCopy;
import com.day.cq.wcm.msm.api.LiveRelationship;
import com.day.cq.wcm.msm.api.LiveRelationshipManager;
import com.day.text.Text;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vonage.core.constants.VonageConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RangeIterator;
import java.util.HashMap;
import java.util.Map;

/**
 * Sling model for Vonage Experience Fragment
 *
 * @author Vonage
 */
@Model(adaptables = SlingHttpServletRequest.class,
  adapters = {ExperienceFragment.class, ComponentExporter.class },
  resourceType = {"vonage/components/content/fragments/experiencefragment"})
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
  extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class VngExperienceFragmentImpl implements ExperienceFragment {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(VngExperienceFragmentImpl.class);

  /**
   * resource type
   */
  public static final String RESOURCE_TYPE_V1 = "core/wcm/components/experiencefragment/v1/experiencefragment";

  /**
   *  Slash Delimiter
   */
  private static final String PATH_DELIMITER = "/";
  /**
   *  Path Delimiter
   */
  private static final char PATH_DELIMITER_CHAR = '/';
  /**
   *  Content Root
   */
  private static final String CONTENT_ROOT = "/content";
  /**
   *  XF Root
   */
  private static final String EXPERIENCE_FRAGMENTS_ROOT = "/content/experience-fragments";

  /**
   *  jcr:content
   */
  private static final String JCR_CONTENT_ROOT = "/jcr:content";

  /**
   *  CSS Base Class
   */
  private static final String CSS_BASE_CLASS = "aem-xf";


  /**
   *  Request Object
   */
  @Self
  private SlingHttpServletRequest request;

  /**
   *  res current Resource
   */
  @Inject
  private Resource res;

  /**
   *  Resource Resovler
   */
  @SlingObject
  private ResourceResolver resolver;


  /**
   *  Current Page
   */
  @ScriptVariable
  private Page currentPage;

  /**
   * Class names of the responsive grid
   */
  private String classNames = CSS_BASE_CLASS;


  /**
   *  fragmentVariationPath
   */
  @ValueMapValue(name = ExperienceFragment.PN_FRAGMENT_VARIATION_PATH, injectionStrategy = InjectionStrategy.OPTIONAL)
  private String fragmentVariationPath;

  /**
   *  languageManager
   */
  @OSGiService
  private LanguageManager languageManager;

  /**
   *  relationshopManager
   */
  @OSGiService
  private LiveRelationshipManager relationshipManager;

  /**
   *  Custom css classes
   */
  @ValueMapValue(name = ComponentStyle.PN_CSS_CLASS)
  @Default(values = "")
  private String customCssClass;

  /**
   *  localizedFragmentVariationPath
   */
  private String localizedFragmentVariationPath;
  /**
   *  name
   */
  private String name;

  /**
   *  Current Resource
   */
  @Inject
  private Resource resource;

  /**
   * Child columns of the responsive grid
   */
  private final Map<String, ComponentExporter> children = new HashMap<>();

  /**
   *  construct
   */
  @PostConstruct
  protected final void initModel() {

    final PageManager pageManager = resolver.adaptTo(PageManager.class);

    if (pageManager != null) {
      /**
       * CurrentPage is null when accessing the sling model exporter.
       */
      if (currentPage == null) {
        currentPage = pageManager.getContainingPage(resource);
      }

      if (currentPage != null) {
        try {
          resolveLocalizedFragmentVariationPath();
        } catch (WCMException e) {
          e.printStackTrace();
        }
        resolveName(pageManager);
        // retrieveExperienceFragmentChildModels();
      } else {
        LOGGER.error("Could not resolve currentPage!");
      }
    }

    appendCssClassNames();
  }


  @Override
  public final String getLocalizedFragmentVariationPath() {
    return localizedFragmentVariationPath;
  }
  /**
   *  gets Name
   * @return name
   */
  @JsonIgnore
  public final String getName() {
    return name;
  }

  @Override public final String getExportedType() {
    return request.getResource().getResourceType();
  }

  /**
   *  getExportedItems
   * @return get export items
   */
  public final Map<String, ? extends ComponentExporter> getExportedItems() {
    return children;
  }

  /**
   *  getExportedItemsOrder
   * @return get export items order
   */
  public final String[] getExportedItemsOrder() {
    if (children.isEmpty()) {
      return  new String[0];
    } else {
      return children.keySet().toArray(new String[children.size()]);
    }
  }

  /**
   * @return The CSS class names to be applied to the current grid.
   * @deprecated Use {@link #getCssClassNames()}
   */
  @JsonProperty("classNames")
  public final String getCssClassNames() {
    return classNames;
  }

  /**
   *  isConfigured
   * @return is configured
   */
  @JsonInclude
  public final boolean isConfigured() {
    return StringUtils.isNotEmpty(localizedFragmentVariationPath) && !children.isEmpty();
  }

  /**
   *  resolveLocalizedFragmentVariationPath
   * @throws  WCMException exception
   */
  private void resolveLocalizedFragmentVariationPath() throws WCMException {
    if (inTemplate() || (relationshipManager.hasLiveRelationship(res)
      && !relationshipManager.getLiveRelationship(res, true).getStatus().isCancelled())) {
      if (currentPage != null) {
        final String pagePath = currentPage.getPath();
        final String currentPageRootPath = getLocalizationRoot(pagePath);
        // we should use getLocalizationRoot instead of getXfLocalizationRoot once the XF UI
        // supports creating Live and Language Copies
        final String xfRootPath = getXfLocalizationRoot(fragmentVariationPath, currentPageRootPath);
        if (StringUtils.isNotEmpty(currentPageRootPath) && StringUtils.isNotEmpty(xfRootPath)) {
          final String xfRelativePath = StringUtils.substring(fragmentVariationPath, xfRootPath.length());
          // final String localizedXfRootPath = StringUtils.replace(currentPageRootPath, CONTENT_ROOT,
          //   EXPERIENCE_FRAGMENTS_ROOT, 1);
          localizedFragmentVariationPath = StringUtils.join(VonageConstants.XF_ROOT_PATH + "/"
          + currentPage.getLanguage().getLanguage() + "-" + currentPage.getLanguage().getCountry().toLowerCase(),
            xfRelativePath, JCR_CONTENT_ROOT);
        }
      }
    } else {
      final String xfContentPath = StringUtils.join(fragmentVariationPath, JCR_CONTENT_ROOT);
      if (!resourceExists(localizedFragmentVariationPath) && resourceExists(xfContentPath)) {
        localizedFragmentVariationPath = xfContentPath;
      }
    }

    if (!isExperienceFragmentVariation(localizedFragmentVariationPath)) {
      localizedFragmentVariationPath = null;
    }
  }

  /**
   * Returns the localization root of the res defined at the given path.
   *
   * Use case                                  | Path                                 | Root
   * ------------------------------------------|--------------------------------------|------------------
   * 1. No localization                        | /content/mysite/mypage               | null
   * 2. Language localization                  | /content/mysite/en/mypage            | /content/mysite/en
   * 3. Country-language localization          | /content/mysite/us/en/mypage         | /content/mysite/us/en
   * 4. Country-language localization (variant)| /content/us/mysite/en/mypage         | /content/us/mysite/en
   * 5. Blueprint                              | /content/mysite/blueprint/mypage     | /content/mysite/blueprint
   * 6. Live Copy                              | /content/mysite/livecopy/mypage      | /content/mysite/livecopy
   *
   * @param path the res path
   * @return the localization root of the res at the given path if it exists, {@code null} otherwise
   */
  private String getLocalizationRoot(final String path) {
    String root = null;
    if (!StringUtils.isEmpty(path)) {
      Resource resLocal = resolver.getResource(path);
      root = getLanguageRoot(resLocal);
      if (StringUtils.isEmpty(root)) {
        root = getBlueprintPath(resLocal);
      }
      if (StringUtils.isEmpty(root)) {
        root = getLiveCopyPath(resLocal);
      }
    }
    return root;
  }

  /**
   * Returns the language root of the res.
   *
   * @param mainRes the res
   * @return the language root of the res if it exists, {@code null} otherwise
   */
  private String getLanguageRoot(final Resource mainRes) {
    Page rootPage = languageManager.getLanguageRoot(mainRes);
    if (rootPage != null) {
      return rootPage.getPath();
    }
    return null;
  }

  /**
   * Returns the path of the blueprint of the res.
   *
   * @param bluePrintRes the res
   * @return the path of the blueprint of the res if it exists, {@code null} otherwise
   */
  private String getBlueprintPath(final Resource bluePrintRes) {
    try {
      if (relationshipManager.isSource(bluePrintRes)) {
        // the res is a blueprint
        RangeIterator liveCopiesIterator = null;
        liveCopiesIterator = relationshipManager.getLiveRelationships(bluePrintRes, null, null);
        if (liveCopiesIterator != null) {
          LiveRelationship relationship = (LiveRelationship) liveCopiesIterator.next();
          LiveCopy liveCopy = relationship.getLiveCopy();
          if (liveCopy != null) {
            return liveCopy.getBlueprintPath();
          }
        }
      }
    } catch (WCMException e) {
      LOGGER.error("Unable to get the blueprint: {}", e.getMessage());
    }
    return null;
  }

  /**
   * Returns the path of the live copy of the res.
   *
   * @param liveCopyRes the res
   * @return the path of the live copy of the res if it exists, {@code null} otherwise
   */
  private String getLiveCopyPath(final Resource liveCopyRes) {
    try {
      if (relationshipManager.hasLiveRelationship(liveCopyRes)) {
        // the res is a live copy
        LiveRelationship liveRelationship = relationshipManager.getLiveRelationship(liveCopyRes, false);
        if (liveRelationship != null) {
          LiveCopy liveCopy = liveRelationship.getLiveCopy();
          if (liveCopy != null) {
            return liveCopy.getPath();
          }
        }
      }
    } catch (WCMException e) {
      LOGGER.error("Unable to get the live copy: {}", e.getMessage());
    }
    return null;
  }

  /**
   * Returns the localization root of the experience fragment path based on the localization root of the current page.
   *
   * As of today (08/aug/2019) the XF UI does not support creating Live and Language Copies, which prevents getRoot
   * to be used with XF.
   * This method works around this issue by deducting the XF root from the XF path and the root of the current page.
   *
   * @param xfPath the experience fragment path
   * @param currentPageRoot the localization root of the current page
   * @return the localization root of the experience fragment path if it exists, {@code null} otherwise
   */
  private String getXfLocalizationRoot(final String xfPath, final String currentPageRoot) {
    String xfRoot = null;
    if (!StringUtils.isEmpty(xfPath) && !StringUtils.isEmpty(currentPageRoot)
      && resolver.getResource(currentPageRoot) != null) {
      String[] xfPathTokens = Text.explode(xfPath, PATH_DELIMITER_CHAR);
      String[] referenceRootTokens = Text.explode(currentPageRoot, PATH_DELIMITER_CHAR);
      int xfRootDepth = 4;
      if (StringUtils.contains(xfPath, "live-copies")) {
        xfRootDepth = 6;
      }
      if (xfPathTokens.length >= xfRootDepth) {
        String[] xfRootTokens = new String[xfRootDepth];
        System.arraycopy(xfPathTokens, 0, xfRootTokens, 0, xfRootDepth);
        xfRoot = StringUtils.join(PATH_DELIMITER, Text.implode(xfRootTokens, PATH_DELIMITER));
      }
    }
    return xfRoot;
  }

  /**
   * Checks if the resource exists at the given path.
   *
   * @param path the resource path
   * @return {@code true} if the resource exists, {@code false} otherwise
   */
  private boolean resourceExists(final String path) {
    return (StringUtils.isNotEmpty(path) && resolver.getResource(path) != null);
  }

  /**
   * Checks if the res exists at the given path.
   *
   * @param path the res path
   * @return {@code true} if the res exists, {@code false} otherwise
   */
  private boolean resExists(final String path) {
    return (StringUtils.isNotEmpty(path) && resolver.getResource(path) != null);
  }

  /**
   * Checks if the res is defined in the template.
   *
   * @return {@code true} if the res is defined in the template, {@code false} otherwise
   */
  private boolean inTemplate() {
    Template template = currentPage.getTemplate();
    return template != null && StringUtils.startsWith(res.getPath(), template.getPath());
  }

  /**
   * Checks if the res at the given path is an Experience Fragment variation.
   * @param varPath the res path
   * @return {@code true} if the res is an XF variation, {@code false} otherwise
   *
   */
  private boolean isExperienceFragmentVariation(final String varPath) {
    if (StringUtils.isNotEmpty(varPath)) {
      Resource xfRes = resolver.getResource(varPath);
      if (xfRes != null) {
        ValueMap properties = xfRes.getValueMap();
        String xfVariantType = properties.get(ExperienceFragmentsConstants.PN_XF_VARIANT_TYPE, String.class);
        return xfVariantType != null;
      }
    }
    return false;
  }

  /**
   * Resolves Name
   * @param pageManager Page Manager
   */
  private void resolveName(final PageManager pageManager) {
    final Page xfVariationPage = pageManager.getPage(fragmentVariationPath);
    if (xfVariationPage != null) {
      final Page xfPage = xfVariationPage.getParent();
      if (xfPage != null) {
        name = xfPage.getName();
      }
    }
  }

  /**
   * appendCssClassNames
   */
  private void appendCssClassNames() {

    if (children.isEmpty()) {
      classNames += " empty";
    }
    if (StringUtils.isNotEmpty(customCssClass)) {
      classNames += "";
    } else {
      classNames += customCssClass;
    }
  }

}
