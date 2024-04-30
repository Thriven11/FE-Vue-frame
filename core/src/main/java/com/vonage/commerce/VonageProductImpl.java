package com.vonage.commerce;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.cq.commerce.common.AbstractJcrProduct;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * VonageProductImpl
 *
 * @author Vonage
 *
 */
public class VonageProductImpl extends AbstractJcrProduct {

    /**
     * productPage
     */
    private final Page productPage;

    /**
     * Constructor
     *
     * @param resource - resource
     */
    public VonageProductImpl(final Resource resource) {
        super(resource);
        ResourceResolver resourceResolver = resource.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager != null) {
            productPage = pageManager.getContainingPage(resource);
        } else {
            productPage = null;
        }
    }

    /**
     * Get SKU
     *
     * @return SKU
     */
    public final String getSKU() {
        return getProperty(CommerceConstants.PN_IDENTIFIER, String.class);
    }

    /**
     * Get Brand
     *
     * @return brand
     */
    public final String getBrand() {
        String brand = StringUtils.EMPTY;
        if (productPage != null) {
            brand = productPage.getAbsoluteParent(2).getTitle();
        }
        return brand;
    }
}
