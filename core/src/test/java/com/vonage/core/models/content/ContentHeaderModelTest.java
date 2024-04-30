package com.vonage.core.models.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ContentHeaderModelTest {

    private final AemContext ctx = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    private static final String PAGE_PATH = "/content";
    private ContentHeaderModel model;

    @Mock
    Page currentPage;

    @BeforeEach
    public void setUp() throws Exception {
        ctx.registerService(Page.class, currentPage);
        ctx.load().json("/com/vonage/core/models/content/ContentHeaderModelTest.json", PAGE_PATH);
        ctx.addModelsForPackage("com.vonage.core.models.content");
        ctx.currentResource(PAGE_PATH);
    }

    @Test
    public void testHeadelineText() {
        Locale locale = new Locale("en", "US");
        when(currentPage.getLanguage()).thenReturn(locale);
        model = ctx.request().adaptTo(ContentHeaderModel.class);
        assertEquals("Headline text", model.getHeadlineText());
    }

    @Test
    public void testEnUsDate() {
        Locale locale = new Locale("en", "US");
        when(currentPage.getLanguage()).thenReturn(locale);
        model = ctx.request().adaptTo(ContentHeaderModel.class);
        assertEquals("November 12, 2019", model.getContentDate());
    }

    @Test
    public void testFrFrDate() {
        Locale locale = new Locale("fr", "FR");
        when(currentPage.getLanguage()).thenReturn(locale);
        model = ctx.request().adaptTo(ContentHeaderModel.class);
        assertEquals("12 novembre 2019", model.getContentDate());
    }

    @Test
    public void testEnGbDate() {
        Locale locale = new Locale("en", "GB");
        when(currentPage.getLanguage()).thenReturn(locale);
        model = ctx.request().adaptTo(ContentHeaderModel.class);
        assertEquals("12 November 2019", model.getContentDate());
    }
}