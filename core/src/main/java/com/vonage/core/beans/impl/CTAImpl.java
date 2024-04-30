package com.vonage.core.beans.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vonage.core.beans.CTA;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Helper class to help identify CTA.
 *
 * @author Vonage
 *
 */
public final class CTAImpl implements CTA {

  /** href placeholder */
  private static final String LINK_PLACEHOLDER = "#";
  /** mailto link */
  private static final Pattern MAILTO_PATTERN = Pattern.compile("mailto:([^\\?]*)");
  /** mailto link */
  private static final Pattern JAVASCRIPT_PATTERN = Pattern.compile("javascript:([^\\?]*)");
  /** internal with no extension */
  private static final Pattern INTERNAL_NO_EXTENSION_PATTERN = Pattern.compile("^/.*[^.]{5}$");
  /** internal with extension pattern */
  private static final Pattern INTERNAL_URL_PATTERN = Pattern.compile("^/.*?\\.html(\\?.*)?$");
  /** internal with pdf extension pattern */
  private static final Pattern INTERNAL_PDF_PATTERN = Pattern.compile("^/.*?\\.pdf(\\?.*)?$");
  /** internal with pdf extension pattern */
  private static final Pattern INTERNAL_ZIP_PATTERN = Pattern.compile("^/.*?\\.zip(\\?.*)?$");
  /** external url link pattern */
  private static final Pattern EXTERNAL_URL_PATTERN = Pattern
      .compile("^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)"
          + "(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)" + "(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])"
          + "(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])"
          + "(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\." + "(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|"
          + "(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)"
          + "(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\."
          + "(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?$");
  /** Hashtag pattern */
  private static final Pattern HASHTAG_PATTERN = Pattern.compile("(?:\\s|^)#[A-Za-z0-9\\-\\.\\_]+(?:\\s|$)");

  /** href */
  private final String href;
  /** ctaBackgroundColor */
  private final String ctaBackgroundColor;
  /** checkDefault */
  private Boolean checkDefault;
  /** path */
  private final String path;
  /** label */
  private final String label;
  /** tagLabel */
  private final String tagLabel;

  /** target */
  private final String target;
  /** type */
  private final String type;
  /** ariaLabel */
  private final String ariaLabel;
  /** staticLabel */
  private final String staticLabel;
  /** dataVideoId */
  private final String dataVideoId;

  /**
   * @param href               link property value
   * @param label              link label
   * @param target             link target
   * @param type               link type
   * @param ariaLabel          Aria Label
   * @param staticLabel        static Label
   * @param dataVideoId        video id
   * @param ctaBackgroundColor color
   * @param checkDefault       value
   *
   *
   */
  public CTAImpl(
      @JsonProperty("href") final String href, @JsonProperty("label") final String label,
      @JsonProperty("target") final String target, @JsonProperty("type") final String type,
      @JsonProperty("ariaLabel") final String ariaLabel, @JsonProperty("staticLabel") final String staticLabel,
      @JsonProperty("dataVideoId") final String dataVideoId,
      @JsonProperty("ctaBackgroundColor") final String ctaBackgroundColor, final Boolean checkDefault) {
    String trimTagLabel = "";
    String labelLowerCase = "";
    String trimStaticLabel = "";
    String staticLabelLowerCase = "";
    this.label = label;
    this.ctaBackgroundColor = ctaBackgroundColor;
    this.checkDefault = checkDefault;

    this.target = target;
    this.type = type;
    this.path = href;
    this.ariaLabel = ariaLabel;
    this.dataVideoId = dataVideoId;

    if (StringUtils.isBlank(href)) {
      this.href = LINK_PLACEHOLDER;
    } else if (INTERNAL_URL_PATTERN.matcher(href).matches() || EXTERNAL_URL_PATTERN.matcher(href).matches()
        || HASHTAG_PATTERN.matcher(href).matches() || MAILTO_PATTERN.matcher(href).matches()
        || JAVASCRIPT_PATTERN.matcher(href).matches() || INTERNAL_ZIP_PATTERN.matcher(href).matches()
        || INTERNAL_PDF_PATTERN.matcher(href).matches()) {
      this.href = href;
    } else if (INTERNAL_NO_EXTENSION_PATTERN.matcher(href).matches()) {
      this.href = String.format("%s.html", href);
    } else {
      this.href = LINK_PLACEHOLDER;
    }

    if (!StringUtils.isBlank(label)) {
      labelLowerCase = StringUtils.lowerCase(label);
      trimTagLabel = StringUtils.deleteWhitespace(labelLowerCase);
    }

    this.tagLabel = trimTagLabel;

    if (!StringUtils.isBlank(staticLabel)) {
      staticLabelLowerCase = StringUtils.lowerCase(staticLabel);
      trimStaticLabel = StringUtils.deleteWhitespace(staticLabelLowerCase);
    }

    this.staticLabel = trimStaticLabel;

    if (this.ctaBackgroundColor.equals("default")) {
      this.checkDefault = true;
    }
  }

  @Override
  public String getHref() {
    return this.href;
  }

  @Override
  public String getLabel() {
    return this.label;
  }

  @Override
  public String getStaticLabel() {
    return this.staticLabel;
  }

  @Override
  public String getTarget() {
    return this.target;
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public boolean isEmpty() {
    return StringUtils.isEmpty(this.label) && StringUtils.isBlank(this.target)
        && StringUtils.equals(this.href, LINK_PLACEHOLDER) && StringUtils.isEmpty(this.staticLabel);
  }

  @Override
  public String getTagLabel() {
    return tagLabel;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public String getAriaLabel() {
    return ariaLabel;
  }

  @Override
  public String getDataVideoId() {
    return dataVideoId;
  }

  /**
   *
   * @return ctaBackgroundColor
   */

  public String getCtaBackgroundColor() {
    return ctaBackgroundColor;
  }

  /**
   *
   * @return checkDefault
   */

  public Boolean getCheckDefault() {
    return checkDefault;
  }
}
