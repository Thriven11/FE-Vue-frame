package com.vonage.commerce;

import org.apache.sling.api.resource.Resource;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.common.AbstractJcrCommerceServiceFactory;

/**
 * Vonage implementation for the {@link CommerceServiceFactory} interface.
 */
@Component(immediate = true, service = CommerceServiceFactory.class,
        property = { Constants.SERVICE_DESCRIPTION + "=Factory for Vonage commerce service",
                "commerceProvider=" + CommerceConstants.VONAGE_COMMERCEPROVIDER })
public class VonageCommerceServiceFactory extends AbstractJcrCommerceServiceFactory implements CommerceServiceFactory {
    /**
     * Create a new <code>GeoCommerceServiceImpl</code>.
     *
     * @param res - Resource
     * @return CommerceService - service
     */
    public final CommerceService getCommerceService(final Resource res) {
        return new VonageCommerceServiceImpl(getServiceContext(), res);
    }
}
