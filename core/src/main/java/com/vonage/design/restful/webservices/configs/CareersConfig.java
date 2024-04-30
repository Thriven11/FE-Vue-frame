package com.vonage.design.restful.webservices.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration for Careers data fetch.
 */
@ObjectClassDefinition(
  name = "Vonage - Careers data service",
  description = "Configuration for Careers data Service"
)
public @interface CareersConfig {
  /**
   * Attribute for enabling/ disabling the API
   *
   * @return apiEnabled - true or false
   */
  @AttributeDefinition(
    name = "Enabled",
    description = "UnCheck to stop API calls from being sent"
  )
  boolean apiEnabled() default true;

  /**
   * Attribute for Careers API url
   *
   * @return apiUrl - Careers api url
   */
  @AttributeDefinition(
    name = "Careers - API URL",
    description = "Careers API URL"
  )
  String apiUrl() default "https://boards-api.greenhouse.io/v1/boards/vonage/jobs/?content=true";

   /**
   * Attribute for Careers API url
   *
   * @return apiUrl - Careers api url
   */
  @AttributeDefinition(
    name = "Careers Departments - API URL",
    description = "Careers Departments API URL"
  )
  String deptApiUrl() default "https://boards-api.greenhouse.io/v1/boards/vonage/departments";

   /**
   * Attribute for Careers API url
   *
   * @return apiUrl - Careers api url
   */
  @AttributeDefinition(
    name = "Careers Locations - API URL",
  description = "Careers Locations API URL"
  )
  String locApiUrl() default "https://boards-api.greenhouse.io/v1/boards/vonage/offices";
}
