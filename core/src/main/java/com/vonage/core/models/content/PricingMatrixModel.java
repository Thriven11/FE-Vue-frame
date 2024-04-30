package com.vonage.core.models.content;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.day.cq.wcm.api.Page;;


 /**
  * Sling Model for Pricing Matrix Component
  */
 @Model(adaptables = { SlingHttpServletRequest.class, Resource.class },
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
 public class PricingMatrixModel {


   /**
    * currencyToggle
    */
   @ValueMapValue(name = "currencyToggle", injectionStrategy = InjectionStrategy.OPTIONAL)
   private boolean currencyToggle;

   /**
     *
     *  title
     */
    @ValueMapValue(name = "title", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String title;
   /**
    * numberOfItems
    */
   @ValueMapValue(name = "numberOfItems", injectionStrategy = InjectionStrategy.OPTIONAL)
   private String numberOfItems;

   /**
    * log variable
    */
   private static final Logger LOG = LoggerFactory.getLogger(PricingMatrixModel.class);

    /**
     * Page
     */
    @Inject
    private Page currentPage;

   /**
    *
    * Check if this component is part of the www.vonage.com site AND part of unified-communications section
    * to determine if we need to account for the checkout flow behavior
    */
   private String isCheckoutFlow = "false";


   /**
    * init method
    */
   @PostConstruct
   protected final void init() {

    LOG.debug("pricing matrix model " + currentPage.getPath());

    if ((StringUtils.contains(currentPage.getPath(), "/content/vonage/live-copies/us/en")
       && StringUtils.contains(currentPage.getPath(), "/unified-communications/"))
       || StringUtils.contains(currentPage.getPath().toLowerCase(), "/ezlynx")
       || StringUtils.contains(currentPage.getPath().toLowerCase(), "/app-smart")
       || StringUtils.contains(currentPage.getPath().toLowerCase(), "/ucx")) {
        isCheckoutFlow = "true";
    }
   }

   /**
    *
    * @return currencyToggle
    */
   public final boolean getCurrencyToggle() {
     return this.currencyToggle;
   };

   /**
    *
    * @return isCheckoutFlow
    */
   public final String isCheckoutFlow() {
     return this.isCheckoutFlow;
   };

   /**
    * @return numberOfItems
    */
   public final String getNumberOfItems() {
     return this.numberOfItems;
   };

   /**
    * @return title
    */
   public final String getTitle() {
     return this.title;
   };


 }
