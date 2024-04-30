package com.vonage.core.models.content;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.vonage.core.services.CareersService;
/**
 * Sling Model for Job Search Component
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class })
public class JobSearchComponentModel {

  /** departments list var */
  private Map<String, String> sortedDeptList = new HashMap<>();

  /** departments list var */
  private Map<String, String> sortedOfficesList = new HashMap<>();

   /**
   * resource resolver
   */
  @OSGiService
  private CareersService careersService;

  /**
   * init method - fetches lists of departments and offices
   */
  @PostConstruct
  protected final void init() {
    sortedDeptList = careersService.getDepartments();
    sortedOfficesList = careersService.getOffices();
  }

  /**
   * Get offices
   *
   * @return offices
   */
  public final Map<String, String> getOffices() {
    return sortedOfficesList;
  }

  /**
   * Get departments list
   *
   * @return departments
   */
  public final Map<String, String> getDepartments() {
    return sortedDeptList;
  }
}
