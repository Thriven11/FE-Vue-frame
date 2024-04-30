package com.vonage.core.models.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.apache.sling.models.spi.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vonage.core.models.injectors.impl.FormattedDatePropertyInjector;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CopyRightComponentModelTest {
    
    private final AemContext ctx = new AemContext();
    private static final String CONTENT_PATH = "/content";
    private CopyRightComponentModel model;
    @InjectMocks
    private FormattedDatePropertyInjector formattedDatePropertyInjector;
    
    @BeforeEach
    public void setUp() throws Exception {
        ctx.addModelsForClasses("com.vonage.core.models.content.CopyRightComponentModel.class");
        ctx.registerService(Injector.class, formattedDatePropertyInjector);
        ctx.load().json("/com/vonage/core/models/content/CopyRightComponentModelTest.json", CONTENT_PATH);
        ctx.currentResource(CONTENT_PATH);
        model = ctx.request().getResource().adaptTo(CopyRightComponentModel.class);
    }
    
    @Test
    void testYearPositiveCase() {
        final String expected = Integer.toString(LocalDateTime.now().getYear());
        final String actual = model.getSystemDate().getSystemYear();
        assertEquals(expected, actual);
    }
    
    @Test
    void testYearNegativeCase() {
        final String expected = "2018";
        final String actual = model.getSystemDate().getSystemYear();
        assertNotEquals(expected, actual);
    }
    
}