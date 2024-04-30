package com.vonage.design.restful.webservices;

import com.google.gson.JsonObject;
import com.vonage.design.restful.exceptions.RestClientException;

/**
 * Base class to Careers data fetch
 *
 * @author Vonage
 *
 */
public interface Careers {
  /**
   * @param type type
   * @return JsonObject response
   * @throws RestClientException Exception
   */
  JsonObject getCareersJson(String type) throws RestClientException;
}
