package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.Self;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sling Model for Bio
 */
@Model(adaptables = { Resource.class, Page.class })
public class BioComponentModel {

  /**
   * name
   */
  @ValueMapValue(name = "name", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String name;

  /**
   * role
   */
  @ValueMapValue(name = "role", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String role;

  /**
   * bioText
   */
  @ValueMapValue(name = "bioText", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String bioText;

  /**
   * namePrefix
   */
  @ValueMapValue(name = "namePrefix", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String namePrefix;

  /**
   * moreByText
   */
  @ValueMapValue(name = "moreByText", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String moreByText;

  /**
   * bio
   */
  @ValueMapValue(name = "bio", injectionStrategy = InjectionStrategy.OPTIONAL)
  private String bio;

  /**
   * log variable
   */
  private static final Logger LOG = LoggerFactory.getLogger(BioComponentModel.class);

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
    LOG.info("------BIO MODEL-------");

    PageManager pm = resource.getResourceResolver().adaptTo(PageManager.class);
    QueryBuilder queryBuilder = resource.getResourceResolver().adaptTo(QueryBuilder.class);
    Session session = resource.getResourceResolver().adaptTo(Session.class);

    Page containingPage = pm.getContainingPage(resource);
    ValueMap pageProperties = containingPage.getProperties();
    String authorTag = (String) pageProperties.get("author");

    LOG.info("authorTag: " + authorTag);
    if (authorTag != null) {
      LOG.info("authorTag: " + authorTag);

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
          if (!path.endsWith("master")) {
            bio = path + "/bio";
            break;
          }
        } catch (RepositoryException e) {
         LOG.error(e.toString());
        }
      }
    }
  }

    /**
     *
     * @return name
     */
     public final String getName() {
         return this.name;
     }

    /**
     *
     * @return role
     */
     public final String getRole() {
         return this.role;
     }

    /**
     *
     * @return bioText
     */
     public final String getBioText() {
         return this.bioText;
     }

    /**
     *
     * @return namePrefix
     */
     public final String getNamePrefix() {
         return this.namePrefix;
     }

    /**
     *
     * @return moreByText
     */
     public final String getNamgetMoreByTextePrefix() {
         return this.moreByText;
     }

    // /**
    //  *
    //  * @return cta
    //  */
    // @CTAProperty
    // @Optional
    // CTA getLink();


  /**
   * Getter size
   * @return size value
   */
  public final List<Hit> getPageList() {
    return this.list;
  }

  /**
  * get bio
  *
  * @return bio
  */
  public final String getBio() {
      return bio;
  }

}
