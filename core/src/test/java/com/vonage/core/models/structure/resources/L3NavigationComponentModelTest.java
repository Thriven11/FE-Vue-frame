package com.vonage.core.models.structure.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.apache.sling.models.spi.Injector;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import com.vonage.core.models.injectors.impl.CTAPropertyInjector;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class L3NavigationComponentModelTest {

    private final AemContext ctx = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    private static final String PAGE_PATH = "/content";
    private L3NavigationComponentModel model;
    @InjectMocks
    private CTAPropertyInjector ctaInjector;

    @Mock
    Page currentPage;

    @BeforeEach
    public void setUp() throws Exception {
        ctx.load().json("/com/vonage/core/models/structure/resources/L3NavigationComponentModelTest.json", PAGE_PATH);
        ctx.addModelsForPackage("com.vonage.core.models.structure.resources");
        ctx.registerService(Injector.class, ctaInjector);
        ctx.currentResource(PAGE_PATH);
    }

    @Test
    void testGetTitle() {
        model = ctx.request().adaptTo(L3NavigationComponentModel.class);
        assertEquals("Category", model.getTitle());
    }

    @Test
    void testGetCategoriesLabel() {
        model = ctx.request().adaptTo(L3NavigationComponentModel.class);
        assertEquals("Category:", model.getCategoriesLabel());
    }

    @Test
    void testGetLinks() {
        model = ctx.request().adaptTo(L3NavigationComponentModel.class);
        assertEquals(5, model.getLinks().size());
    }

    @Test
    void testGetCurrentCategoryTitle() {
        ctx.registerService(Page.class, currentPage);
        when(currentPage.getPath()).thenReturn("/content/vonage/en-us/featured");
        model = ctx.request().adaptTo(L3NavigationComponentModel.class);
        assertEquals("Featured", model.getCurrentCategoryTitle());
    }

    @Test
    void testGetCurrentCategoryTitleWhenNotCurrent() {
        ctx.registerService(Page.class, currentPage);
        when(currentPage.getPath()).thenReturn("/content/vonage/en-us/otherpage");
        model = ctx.request().adaptTo(L3NavigationComponentModel.class);
        assertEquals("", model.getCurrentCategoryTitle());
    }

    @Test
    void testGetCurrentCategoryTitleWhenPageNull() {
        model = ctx.request().adaptTo(L3NavigationComponentModel.class);
        assertEquals("", model.getCurrentCategoryTitle());
    }

}
