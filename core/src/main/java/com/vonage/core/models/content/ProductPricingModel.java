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
public interface ProductPricingModel {

    /**
     *
     * @return include Currency Toggle
     */
    @ValueMapValue(name = "includeCurrencyToggle", injectionStrategy = InjectionStrategy.OPTIONAL)
    boolean getIncludeCurrencyToggle();

    /**
     *
     * @return include CTA
     */
    @ValueMapValue(name = "includeCta", injectionStrategy = InjectionStrategy.OPTIONAL)
    boolean getIncludeCta();

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
     * @return paddingTop
     */
    @ValueMapValue(name = "paddingTop", injectionStrategy = InjectionStrategy.OPTIONAL)
    String getPaddingTop();

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
    *
    * @return validityText
    */
   @ValueMapValue(name = "validityText", injectionStrategy = InjectionStrategy.OPTIONAL)
   String getValidityText();

    /**
     * String Company BulletPoints
     * the name `getSubList` corresponds to the multifield name="./sublist"
     * @return Department object
     */
    @Inject
    List<OfferingListItem> getOfferingList();

    /**
     * Sling Model for Offering List Item
     */
    @Model(adaptables = Resource.class,
           defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    class OfferingListItem {

      /**
       * price
       */
      @ValueMapValue(name = "price", injectionStrategy = InjectionStrategy.OPTIONAL)
      private String price;

      /**
       * Getter price
       * @return price value
       */
      public final String getPrice() {
        return this.price;
      }

      /**
       * description
       */
      @ValueMapValue(name = "description", injectionStrategy = InjectionStrategy.OPTIONAL)
      private String description;

      /**
       * Getter description
       * @return description value
       */
      public final String getDescription() {
        return this.description;
      }

      /**
       * legal
       */
      @ValueMapValue(name = "legal", injectionStrategy = InjectionStrategy.OPTIONAL)
      private String legal;

      /**
       * Getter legal
       * @return legal value
       */
      public final String getLegal() {
        return this.legal;
      }
    }
  }


}
