package com.vonage.core.models.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.spi.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vonage.core.models.injectors.impl.CTAPropertyInjector;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CardComponentModelTest {

    public final AemContext context = new AemContext();

    @InjectMocks
    private CTAPropertyInjector ctaInjector;
    private CardComponentModel adapted;
    private static final String CTA_PATH = "/content";

    @BeforeEach
    public void setUp() throws Exception {
        context.load().json("/com/vonage/core/models/content/CardComponentModelTest.json", CTA_PATH);
        context.currentResource("/content/iconCard-1");
        context.registerService(Injector.class, ctaInjector);
        context.addModelsForClasses(CardComponentModel.class);
        Resource adaptable = context.request().getResource();
        adapted = adaptable.adaptTo(CardComponentModel.class);
    }

    @Test
    void testCTANotEmpty() {
        context.currentResource("/content/iconCard-2");
        adapted = context.request().getResource().adaptTo(CardComponentModel.class);
        assertFalse(adapted.getLink().isEmpty());
    }

    @Test
    void testCTAEmpty() {
        context.currentResource("/content/iconCard-3");
        adapted = context.request().getResource().adaptTo(CardComponentModel.class);
        assertTrue(adapted.getLink().isEmpty());
    }

    @Test
    void testCTAHrefInternal() {
        context.currentResource("/content/iconCard-4");
        adapted = context.request().getResource().adaptTo(CardComponentModel.class);
        assertFalse(adapted.getLink().isEmpty());
    }

    @Test
    void testGetTitle() {
        assertEquals("Communications APIs", adapted.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("Customize your offering easily with our innovative apps and integrations.",
                adapted.getDescription());
    }

    @Test
    void testGetFileReference() {
        assertNull(adapted.getFileReference());
    }

    @Test
    void testGetIcon() {
        assertEquals("cloud", adapted.getIcon());
    }

    @Test
    void testGetTheme() {
        assertEquals("magenta-coral", adapted.getTheme());
    }

    @Test
    void testGetPosition() {
        assertNull(adapted.getPosition());
    }

    @Test
    void testGetHeaderType() {
        assertEquals("h5", adapted.getHeaderType());
    }

    @Test
    void testGetLink() {
        assertEquals("#", adapted.getLink().getHref());
        assertEquals("_blank", adapted.getLink().getTarget());
        assertEquals("", adapted.getLink().getType());
        assertEquals("", adapted.getLink().getLabel());
    }

    @Test
    void testGetAltText() {
        assertNull(adapted.getAltText());
    }

    @Test
    void testGetSize() {
        assertNull(adapted.getSize());
    }

    @Test
    void testGetTextAlignemnt() {
        assertFalse(adapted.getTextAlignemnt());
    }

    @Test
    void testGetMarginBottom() {
        assertNull(adapted.getMarginBottom());
    }

    @Test
    void testGetTopAllignment() {
        assertNull(adapted.getTopAllignment());
    }

    @Test
    void testGetHeadline() {
        assertNull(adapted.getHeadline());
    }

}
