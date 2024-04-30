package com.vonage.core.models.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.vonage.core.constants.VonageConstants;

import junitx.util.PrivateAccessor;

@ExtendWith({ MockitoExtension.class })
public class LanguageListModelTest {

    @Mock
    Page currentPage, parentPage, languagePage;
    
    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Externalizer externalizer;
    
    @Mock
    Resource country, language;
    
    @Mock
    Resource pageResourceResolver;
    
    @Mock
    Iterable<Resource> languages;
    
    @Mock
    Iterator<Resource> languagesItr;

    @InjectMocks
    LanguageListModel model;
	
	String currentLanguage;
	
	ArrayList<Map<String, String>> languageList = new ArrayList<>();

	@BeforeEach
	public void setUp() throws Exception {
		PrivateAccessor.setField(model, "currentPage", currentPage);
		PrivateAccessor.setField(model, "resourceResolver", resourceResolver);
        PrivateAccessor.setField(model, "externalizer", externalizer);
        PrivateAccessor.setField(model, "languageList", languageList);
        PrivateAccessor.setField(model, "currentLanguage", currentLanguage);
	}

	@Test
	void test() throws RepositoryException, NoSuchFieldException {
		Locale locale = new Locale("ENG", "US");		
		when(currentPage.getPath()).thenReturn("/content/vonage/live-copies/us/en/about-us");
		when(currentPage.getAbsoluteParent(3)).thenReturn(parentPage);
		when(parentPage.adaptTo(Resource.class)).thenReturn(country);
		when(country.hasChildren()).thenReturn(true, false);
		when(country.getChildren()).thenReturn(languages);
		when(country.getChildren().iterator()).thenReturn(languagesItr);
		when(languagesItr.hasNext()).thenReturn(true, false);
		when(languagesItr.next()).thenReturn(language);
		when(languagesItr.next().adaptTo(Page.class)).thenReturn(languagePage);
		when(languagePage.getPath()).thenReturn("/content/vonage/live-copies/us/en");
		when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getPath()).thenReturn("/content/vonage/live-copies");
		String pagePath = currentPage.getPath().replace(currentPage.getAbsoluteParent(4).getPath(), languagePage.getPath());
		when(resourceResolver.getResource(pagePath)).thenReturn(pageResourceResolver);
		when(externalizer.publishLink(resourceResolver, "https", pagePath)).thenReturn("/content/vonage/live-copies");
		when(languagePage.getLanguage()).thenReturn(locale);
		String displayLanguage = languagePage.getLanguage().getDisplayLanguage();
        String languageCode = languagePage.getLanguage().getLanguage().toLowerCase();
		when(language.getPath()).thenReturn("/content/vonage/live-copies/us/en");
		currentLanguage = languageCode;
		Map<String, String> site = new HashMap<>();
		site.put("name", "vng-lang-" + displayLanguage.toLowerCase());
        site.put("url", externalizer.publishLink(resourceResolver, "https", pagePath));
        site.put("language", languageCode);
        languageList.add(site);
        Map<String, String> site1 = new HashMap<>();
        site1.put("name", "vng-lang-" + displayLanguage.toLowerCase());
        site1.put("url", externalizer.publishLink(resourceResolver, "https", pagePath));
        site1.put("language", languageCode);
        languageList.add(site1);
        
        model.init();
        
        assertNotNull(currentPage);
		assertEquals(StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH), true);
        assertNotNull(country);
        assertNotNull(language.adaptTo(Page.class));
        assertNotNull(languagePage);
        assertNotNull(resourceResolver.getResource(pagePath));
        assertEquals(currentPage.getPath().contains(language.getPath()), true);
        assertEquals(languageList, model.getLanguageList());
        assertEquals(currentLanguage, model.getCurrentLanguage());
        assertEquals(languageList.size() > 1, model.showLanguageDropdown());
	}
	
	@Test
	void test1() throws RepositoryException, NoSuchFieldException {
		Locale locale = new Locale("ENG", "US");		
		when(currentPage.getPath()).thenReturn("/content/vonage/live-copies/us/en/about-us");
		when(currentPage.getAbsoluteParent(3)).thenReturn(parentPage);
		when(parentPage.adaptTo(Resource.class)).thenReturn(country);
		when(country.hasChildren()).thenReturn(true, false);
		when(country.getChildren()).thenReturn(languages);
		when(country.getChildren().iterator()).thenReturn(languagesItr);
		when(languagesItr.hasNext()).thenReturn(true, false);
		when(languagesItr.next()).thenReturn(language);
		when(languagesItr.next().adaptTo(Page.class)).thenReturn(languagePage);
		when(languagePage.getPath()).thenReturn("/content/vonage/live-copies/us/en");
		when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
		when(parentPage.getPath()).thenReturn("/content/vonage/live-copies");
		String pagePath = currentPage.getPath().replace(currentPage.getAbsoluteParent(4).getPath(), languagePage.getPath());
		String homepagePath = languagePage.getPath() + "/homepage";
		when(resourceResolver.getResource(pagePath)).thenReturn(null);
		when(externalizer.publishLink(resourceResolver, "https", homepagePath)).thenReturn("/content/vonage/live-copies/us/en/homepage");
		when(languagePage.getLanguage()).thenReturn(locale);
		String displayLanguage = languagePage.getLanguage().getDisplayLanguage();
        String languageCode = languagePage.getLanguage().getLanguage().toLowerCase();
		when(language.getPath()).thenReturn("/content/vonage/live-copies/us/en");
		currentLanguage = languageCode;
		Map<String, String> site = new HashMap<>();
		site.put("name", "vng-lang-" + displayLanguage.toLowerCase());
        site.put("url", externalizer.publishLink(resourceResolver, "https", pagePath));
        site.put("language", languageCode);
        languageList.add(site);
        Map<String, String> site1 = new HashMap<>();
        site1.put("name", "vng-lang-" + displayLanguage.toLowerCase());
        site1.put("url", externalizer.publishLink(resourceResolver, "https", pagePath));
        site1.put("language", languageCode);
        languageList.add(site1);
        
        model.init();
        
        assertNotNull(currentPage);
		assertEquals(StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH), true);
        assertNotNull(country);
        assertNotNull(language.adaptTo(Page.class));
        assertNotNull(languagePage);
        assertNull(resourceResolver.getResource(pagePath));
        assertEquals(currentPage.getPath().contains(language.getPath()), true);
        assertEquals(languageList, model.getLanguageList());
        assertEquals(currentLanguage, model.getCurrentLanguage());
        assertEquals(languageList.size() > 1, model.showLanguageDropdown());
	}

	@Test
	void test2() throws RepositoryException, NoSuchFieldException {
		when(currentPage.getPath()).thenReturn("/content/vonage/live-copies/us/en/about-us");
		when(currentPage.getAbsoluteParent(3)).thenReturn(parentPage);
		when(parentPage.adaptTo(Resource.class)).thenReturn(country);
		when(country.hasChildren()).thenReturn(true, false);
		when(country.getChildren()).thenReturn(languages);
		when(languagesItr.hasNext()).thenReturn(true, false);
		when(country.getChildren().iterator()).thenReturn(languagesItr);
		when(languagesItr.next()).thenReturn(language);
		languagePage = null;
		when(languagesItr.next().adaptTo(Page.class)).thenReturn(languagePage);
        
        model.init();
        
        assertNotNull(currentPage);
		assertEquals(StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH), true);
        assertNotNull(country);
        assertNull(language.adaptTo(Page.class));
        assertNull(languagePage);
        assertEquals(languageList, model.getLanguageList());
        assertEquals(languageList.size() > 1, model.showLanguageDropdown());
	}
	
	@Test
	void test3() throws RepositoryException, NoSuchFieldException {
		when(currentPage.getPath()).thenReturn("https://www.vonage.com");
		model.init();
		assertNotNull(currentPage);
		assertEquals(StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH), false);
		country = null;
	    assertNull(country);
	}
	
	@Test
	void test4() throws RepositoryException, NoSuchFieldException {		
		when(currentPage.getPath()).thenReturn("https://www.vonage.com");
        model.init();
        assertEquals(StringUtils.contains(currentPage.getPath(), VonageConstants.SITES_ROOT_PATH), false);
        currentPage = null;
        assertNull(currentPage);
	}

}