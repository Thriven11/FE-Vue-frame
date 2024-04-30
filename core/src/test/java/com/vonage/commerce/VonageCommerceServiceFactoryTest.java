package com.vonage.commerce;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;

@ExtendWith({ MockitoExtension.class })
class VonageCommerceServiceFactoryTest {

    private final AemContext ctx = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    @InjectMocks
    VonageCommerceServiceFactory factory;

    @Test
    void testGetCommerceService() {
        Resource resource = ctx.load().json("/com/vonage/commerce/VonageCommerceServiceFactoryTest.json",
                "/var/commerce/products/vonage");
        assertNotNull(factory.getCommerceService(resource.getChild("communications-api/messaging/AC")));
    }

}
