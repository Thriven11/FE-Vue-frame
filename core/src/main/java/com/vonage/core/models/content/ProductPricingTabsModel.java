package com.vonage.core.models.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

/**
 * Sling Model for Pricing Card
 */
@Model(adaptables = Resource.class)
public interface ProductPricingTabsModel {

    /**
     *
     * @return tabList
     */
    @ChildResource(name = "tabList",
      injectionStrategy = InjectionStrategy.OPTIONAL)
    List<ProductPricingModel> getTabList();

    /**
     *
     * @return tab Title
     */
    @ValueMapValue(name = "tabTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getTabTitle();

    /**
     *
     * @return section Title
     */
    @ValueMapValue(name = "sectionTitle", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getSectionTitle();

    /**
     *
     * @return headline
     */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();

    /**
     *
     * @return sectionList
     */
    @ChildResource(name = "sectionList",
            injectionStrategy = InjectionStrategy.OPTIONAL)
    List<OfferingList> getSectionList();

  /**
   * OfferingList model
   * has a name and a list of departments
   */
  @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
  interface OfferingList {

    /**
     *
     * @return headline
     */
    @ValueMapValue(name = "headline", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getHeadline();



    /**
     * String Company BulletPoints
     * the name `getSubList` corresponds to the multifield name="./sublist"
     * @return Department object
     */
    @Inject
    List<OfferingListItem> getOfferingList();

    /**
     * Sling Model for Pricing Card
     */
    @Model(adaptables = Resource.class)
    public interface OfferingListItem {

      /**
       *
       * @return price
       * "$0.00475" by default
       */
      @ValueMapValue(name = "price", injectionStrategy = InjectionStrategy.OPTIONAL)
      String getPrice();

      /**
       *
       * @return description
       * "2,001 - 100,000 minutes" by default
       */
      @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
      String getDescription();

      /**
       *
       * @return optional legal text
       */
      @ValueMapValue(name = "legal", injectionStrategy = InjectionStrategy.OPTIONAL)
      String getLegal();
    }
  }



}
