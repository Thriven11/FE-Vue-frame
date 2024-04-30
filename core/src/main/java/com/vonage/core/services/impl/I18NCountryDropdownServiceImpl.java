package com.vonage.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.vonage.core.services.I18NCountryDropdownConfiguration;
import com.vonage.core.services.I18NCountryDropdownService;
import com.vonage.core.utils.ServiceUtils;

/**
 * Service for datasource
 * <p>
 * This service is called to get datasource
 * </p>
 *
 * @author Vonage
 */
@Component(immediate = true, service = I18NCountryDropdownService.class)
@Designate(ocd = I18NCountryDropdownConfiguration.class)
public class I18NCountryDropdownServiceImpl implements I18NCountryDropdownService {

    /**
     * I18NCountryDropdownConfiguration object
     */
    private I18NCountryDropdownConfiguration countryDropdownConfig;

    /**
     * Activate I18NCountryDropdownConfiguration.
     *
     * @param countryDropdownConfigParam application default configs value
     */
    @Activate
    public final void activate(final I18NCountryDropdownConfiguration countryDropdownConfigParam) {
        this.countryDropdownConfig = countryDropdownConfigParam;
    }

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18NCountryDropdownServiceImpl.class);

    /**
     * Resource Resolver Factory
     */
    @Reference
    private ResourceResolverFactory resolverFactory;

    /**
     * Get a datasources
     *
     * @param name - The campaign name
     * @return dataSource - contains all I18N country codes (or null if not found)
     */

    private DataSource dataSource;

    @Override
    public final DataSource getI18NLanguages() {
        try {
            final ResourceResolver resourceResolver = ServiceUtils.getReadResourceResolver(resolverFactory);
            Resource parentResource = resourceResolver.getResource(countryDropdownConfig.I18NPricingPath());
            Iterator<Resource> resourceItr = parentResource.listChildren();
            List<Resource> list = new ArrayList<Resource>();
            while (resourceItr.hasNext()) {
                Resource childResource = resourceItr.next();
                ValueMap valMap = childResource.adaptTo(ValueMap.class);
                ValueMap val = new ValueMapDecorator(new HashMap<String, Object>());
                val.put("text", valMap.get("jcr:language").toString());
                val.put("value", valMap.get("jcr:language").toString());
                list.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED,
                        val));

            }
            LOGGER.error("list ------" + list.size());
            dataSource = new SimpleDataSource(list.iterator());

        } catch (Exception e) {
            LOGGER.error("Exception {}" + e);
        }
        return dataSource;
    }

}
