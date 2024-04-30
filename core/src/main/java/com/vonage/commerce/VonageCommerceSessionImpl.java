package com.vonage.commerce;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.PlacedOrder;
import com.adobe.cq.commerce.api.Product;
import com.adobe.cq.commerce.common.AbstractJcrCommerceService;
import com.adobe.cq.commerce.common.AbstractJcrCommerceSession;

/**
 * Session Impl
 *
 * @author Vonage
 *
 */
public class VonageCommerceSessionImpl extends AbstractJcrCommerceSession {

    /**
     * Constructor
     *
     * @param commerceService - service
     * @param request         - request
     * @param response        -response
     * @param resource        - resource
     * @throws CommerceException - exception
     */
    public VonageCommerceSessionImpl(final AbstractJcrCommerceService commerceService,
            final SlingHttpServletRequest request, final SlingHttpServletResponse response, final Resource resource)
            throws CommerceException {
        super(commerceService, request, response, resource);
        PN_UNIT_PRICE = CommerceConstants.PN_PRICE;
    }

    @Override
    protected final BigDecimal getShipping(final String method) {
        return BigDecimal.ZERO;
    }

    @Override
    protected final String tokenizePaymentInfo(final Map<String, String> paymentDetails) throws CommerceException {
        return "faux-payment-token";
    }

    @Override
    protected final void initiateOrderProcessing(final String orderPath) throws CommerceException {
        // NOP
    }

    @Override
    protected final String getOrderStatus(final String orderId) throws CommerceException {
        return StringUtils.EMPTY;
    }

    @Override
    protected final Predicate getPredicate(final String predicateName) {
        return null;
    }

    @Override
    public final void modifyCartEntry(final int entryNumber, final int quantity) throws CommerceException {
        // NOP
    }

    @Override
    public final void addCartEntry(final Product product, final int quantity) throws CommerceException {
        // NOP
    }

    @Override
    public final PlacedOrder getPlacedOrder(final String orderId) throws CommerceException {
        return super.getPlacedOrder(orderId);
    }
}
