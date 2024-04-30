package com.vonage.core.models.content.navigations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.models.spi.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vonage.core.models.CTAModel;
import com.vonage.core.models.injectors.impl.CTAPropertyInjector;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class LinkListComponentModelTest {

    private final AemContext ctx = new AemContext();
    private static final String CTA_PATH = "/content";
    private LinkListComponentModel model;
    @InjectMocks
    private CTAPropertyInjector ctaInjector;

    @BeforeEach
    public void setUp() throws Exception {
        ctx.addModelsForPackage("com.vonage.core.models.content.navigations");
        ctx.registerService(Injector.class, ctaInjector);
        ctx.load().json("/com/vonage/core/models/content/navigations/LinkListComponentModelTest.json", CTA_PATH);
        ctx.currentResource(CTA_PATH);
        model = ctx.request().getResource().adaptTo(LinkListComponentModel.class);
    }

    @Test
    void testGetTitle() {
        final String expected = "Contact Centers";
        String actual = model.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void testGetLink() {
        assertNotNull(model.getLink());
    }

    @Test
    void testGetLinks() {
        assertEquals(2, model.getLinks().size());
        CTAModel link = model.getLinks().get(0);
        assertEquals("Partners", link.getLink().getLabel());
        assertEquals("https://www.newvoicemedia.com/en-us/partners", link.getLink().getHref());
        assertEquals("_blank", link.getLink().getTarget());
        assertEquals("", link.getLink().getType());
    }

}
