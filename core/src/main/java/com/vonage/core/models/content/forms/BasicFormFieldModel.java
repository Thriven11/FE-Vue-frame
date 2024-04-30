package com.vonage.core.models.content.forms;

import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.commons.lang3.StringUtils;
import java.util.UUID;


/**
 * Sling Model for Basic Form Fields
 */
@Model(adaptables = Resource.class)
public class BasicFormFieldModel {

  /**
   * uuid String
   */
  private String uuid = StringUtils.EMPTY;

  /**
   * init method
   */
  @PostConstruct
  protected final void init() {
    uuid = UUID.randomUUID().toString();
  }

  /**
   *
   * @return uuid
   */
  public final String getUuid() {
    return uuid;
  }

}
