package com.vonage.core.models.content;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.models.spi.Injector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import com.vonage.core.models.injectors.impl.CTAPropertyInjector;
import com.vonage.core.models.structure.header.L3NavModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class L3NavModelTest {

    @InjectMocks
    private CTAPropertyInjector ctaInjector;
    private L3NavModel adapted;
    private static final String CTA_PATH = "/content/vonage/en-us";

    public void setUp(AemContext context) {
        context.load().json("/com/vonage/core/models/content/L3NavModelTest.json", CTA_PATH);
        context.addModelsForPackage("com.vonage.core.models.content");
        context.currentResource(CTA_PATH + "/new-page");
        context.registerService(Injector.class, ctaInjector);
        Page page = context.currentResource().adaptTo(Page.class);
        context.currentPage(page);
        adapted = context.request().adaptTo(L3NavModel.class);
    }

    @Test
    void testNavLinkModel(AemContext context) {
        setUp(context);
        assertEquals(2, adapted.getNavLinkModel().size());
    }

    @Test
    void testActiveNavLinkLabel(AemContext context) {
        setUp(context);
        assertEquals("Homepage", adapted.getActiveNavLinkLabel());
    }

    @Test
    void testActiveNavLinkLabelEmpty(AemContext context2) {
        context2.load().json("/com/vonage/core/models/content/L3NavModelTest.json", CTA_PATH);
        context2.addModelsForPackage("com.vonage.core.models.content");
        context2.currentResource(CTA_PATH + "/other-page");
        context2.registerService(Injector.class, ctaInjector);
        Page page = context2.currentResource().adaptTo(Page.class);
        context2.currentPage(page);
        L3NavModel adapted2 = context2.request().adaptTo(L3NavModel.class);
        assertEquals("", adapted2.getActiveNavLinkLabel());
    }

}
