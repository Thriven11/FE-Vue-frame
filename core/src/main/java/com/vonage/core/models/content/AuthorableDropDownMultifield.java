package com.vonage.core.models.content;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;



 /**
  * Sling Model for Authrable multifiled section
  */
 @Model(adaptables = { SlingHttpServletRequest.class, Resource.class },
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
 public class AuthorableDropDownMultifield {
   /**
     * log variable
     */
  private static final Logger LOG = LoggerFactory.getLogger(AuthorableDropDownMultifield.class);


   /**
    * authorablelabel
    */
   @ValueMapValue(name = "label", injectionStrategy = InjectionStrategy.OPTIONAL)
   private String label;

   /**@return
    * label
    */
    public final String label() {
    return label;
  }

    /**
    * value
    */
    @ValueMapValue(name = "value", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String value;
     /**@return
    * value
    */

    public final String getValue() {
      return value;
    }


  /**
    * init method
    */
   @PostConstruct
   protected final void init() {
    LOG.debug("pricing matrix model ");
   }



 }
