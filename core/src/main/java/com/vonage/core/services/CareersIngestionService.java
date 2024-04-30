package com.vonage.core.services;

import com.google.gson.JsonObject;

/**
 * Careers Ingestion Service
 *
 * @author Vonage
 */
public interface CareersIngestionService {
  /**
   *
   * @param careers - careers JsonObject
   * @return true if successful
   */
  boolean ingestCareers(JsonObject careers);

  /**
   *
   * @param depts - careers JsonObject
   * @return true if successful
   */
  boolean ingestDepartments(JsonObject depts);

  /**
   *
   * @param locations - careers JsonObject
   * @return true if successful
   */
  boolean ingestLocations(JsonObject locations);
}
