package com.vonage.core.services.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enable OOTB osgi components
 */
@Component(immediate = true, service = EnableOsgiComponentsImpl.class)
public class EnableOsgiComponentsImpl {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(EnableOsgiComponentsImpl.class);

  /**
   * Component Runtime Src
   */
  @Reference
  private ServiceComponentRuntime scr;

  /**
   * Bundle Context
   */
  private BundleContext bundleContext;

  /**
   * Enable Compoennt
   * @param componentName component PID name
   */
  private void enableComponent(final String componentName) {
    for (Bundle bundle : bundleContext.getBundles()) {
      ComponentDescriptionDTO dto = scr.getComponentDescriptionDTO(bundle, componentName);
      if (dto != null && !scr.isComponentEnabled(dto)) {
        LOGGER.info("Component {} disabled by configuration.", dto.implementationClass);
        scr.enableComponent(dto);
      }
    }
  }

  /**
   * Activate
   * @param bundleContextParam bundle Context
   */
  @Activate
  public final void activate(final BundleContext bundleContextParam) {
    LOGGER.info("Component activated");
    this.bundleContext = bundleContextParam;
    this.enableComponent("com.day.cq.dam.core.impl.MissingMetadataNotificationJob");
  }

}
