// package com.vonage.core.models.structure;

// import java.util.HashMap;
// import java.util.Map;

// import javax.annotation.PostConstruct;
// import javax.inject.Inject;
// import javax.jcr.RangeIterator;

// import com.adobe.cq.export.json.ComponentExporter;
// import com.adobe.cq.export.json.ContainerExporter;
// import com.adobe.cq.export.json.ExporterConstants;
// import com.adobe.cq.wcm.core.components.models.ExperienceFragment;
// import com.adobe.cq.xf.ExperienceFragmentsConstants;
// import com.day.cq.wcm.api.LanguageManager;
// import com.day.cq.wcm.api.Page;
// import com.day.cq.wcm.api.PageManager;
// import com.day.cq.wcm.api.Template;
// import com.day.cq.wcm.api.WCMException;
// import com.day.cq.wcm.api.designer.ComponentStyle;
// import com.day.cq.wcm.msm.api.LiveRelationshipManager;
// import com.day.text.Text;
// import com.day.cq.wcm.msm.api.LiveCopy;
// import com.day.cq.wcm.msm.api.LiveRelationship;

// import org.apache.commons.lang3.StringUtils;
// import org.apache.sling.api.SlingHttpServletRequest;
// import org.apache.sling.api.resource.Resource;
// import org.apache.sling.api.resource.ResourceResolver;
// import org.apache.sling.api.resource.ValueMap;
// import org.apache.sling.models.annotations.Default;
// import org.apache.sling.models.annotations.DefaultInjectionStrategy;
// import org.apache.sling.models.annotations.Exporter;
// import org.apache.sling.models.annotations.Model;
// import org.apache.sling.models.annotations.Via;
// import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
// import org.apache.sling.models.annotations.injectorspecific.OSGiService;
// import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
// import org.apache.sling.models.annotations.injectorspecific.Self;
// import org.apache.sling.models.annotations.injectorspecific.SlingObject;
// import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
// import org.apache.sling.models.annotations.via.ResourceSuperType;
// import org.apache.sling.models.factory.ModelFactory;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.annotation.JsonProperty;

// /**
//  * Vonage Experience Fragment overwriting core components
//  */
// @Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
//         resourceType = "vonage/components/content/fragments/experiencefragment",
//        adapters = {ExperienceFragment.class, ComponentExporter.class, ContainerExporter.class })
// @Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
//           extensions = ExporterConstants.SLING_MODEL_EXTENSION)
// public class VngExperienceFragment implements ExperienceFragment {

//     /**
//      *  Logger
//      */
//     private static final Logger LOGGER = LoggerFactory.getLogger(VngExperienceFragment.class);

//      /**
//      *  Resource Type
//      */
//     public static final String RESOURCE_TYPE_V1 = "core/wcm/components/experiencefragment/v1/experiencefragment";

//     /**
//      *  Path Delimiter
//      */
//     private static final String PATH_DELIMITER = "/";
//     /**
//      *  Path Char Delimiter
//      */
//     private static final char PATH_DELIMITER_CHAR = '/';
//     /**
//      *  Content Root
//      */
//     private static final String CONTENT_ROOT = "/content";
//     /**
//      *  Experience Fragments Root
//      */
//     private static final String EXPERIENCE_FRAGMENTS_ROOT = "/content/experience-fragments";
//     /**
//      *  JCR_CONTENT root
//      */
//     private static final String JCR_CONTENT_ROOT = "/jcr:content";
//     /**
//      * CSS Empty class
//      */
//     private static final String CSS_EMPTY_CLASS = "empty";
//     /**
//      *  CSS Base Class
//      */
//     private static final String CSS_BASE_CLASS = "aem-xf";

//      /**
//      *  XF Object from supertype
//      */
//     @Self @Via(type = ResourceSuperType.class)
//     private ExperienceFragment superXf;

//     /**
//      *  HTTP request
//      */
//     @Self
//     private SlingHttpServletRequest request;

//     /**
//      *  Current Resource
//      */
//     @Inject
//     private Resource resource;

//     /**
//      *  Resource Resolver
//      */
//     @SlingObject
//     private ResourceResolver resolver;

//     /**
//      *  Current Page
//      */
//     @ScriptVariable
//     private Page currentPage;

//     /**
//      * Fragment Variation Path
//      */
//     @ValueMapValue(name = ExperienceFragment.PN_FRAGMENT_VARIATION_PATH,
// injectionStrategy = InjectionStrategy.OPTIONAL)
//     private String fragmentVariationPath;

//     /**
//      *  Language Manager
//      */
//     @OSGiService
//     private LanguageManager languageManager;

//     /**
//      *  Relationship Manager
//      */
//     @OSGiService
//     private LiveRelationshipManager relationshipManager;

//     /**
//      *  Custom css classes
//      */
//     @ValueMapValue(name = ComponentStyle.PN_CSS_CLASS)
//     @Default(values = "")
//     private String customCssClass;

//     /**
//      *  Model Factory
//      */
//     @Inject
//     private ModelFactory modelFactory;

//     /**
//      *  localizedFragmentVariationPath
//      */
//     private String localizedFragmentVariationPath;
//     /**
//      *  name
//      */
//     private String name;

//     /**
//      * Class names of the responsive grid
//      */
//     private String classNames = CSS_BASE_CLASS;

//     /**
//      * Child columns of the responsive grid
//      */
//     private final Map<String, ComponentExporter> children = new HashMap<>();

//     /**
//      *  Initialize Model
//      */
//     @PostConstruct
//     protected final void initModel() {

//         final PageManager pageManager = resolver.adaptTo(PageManager.class);

//         if (pageManager != null) {
//             /**
//              * CurrentPage is null when accessing the sling model exporter.
//              */
//             if (currentPage == null) {
//                 currentPage = pageManager.getContainingPage(resource);
//             }

//             if (currentPage != null) {
//                 resolveLocalizedFragmentVariationPath();
//                 resolveName(pageManager);
//                 // retrieveExperienceFragmentChildModels();
//             } else {
//                 LOGGER.error("Could not resolve currentPage!");
//             }
//         }

//         appendCssClassNames();
//     }

//     /**
//      *  getLocalizedFragmentVariationPath
//      * @return local path
//      */
//     @Override
//     public final String getLocalizedFragmentVariationPath() {
//         return localizedFragmentVariationPath;
//     }

//     /**
//      *  test string
//      *  @return yanakamma
//      */
//     public final String getTestString() {
//         return "nee yankammas";
//     }

//     /**
//      *  getName
//      * @return name name
//      */
//     @JsonIgnore
//     public final String getName() {
//         return name;
//     }

//     /**
//      *  getExportedType
//      * @return export type
//      */
//     @Override
//     public final String getExportedType() {
//         return request.getResource().getResourceType();
//     }

//     /**
//      *  getExportedItems
//      * @return get export items
//      */
//     public final Map<String, ? extends ComponentExporter> getExportedItems() {
//         return children;
//     }

//     /**
//      *  getExportedItemsOrder
//      * @return get export items order
//      */
//     public final String[] getExportedItemsOrder() {
//         if (children.isEmpty()) {
//             return  new String[0];
//         } else {
//             return children.keySet().toArray(new String[children.size()]);
//         }
//     }

//     /**
//      * @return The CSS class names to be applied to the current grid.
//      * @deprecated Use {@link #getCssClassNames()}
//      */
//     @JsonProperty("classNames")
//     public final String getCssClassNames() {
//         return classNames;
//     }

//     /**
//      *  isConfigured
//      * @return is configured
//      */
//     @JsonInclude
//     public final boolean isConfigured() {
//         return StringUtils.isNotEmpty(localizedFragmentVariationPath) && !children.isEmpty();
//     }

//     /**
//      *  resolveLocalizedFragmentVariationPath
//      */
//     private void resolveLocalizedFragmentVariationPath() {
//         if (inTemplate()) {
//             if (currentPage != null) {
//                 final String pagePath = currentPage.getPath();
//                 final String currentPageRootPath = getLocalizationRoot(pagePath);
//                 // we should use getLocalizationRoot instead of getXfLocalizationRoot once the XF UI
//                 // supports creating Live and Language Copies
//                 final String xfRootPath = getXfLocalizationRoot(fragmentVariationPath, currentPageRootPath);
//                 if (StringUtils.isNotEmpty(currentPageRootPath) && StringUtils.isNotEmpty(xfRootPath)) {
//                     final String xfRelativePath = StringUtils.substring(fragmentVariationPath, xfRootPath.length());
//                     final String localizedXfRootPath = StringUtils.replace(currentPageRootPath, CONTENT_ROOT,
//                         EXPERIENCE_FRAGMENTS_ROOT, 1);
//                     localizedFragmentVariationPath = StringUtils.join(localizedXfRootPath,
//                         xfRelativePath, JCR_CONTENT_ROOT);
//                 }
//             }
//         }

//         final String xfContentPath = StringUtils.join(fragmentVariationPath, JCR_CONTENT_ROOT);
//         if (!resourceExists(localizedFragmentVariationPath) && resourceExists(xfContentPath)) {
//             localizedFragmentVariationPath = xfContentPath;
//         }
//         if (!isExperienceFragmentVariation(localizedFragmentVariationPath)) {
//             localizedFragmentVariationPath = null;
//         }
//     }




//     /**
//      * Returns the localization root of the resource defined at the given path.
//      *
//      * Use case                                  | Path                                 | Root
//      * ------------------------------------------|--------------------------------------|------------------
//      * 1. No localization                        | /content/mysite/mypage               | null
//      * 2. Language localization                  | /content/mysite/en/mypage            | /content/mysite/en
//      * 3. Country-language localization          | /content/mysite/us/en/mypage         | /content/mysite/us/en
//      * 4. Country-language localization (variant)| /content/us/mysite/en/mypage         | /content/us/mysite/en
//      * 5. Blueprint                              | /content/mysite/blueprint/mypage     | /content/mysite/blueprint
//      * 6. Live Copy                              | /content/mysite/livecopy/mypage      | /content/mysite/livecopy
//      *
//      * @param path the resource path
//      * @return the localization root of the resource at the given path if it exists, {@code null} otherwise
//      */
//     private String getLocalizationRoot(final String path) {
//         String root = null;
//         if (StringUtils.isNotEmpty(path)) {
//             final Resource localRes = resolver.getResource(path);
//             root = getLanguageRoot(localRes);
//             if (StringUtils.isEmpty(root)) {
//                 root = getBlueprintPath(localRes);
//             }
//             if (StringUtils.isEmpty(root)) {
//                 root = getLiveCopyPath(localRes);
//             }
//         }
//         return root;
//     }

//     /**
//      * Returns the language root of the resource.
//      *
//      * @param langRes the resource
//      * @return the language root of the resource if it exists, {@code null} otherwise
//      */
//     private String getLanguageRoot(final Resource langRes) {
//         final Page rootPage = languageManager.getLanguageRoot(langRes);
//         if (rootPage != null) {
//             return rootPage.getPath();
//         }
//         return null;
//     }

//     /**
//      * Returns the path of the blueprint of the resource.
//      *
//      * @param blueprintRes the resource
//      * @return the path of the blueprint of the resource if it exists, {@code null} otherwise
//      */
//     private String getBlueprintPath(final Resource blueprintRes) {
//         try {
//             if (relationshipManager.isSource(blueprintRes)) {
//                 // the resource is a blueprint
//                 final RangeIterator liveCopiesIterator = relationshipManager.getLiveRelationships(
//                     blueprintRes, null, null);
//                 if (liveCopiesIterator != null) {
//                     final LiveRelationship relationship = (LiveRelationship) liveCopiesIterator.next();
//                     final LiveCopy liveCopy = relationship.getLiveCopy();
//                     if (liveCopy != null) {
//                         return liveCopy.getBlueprintPath();
//                     }
//                 }
//             }
//         } catch (final WCMException e) {
//             LOGGER.error("Unable to get the blueprint: {}", e.getMessage());
//         }
//         return null;
//     }

//     /**
//      * Returns the path of the live copy of the resource.
//      *
//      * @param liveCopyRes the resource
//      * @return the path of the live copy of the resource if it exists, {@code null} otherwise
//      */
//     private String getLiveCopyPath(final Resource liveCopyRes) {
//         try {
//             if (relationshipManager.hasLiveRelationship(liveCopyRes)) {
//                 // the resource is a live copy
//                 final LiveRelationship liveRelationship =
// relationshipManager.getLiveRelationship(liveCopyRes, false);
//                 if (liveRelationship != null) {
//                     final LiveCopy liveCopy = liveRelationship.getLiveCopy();
//                     if (liveCopy != null) {
//                         return liveCopy.getPath();
//                     }
//                 }
//             }
//         } catch (final WCMException e) {
//             LOGGER.error("Unable to get the live copy: {}", e.getMessage());
//         }
//         return null;
//     }

//     /**
//      * Returns the localization root of the experience fragment path
//      * based on the localization root of the current page.
//      *
//      * As of today (08/aug/2019) the XF UI does not support creating Live and Language Copies, which prevents getRoot
//      * to be used with XF.
//      * This method works around this issue by deducting the XF root
//      * from the XF path and the root of the current page.
//      *
//      * @param xfPath the experience fragment path
//      * @param currentPageRoot the localization root of the current page
//      * @return the localization root of the experience fragment path if it exists, {@code null} otherwise
//      */
//     private String getXfLocalizationRoot(final String xfPath, final String currentPageRoot) {
//         String xfRoot = null;
//         if (StringUtils.isNotEmpty(xfPath) && StringUtils.isNotEmpty(currentPageRoot)
//                 && resolver.getResource(xfPath) != null && resolver.getResource(currentPageRoot) != null) {
//             final String[] xfPathTokens = Text.explode(xfPath, PATH_DELIMITER_CHAR);
//             final String[] referenceRootTokens = Text.explode(currentPageRoot, PATH_DELIMITER_CHAR);
//             final int xfRootDepth = referenceRootTokens.length + 1;
//             if (xfPathTokens.length >= xfRootDepth) {
//                 final String[] xfRootTokens = new String[xfRootDepth];
//                 System.arraycopy(xfPathTokens, 0, xfRootTokens, 0, xfRootDepth);
//                 xfRoot = StringUtils.join(PATH_DELIMITER, Text.implode(xfRootTokens, PATH_DELIMITER));
//             }
//         }
//         return xfRoot;
//     }

//     /**
//      * Checks if the resource exists at the given path.
//      *
//      * @param path the resource path
//      * @return {@code true} if the resource exists, {@code false} otherwise
//      */
//     private boolean resourceExists(final String path) {
//         return (StringUtils.isNotEmpty(path) && resolver.getResource(path) != null);
//     }

//     /**
//      * Checks if the resource is defined in the template.
//      *
//      * @return {@code true} if the resource is defined in the template, {@code false} otherwise
//      */
//     private boolean inTemplate() {
//         if (currentPage == null) {
//             return false;
//         }

//         final Template template = currentPage.getTemplate();
//         return template != null && StringUtils.startsWith(resource.getPath(), template.getPath());
//     }

//     /**
//      * Checks if the resource at the given path is an Experience Fragment variation.
//      * @param path path
//      * @return {@code true} if the resource is an XF variation, {@code false} otherwise
//      */
//     private boolean isExperienceFragmentVariation(final String path) {
//         if (StringUtils.isNotEmpty(path)) {
//             final Resource xfRes = resolver.getResource(path);
//             if (xfRes != null) {
//                 final ValueMap properties = xfRes.getValueMap();
//                 final String xfVariantType = properties.get(
//                     ExperienceFragmentsConstants.PN_XF_VARIANT_TYPE, String.class);
//                 return xfVariantType != null;
//             }
//         }
//         return false;
//     }

//     /**
//      * Resolves Name
//      * @param pageManager Page Manager
//      */
//     private void resolveName(final PageManager pageManager) {
//         final Page xfVariationPage = pageManager.getPage(fragmentVariationPath);
//         if (xfVariationPage != null) {
//             final Page xfPage = xfVariationPage.getParent();
//             if (xfPage != null) {
//                 name = xfPage.getName();
//             }
//         }
//     }

//     /**
//      * retrieveExperienceFragmentChildModels
//      */
//     // private void retrieveExperienceFragmentChildModels() {
//     //     if (StringUtils.isNotBlank(localizedFragmentVariationPath) &&
//     // resourceExists(localizedFragmentVariationPath)) {
//     //         final Resource experienceFragmentResource = resolver.getResource(localizedFragmentVariationPath);

//     //         if (experienceFragmentResource != null) {
//     //             final Iterator<Resource> experienceFragmentVariantChildResources
//     //                 = experienceFragmentResource.listChildren();
//     //             final Map<String, ComponentExporter> resolvedChildren©
// = ContentFragmentUtils.getComponentExporters(
//     //                 experienceFragmentVariantChildResources, modelFactory, request);
//     //             children.putAll(resolvedChildren);
//     //         }
//     //     }
//     // }

//      /**
//      * appendCssClassNames
//      */
//     private void appendCssClassNames() {

//         if (children.isEmpty()) {
//             classNames += " empty";
//         }
//         if (StringUtils.isNotEmpty(customCssClass)) {
//             classNames += "";
//         } else {
//             classNames += customCssClass;
//         }
//     }
// }
