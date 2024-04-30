package com.vonage.core.models.content;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sling Model for Header Media Component
 */
@Model(adaptables = { Resource.class, Page.class })
public class HeaderMediaComponentModel {

  /**
   * title
   */
  @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String title;

  /**
   * description
   */
  @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String description;

  /**
   * headline
   */
  @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String headline;

  /**
   *
   * mediaContainerType
   */
  @ValueMapValue(name = "mediaContainerType", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String mediaContainerType;

  /**
   *
   * dataVideoId
   */
  @ValueMapValue(name = "dataVideoId", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String dataVideoId;

  /**
   *
   * videoCaption
   */
  @ValueMapValue(name = "videoCaption", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String videoCaption;

  /**
   *
   * backgroundColor
   */
  @ValueMapValue(name = "backgroundColor", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String backgroundColor;

  /**
   *
   * videoAriaLabel
   */
  @ValueMapValue(name = "videoAriaLabel", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String videoAriaLabel;

    /**
     * byLine
     */
    @ValueMapValue(name = "byLine", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String byLine;

  /**
   * log variable
   */
  private static final Logger LOG = LoggerFactory.getLogger(HeaderMediaComponentModel.class);

  /**
   *
   * currentResource
   */
  @Self
  private Resource resource;

  /**
   *
   * pageList
   */
  private List<Hit> list;

  /**
   * init method
   */
  @PostConstruct
  protected final void init() {
    LOG.info("------BYLINE MODEL-------");

    PageManager pm = resource.getResourceResolver().adaptTo(PageManager.class);
    QueryBuilder queryBuilder = resource.getResourceResolver().adaptTo(QueryBuilder.class);
    Session session = resource.getResourceResolver().adaptTo(Session.class);

    Page containingPage = pm.getContainingPage(resource);
    ValueMap pageProperties = containingPage.getProperties();
    String authorTag = (String) pageProperties.get("author");

    if (authorTag != null) {

      Map predicateMap = new HashMap();
      predicateMap.put("path", "/content/experience-fragments/vonage/en-us/authors/");
      predicateMap.put("type", "cq:Page");
      // predicateMap.put("property", "property=jcr:content/@cq:tags, value=" + authorTag + ", operation=like");
      predicateMap.put("property", "jcr:content/@cq:tags");
      predicateMap.put("property.value", authorTag);
      predicateMap.put("property.operation", "operation=like");
      predicateMap.put("p.limit", "255");
      Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);

      SearchResult result = query.getResult();
      this.list = result.getHits();
      // Iterate query results
      for (Hit hit : list) {
        try {
          String path = hit.getPath();

          LOG.info(path);

          if (!path.endsWith("master")) {
            byLine = path + "/by-line/jcr:content/root";
            break;
          }
        } catch (RepositoryException e) {
          LOG.error(e.toString());
        }
      }
    }
  }

  /**
   * Getter title
   * @return title value
   */
  public final String getTitle() {
    return this.title;
  }

  /**
   * Getter description
   * @return description value
   */
  public final String getDescription() {
    return this.description;
  }

  /**
   * Getter headline
   * @return headline value
   */
  public final String getHeadline() {
    return this.headline;
  }

  /**
   * Getter mediaContainerType
   * @return mediaContainerType value
   */
  public final String getMediaContainerType() {
    return this.mediaContainerType;
  }

  /**
   * Getter dataVideoId
   * @return dataVideoId value
   */
  public final String getDataVideoId() {
    return this.dataVideoId;
  }

  /**
   * Getter videoCaption
   * @return videoCaption value
   */
  public final String getVideoCaption() {
    return this.videoCaption;
  }

  /**
   * Getter backgroundColor
   * @return backgroundColor value
   */
  public final String getBackgroundColor() {
    return this.backgroundColor;
  }

  /**
   * Getter videoAriaLabel
   * @return videoAriaLabel value
   */
  public final String getVideoAriaLabel() {
    return this.videoAriaLabel;
  }

  /**
   * Getter size
   * @return size value
   */
  public final List<Hit> getPageList() {
    return this.list;
  }

  /**
    * get byLine
    *
    * @return byLine
    */
  public final String getByLine() {
      return byLine;
  }

 }
