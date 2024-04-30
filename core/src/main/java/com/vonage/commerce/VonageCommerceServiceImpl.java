package com.vonage.commerce;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

import com.adobe.cq.commerce.api.CommerceConstants;
import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceSession;
import com.adobe.cq.commerce.api.PaymentMethod;
import com.adobe.cq.commerce.api.Product;
import com.adobe.cq.commerce.api.ShippingMethod;
import com.adobe.cq.commerce.api.promotion.Voucher;
import com.adobe.cq.commerce.common.AbstractJcrCommerceService;
import com.adobe.cq.commerce.common.AbstractJcrProduct;
import com.adobe.cq.commerce.common.ServiceContext;
import com.day.cq.wcm.api.Page;

/**
 * Impl class for Commerce Service
 *
 * @author Vonage
 *
 */
public class VonageCommerceServiceImpl extends AbstractJcrCommerceService implements CommerceService {

    /**
     * Constant
     */
    private static final String REQUEST_ATTRIBUTE_NAME = VonageCommerceServiceImpl.class.getName();

    /**
     * resource - resource
     */
    private Resource resource;

    /**
     * Constructor
     *
     * @param serviceContext - context
     * @param res            - resource
     */
    public VonageCommerceServiceImpl(final ServiceContext serviceContext, final Resource res) {
        super(serviceContext, res);
        this.resource = res;
    }

    @Override
    public final CommerceSession login(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws CommerceException {
        Object session = request.getAttribute(REQUEST_ATTRIBUTE_NAME);
        if (session != null) {
            return (CommerceSession) session;
        }

        CommerceSession commerceSession = new VonageCommerceSessionImpl(this, request, response, resource);
        request.setAttribute(REQUEST_ATTRIBUTE_NAME, commerceSession);
        return commerceSession;
    }

    @Override
    public final boolean isAvailable(final String serviceType) {
        return CommerceConstants.SERVICE_COMMERCE.equals(serviceType);
    }

    @Override
    public final Product getProduct(final String path) throws CommerceException {
        Resource productResource = resolver.getResource(path);
        if (productResource != null && AbstractJcrProduct.isAProductOrVariant(productResource)) {
            return new VonageProductImpl(productResource);
        }
        return null;
    }

    @Override
    public final Voucher getVoucher(final String path) throws CommerceException {
        return null;
    }

    @Override
    public final void catalogRolloutHook(final Page blueprint, final Page catalog) {
        // NOP
    }

    @Override
    public final void sectionRolloutHook(final Page blueprint, final Page section) {
        // NOP
    }

    @Override
    public final void productRolloutHook(final Product productData, final Page productPage, final Product product)
            throws CommerceException {
        // NOP
    }

    @Override
    public final List<ShippingMethod> getAvailableShippingMethods() throws CommerceException {
        return enumerateMethods("/var/commerce/shipping-methods/vonage", ShippingMethod.class);
    }

    @Override
    public final List<PaymentMethod> getAvailablePaymentMethods() throws CommerceException {
        return enumerateMethods("/var/commerce/payment-methods/vonage", PaymentMethod.class);
    }

    @Override
    public final List<String> getCountries() throws CommerceException {
        List<String> countries = new ArrayList<>();
        countries.add("*");
        return countries;
    }

    @Override
    public final List<String> getCreditCardTypes() throws CommerceException {
        List<String> ccTypes = new ArrayList<>();
        ccTypes.add("*");
        return ccTypes;
    }

    @Override
    public final List<String> getOrderPredicates() throws CommerceException {
        List<String> predicates = new ArrayList<>();
        predicates.add(CommerceConstants.OPEN_ORDERS_PREDICATE);
        return predicates;
    }
}
