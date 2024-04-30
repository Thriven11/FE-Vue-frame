package com.vonage.core.models.content;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;




 /**
  * Sling Model for Authrable ID
  */
 @Model(adaptables = { SlingHttpServletRequest.class, Resource.class },
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
 public class AuthorableDropDown {

  /**
     * log variable
     */
  private static final Logger LOG = LoggerFactory.getLogger(AuthorableDropDown.class);
  /**
    * list
    */
  private List<AuthorableDropDownMultifield> options;

   /**
    * currencyToggle
    */
   @ValueMapValue(name = "id", injectionStrategy = InjectionStrategy.OPTIONAL)
   private String id;
 /**
    * currencyToggle
    */
   @ValueMapValue(name = "name", injectionStrategy = InjectionStrategy.OPTIONAL)
   private String name;
   /**
    * currencyToggle
    */
    @ValueMapValue(name = "authorablelabel", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String authorablelabel;

  /**
    * childResource
    */

  @ChildResource(name = "dropdownOptions")
  private Resource dropdownOptions;

/**@return
    * getter
    */
  public final Resource getDropdownOptions() {
    return dropdownOptions;
  }
/**@return
    * getter
    */
    public final List<AuthorableDropDownMultifield> getMultifields() {
        return this.options;
    }

  /**
    * init method
    */
   @PostConstruct
   protected final void init() {
    options = new ArrayList<>();
    if (dropdownOptions != null) {
      Iterator<Resource> iterator = dropdownOptions.listChildren();
      while (iterator.hasNext()) {
          Resource child = iterator.next();
          AuthorableDropDownMultifield authorableDropDownMultifield = child.adaptTo(AuthorableDropDownMultifield.class);
          options.add(authorableDropDownMultifield);
      }
  }
    LOG.debug("pricing matrix model ");
   }
   /**@return
    * id
    */
   public final String  getId() {
    return id;
  }
  /**@return
    * name
    */
  public final String getName() {
    return name;
  }
  /**@return
    * label
    */
  public final String getAuthorablelabel() {
    return authorablelabel;
  }


 }
