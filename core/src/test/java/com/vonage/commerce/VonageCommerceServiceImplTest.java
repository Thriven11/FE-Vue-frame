package com.vonage.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceSession;
import com.adobe.cq.commerce.api.Product;
import com.day.cq.wcm.api.LanguageManager;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;

@ExtendWith({ MockitoExtension.class })
class VonageCommerceServiceImplTest {

    private final AemContext ctx = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    private CommerceService service;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    SlingHttpServletResponse response;

    @Mock
    LanguageManager languageManager;

    @Mock
    PageManager pageManager;

    @Mock
    ResourceResolver resourceResolver;

    @InjectMocks
    VonageCommerceServiceFactory factory;

    Resource resource;

    @BeforeEach
    void setup() {
        resource = ctx.load().json("/com/vonage/commerce/VonageCommerceServiceFactoryTest.json",
                "/var/commerce/products/vonage");
        service = factory.getCommerceService(resource.getChild("communications-api/messaging/AC"));
    }

    @Test
    void testLogin() throws CommerceException {
        CommerceSession session = service.login(request, response);
        session.modifyCartEntry(0, 0);
        session.addCartEntry(null, 0);
        session.getPlacedOrder("nothing");
        assertNotNull(session);
    }

    @Test
    void testGetProduct() throws CommerceException {
        Product product = service.getProduct("/var/commerce/products/vonage/communications-api/messaging/AC");
        assertNull(product.getSKU());
        assertEquals("", ((VonageProductImpl) product).getBrand());
        assertEquals("Ascension Island", product.getTitle());
    }

    @Test
    void testGetOthers() throws CommerceException {
        service.getVoucher("some-path");
        service.getOrderPredicates();
        ((VonageCommerceServiceImpl) service).getCreditCardTypes();
        ((VonageCommerceServiceImpl) service).getCountries();
        service.catalogRolloutHook(null, null);
        service.sectionRolloutHook(null, null);
        service.productRolloutHook(null, null, null);
        assertNotNull(((VonageCommerceServiceImpl) service).getAvailablePaymentMethods());
        assertFalse(service.isAvailable("some-service"));
    }

}
