package com.vonage.core.models.content;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.ResourceResolver;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 * Sling model for Search Filters Root
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class SearchFiltersRootModel {

  /**
   * resource resolver
   */
  @Inject
  private ResourceResolver resourceResolver;


  /**
   * Page
   */
  @Inject
  private Page currentPage;

  /**
   * @return contentCategory
   */
  @ValueMapValue(name = "contentCategory", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String contentCategory;

  /**
   * tagsArray variable
   */
  @ValueMapValue(name = "cq:tags", injectionStrategy = InjectionStrategy.OPTIONAL)
  private final List<String> pageTagsList = new ArrayList<>();

  /**
   * filters variable
   */
  private String filters;

  /**
   * exclude filters in checkboxes for resources filters page
   */
  private String[] excludeFilters = {
    "vonage:topic/customer-360",
    "vonage:topic/infrastructure",
    "vonage:topic/customer-experience",
    "vonage:topic/call-center",
    "vonage:topic/business-productivity",
    "vonage:topic/marketing",
    "vonage:topic/support",
    "vonage:industry/accounting",
    "vonage:industry/finance",
    "vonage:industry/franchise",
    "vonage:industry/insurance",
    "vonage:industry/logistics",
    "vonage:industry/marketing",
    "vonage:industry/retail",
    "vonage:industry/ecommerce",
    "vonage:industry/hospitality",
    "vonage:industry/human-resources",
    "vonage:industry/recruiting",
    "vonage:industry/security",
    "vonage:industry/gaming-social",
    "vonage:industry/transportation"
  };

  /**
   * exclude product filters to not sort in alphabetical order
   */
  private String[] excludeProductFilters = {
    "vonage:product"
  };

  /**
   * contentType variable
   */
  private String contentType;

  /**
   * contentType
   */
  private void updateContentType() {
    /**
     * extract content category selected from tag
     */
    final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
    if (contentCategory != null) {
      Tag tag = tagManager.resolve(contentCategory);
      contentType = tag.getName();
    }
  }

  /**
   * filtersList
   */
  private void updateFiltersList() {
    final List<TagFilters> filterOptions = new ArrayList<>();
    /*
     * Null check for tags list
     */
    if (pageTagsList != null) {
      final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
      /*
      * Iterate to root tags and add all the tags in the list
      */
      for (int i = 0; i < pageTagsList.size(); i++) {
        String tagId = pageTagsList.get(i);
        Tag containerTags = tagManager.resolve(tagId);
        List<TagFilters> tagsOptions = new ArrayList<>();
        Iterator<Tag> tagsItr = containerTags.listChildren();
        /*
        * Tags Iterator
        */
        while (tagsItr.hasNext()) {
          Tag tags = tagsItr.next();
          List<TagFilters> subTagsOptions = new ArrayList<>();
          Iterator<Tag> subTagsItr = tags.listChildren();
          /*
          * Subtags Iterator
          */
          while (subTagsItr.hasNext()) {
            Tag subTags = subTagsItr.next();
            /*
            * Check for exclude tags for resources content type
            */
            boolean excludeTag = (StringUtils.contains(contentType, "resources")
              && Arrays.asList(excludeFilters).contains(subTags.getTagID()));
            if (!excludeTag) {
              TagFilters subTagsOptionsMap = new TagFilters(this.getTagTitle(subTags),
                subTags.getLocalTagID(), null);
              subTagsOptions.add(subTagsOptionsMap);
            }
          }
          TagFilters tagOptionsMap;
          /*
          * Check for exclude tags for resources content type
          */
          boolean excludeTag = (StringUtils.contains(contentType, "resources")
            && Arrays.asList(excludeFilters).contains(tags.getTagID()));
          if (!excludeTag) {
            if (subTagsOptions.size() >= 1) {
              Collections.sort(subTagsOptions, Comparator.comparing(TagFilters::getLabel));
              tagOptionsMap = new TagFilters(this.getTagTitle(tags),
                tags.getLocalTagID(), subTagsOptions);
            } else {
              tagOptionsMap = new TagFilters(this.getTagTitle(tags),
                tags.getLocalTagID(), null);
            }
            tagsOptions.add(tagOptionsMap);
          }
        }
        boolean excludeProductTag = (StringUtils.contains(contentType, "resources")
            && Arrays.asList(excludeProductFilters).contains(containerTags.getTagID()));
        if (!excludeProductTag
          && StringUtils.equals(currentPage.getLanguage().getLanguage(), "en")) {
          Collections.sort(tagsOptions, Comparator.comparing(TagFilters::getLabel));
        }
        TagFilters filterObjects = new TagFilters(this.getTagTitle(containerTags),
          containerTags.getLocalTagID(), tagsOptions);
        filterOptions.add(filterObjects);
      }
      filters = new Gson().toJson(filterOptions);
    }
  }

  /**
   * init method
   */
  @PostConstruct
  protected final void init() {
    updateContentType();
    updateFiltersList();
  }

  /**
   * Getter pageTagsList
   * @return pageTagsList
   */
  public final List<String> getPageTagList() {
    return new ArrayList<>(pageTagsList);
  }

    /**
   * Getter getTagTitle used to get localized tags for other languages and default tag for english
   * @param tag tag
   * @return tagtitle
   */
  public final String getTagTitle(final Tag tag) {
    String tagTitle = "";

    Map<String, Locale> hashMapForLocale = new HashMap<String, Locale>();
    hashMapForLocale.put("fr", new Locale("fr", "fr"));
    hashMapForLocale.put("ja", new Locale("ja"));
    hashMapForLocale.put("de", new Locale("de", "de"));
    hashMapForLocale.put("ru", new Locale("ru", "ru"));
    hashMapForLocale.put("es", new Locale("es", "es"));
    hashMapForLocale.put("ko", new Locale("ko", "kr"));
    hashMapForLocale.put("zh", new Locale("zh", "cn"));

    if (StringUtils.equals(currentPage.getLanguage().getLanguage(), "en")) {
      tagTitle = tag.getTitle();
    } else {
      tagTitle = tag.getLocalizedTitles().get(hashMapForLocale.get(currentPage.getLanguage().getLanguage()));
    }
    return tagTitle;
  }

  /**
   * Get contentType
   * @return contentType
   */
  public final String getContentType() {
    return contentType;
  }

  /**
   * Get filters
   * @return filters
   */
  public final String getFilters() {
    return filters;
  }

  /**
   * TagFilters class
   */
  public class TagFilters {

    /**
     * label variable
     */
    private final String label;

    /**
     * name variable
     */
    private final String name;

    /**
     * options variable
     */
    private final List<TagFilters> options;

    /**
     * Getter label
     *
     * @return label
     */
    public final String getLabel() {
      return label;
    }

    /**
     * Getter name
     *
     * @return name
     */
    public final String getName() {
      return name;
    }

    /**
     * Getter options
     *
     * @return options
     */
    public final List<TagFilters> getOptions() {
      return options;
    }

    /**
     * TagFilters constructor
     *
     * @param label - this is label
     * @param name - this is name
     * @param options - this is options
     *
     */
    public TagFilters(final String label, final String name, final List<TagFilters> options) {
      this.label = label;
      this.name = name;
      this.options = options;
    }

  }

}
