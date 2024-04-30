package com.vonage.core.models.structure;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.adobe.cq.wcm.core.components.models.Image;
import com.adobe.cq.wcm.core.components.models.ImageArea;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.wcm.api.Page;
// import com.day.cq.dam.api.Asset;
// import com.day.cq.dam.commons.util.DamUtil;
import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.apache.sling.settings.SlingSettingsService;
/**
 * Vonage Image overwriting core components
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, adapters = Image.class)
public class VonageImage implements Image {

  /**
   *  currentPage current page
   */
  @ScriptVariable
  private Page currentPage;

  /**
     * resourceResolver variable
     */
    @Inject
    private ResourceResolver resourceResolver;

  /**
   *  ImageObject from supertype
   */
  @Self @Via(type = ResourceSuperType.class)
  private Image superImage;

    /**
     * Sling Settings Service
     *
     * @return SlingSettingsService
     */
    @Inject
    private SlingSettingsService settings;

    /**
     * Boolean to check if SVG
     */
    private boolean isUseOptimizedImage = true;

    /**
     * Init Method of Model.
     */
    @PostConstruct
    protected final void init() {
        String runmodes = settings.getRunModes().toString();
        String mimeType = "";
        final Resource resource = resourceResolver.getResource(superImage.getFileReference());
        if (DamUtil.isAsset(resource)) {
          final Asset asset = DamUtil.resolveToAsset(resource);
          mimeType = asset.getMimeType();
        }
        if (StringUtils.equals(mimeType, "image/svg+xml")
        || StringUtils.containsIgnoreCase(runmodes, "author")
        || StringUtils.containsIgnoreCase(runmodes, "local")
        ) {
          isUseOptimizedImage = false;
        }
    }

    /**
     *  @return fileReference file reference
     */
    @Override
    public final String getFileReference() {
      return superImage.getFileReference();
    }

      /**
     * Returns the value for the {@code src} attribute of the image.
     *
     * @return the image's URL
     * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
     */
    public final String getSrc() {
      return superImage.getSrc();
  }

  /**
   * Returns the value for the {@code alt} attribute of the image.
   *
   * @return the value for the image's {@code alt} attribute, if one was set, or {@code null}
   * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
   */
  public final String getAlt() {
      return superImage.getAlt();
  }

  /**
   * Returns the value for the image's {@code title} attribute, if one was set.
   *
   * @return the value for the image's {@code title} attribute, if one was set, or {@code null}
   * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
   */
  public final String getTitle() {
      return superImage.getTitle();
  }

  /**
   * Returns the value for the image's uuid, if one was set.
   *
   * @return the value for the image's uuid, if one was set, or {@code null}
   * @since com.adobe.cq.wcm.core.components.models 12.4.0;
   */
  public final String getUuid() {
    return superImage.getUuid();
  }

  /**
   * Returns the image's link URL, if one was set.
   *
   * @return the image's link URL, if one was set, or {@code null}
   * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
   */
  public final String getLink() {
      return superImage.getLink();
  }

  /**
   * Checks if the image should display its caption as a popup (through the <code>&lt;img&gt;</code> {@code title}
   * attribute).
   *
   * @return {@code true} if the caption should be displayed as a popup, {@code false} otherwise
   * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
   */
  public final boolean displayPopupTitle() {
    return superImage.displayPopupTitle();
  }

  /**
   * Returns a JSON object used for the smart image functionality. The object provides the following properties:
   *
   * <ul>
   *     <li>{@link #JSON_SMART_SIZES} - array of integers, representing the available image widths</li>
   *     <li>{@link #JSON_SMART_IMAGES} - array of strings, providing the URLs for the available image renditions</li>
   *     <li>{@link #JSON_LAZY_ENABLED} - boolean, specifying if the image should be rendered lazily or not</li>
   * </ul>
   *
   * @return the JSON for the smart image functionality
   * @since com.adobe.cq.wcm.core.components.models 11.0.0; marked <code>default</code> in 12.1.0
   * @deprecated since 12.1.0
   */
  @Deprecated
  @JsonIgnore
  public final String getJson() {
      return superImage.getJson();
  }

  /**
   * Returns the alternative image widths (in pixels), configured through the
   * {@link #PN_DESIGN_ALLOWED_RENDITION_WIDTHS}
   * content policy. If no configuration is present, this method will return an empty array.
   *
   * @return the alternative image widths (in pixels)
   * @since com.adobe.cq.wcm.core.components.models 12.2.0
   */
  @NotNull
  public final int[] getWidths() {
      return superImage.getWidths();
  }

  /**
   * Returns a URI template representation of the image src attribute that can be variable expanded
   * to a URI reference. Useful for building an alternative image configuration from the original src.
   *
   * @return the image src URI template
   * @since com.adobe.cq.wcm.core.components.models 12.2.0
   */
  public final String getSrcUriTemplate() {
      return superImage.getSrcUriTemplate();
  }

      /**
     * Indicates if the image should be rendered lazily or not.
     *
     * @return true if the image should be rendered lazily; false otherwise
     * @since com.adobe.cq.wcm.core.components.models 12.2.0
     */
    public final boolean isLazyEnabled() {
      return superImage.isLazyEnabled();
  }

  /**
   * Returns a list of image map areas.
   *
   * @return the image map areas
   * @since com.adobe.cq.wcm.core.components.models 12.4.0
   */
  public final List<ImageArea> getAreas() {
      return superImage.getAreas();
  }


  /**
   * @see ComponentExporter#getExportedType()
   * @since com.adobe.cq.wcm.core.components.models 12.2.0
   */
  @NotNull
  @Override
  public final String getExportedType() {
    return superImage.getExportedType();
  }

  /**
   *  @return isSvg Check if image is svg
   */
  public final boolean useOptimizedImage() {
    return this.isUseOptimizedImage;
  }
}
