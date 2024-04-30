package com.vonage.design.restful.webservices.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vonage.design.restful.clients.BasicClient;
import com.vonage.design.restful.exceptions.RestClientException;
import com.vonage.design.restful.models.Response;
import com.vonage.design.restful.webservices.Careers;
import com.vonage.design.restful.webservices.configs.CareersConfig;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation class for Careers Data Connector
 *
 * @author Vonage
 *
 */
@Component(service = Careers.class)
@Designate(ocd = CareersConfig.class)
public class CareersImpl extends BasicClient implements Careers {

   /**
   * logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(CareersImpl.class);

  /**
   * API URL
   */
  private String apiUrl;

  /**
   * Dept API URL
   */
  private String deptApiUrl;

  /**
   * Locations API URL
   */
  private String locApiUrl;

  /**
   * are API calls enabled?
   */
  private boolean isEnabled;

  /**
   * Activate method
   *
   * @param config configs
   */
  @Activate
  @Modified
  protected final void activate(final CareersConfig config) {
    apiUrl = config.apiUrl().trim();
    deptApiUrl = config.deptApiUrl().trim();
    locApiUrl = config.locApiUrl().trim();
    isEnabled = config.apiEnabled();
  }

  @Override
  public final JsonObject getCareersJson(final String type)
    throws RestClientException {
    JsonObject json = null;
    String requestUrl = apiUrl;
    if ("loc".equalsIgnoreCase(type)) {
      requestUrl = locApiUrl;
    } else if ("dept".equalsIgnoreCase(type)) {
      requestUrl = deptApiUrl;
    } else if ("jobs".equalsIgnoreCase(type)) {
      requestUrl = apiUrl;
    }
    LOGGER.info("url " + requestUrl);
    if (isEnabled && StringUtils.isNotEmpty(requestUrl)) {
      Response response = super.get(requestUrl);
      if (response != null) {
        json = new Gson().fromJson(response.getBody().trim(), JsonObject.class);
    }
    }
    return json;
  }
}
