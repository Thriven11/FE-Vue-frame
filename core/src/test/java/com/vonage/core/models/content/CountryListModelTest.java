package com.vonage.core.models.content;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.vonage.core.constants.VonageConstants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CountryListModelTest {
    private final AemContext ctx = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    @Mock
    CountryListModel model;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    Page currentPage,page;
    @Mock
    Iterator<Resource> countries,languages;
    @Mock
    Resource res,country,language;
    @Mock
    Page countryPage,languagePage;
    @Mock
    Externalizer externalizer;
    private ArrayList<Map<String, String>> countriesList = new ArrayList<>();
    Locale locale;
    /*
     * @BeforeEach public void setUp() throws Exception {
     * ctx.load().json("/com/vonage/core/models/content/CountryListModelTest.json",
     * VonageConstants.COUNTRIES_LIST_PATH);
     * ctx.addModelsForPackage("com.vonage.core.models.content"); }
     *
     * @Test public void testNull() { model =
     * ctx.request().adaptTo(CountryListModel.class);
     * assertTrue(model.getCountriesList().size() == 0); }
     */
    @Test
    public void tes() throws NoSuchFieldException {
        locale = new Locale("ENG", "In");
        PrivateAccessor.setField(model, "currentPage", currentPage);
        PrivateAccessor.setField(model, "resourceResolver", resourceResolver);
        PrivateAccessor.setField(model, "externalizer", externalizer);
        PrivateAccessor.setField(model, "countriesList", countriesList);
        when(currentPage.getPath()).thenReturn("/content/vonage/live-copies/us/en/abc");
        when(resourceResolver.getResource("/content/vonage/live-copies")).thenReturn(res);
        when(res.listChildren()).thenReturn(countries);
        when(countries.hasNext()).thenReturn(true).thenReturn(false);
        when(countries.next()).thenReturn(country);
        when(country.adaptTo(Page.class)).thenReturn(countryPage);
        when(country.listChildren()).thenReturn(languages);
        when(languages.hasNext()).thenReturn(true).thenReturn(false);
        when(languages.next()).thenReturn(language);
        when(language.adaptTo(Page.class)).thenReturn(languagePage);
        when(language.getPath()).thenReturn("/content/vonage/live-copies/us/en");
        when(currentPage.getAbsoluteParent(4)).thenReturn(page);
        when(page.getPath()).thenReturn("test");
        Mockito.lenient().when(externalizer.publishLink(resourceResolver, "https", "/content/vonage/live-copies")).thenReturn("/content/vonage/live-copies");
        when(languagePage.getLanguage()).thenReturn(locale);
        //when(locale.getCountry()).thenReturn("IN");
        model.init();
        assertEquals("eng-IN", model.getCurrentLocale());
        assertNotNull(model.getCurrentLocale());
        assertNotNull(model.getCountriesList());
    }
}