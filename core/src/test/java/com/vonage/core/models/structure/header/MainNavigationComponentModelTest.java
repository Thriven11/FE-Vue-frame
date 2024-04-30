package com.vonage.core.models.structure.header;

import com.day.cq.wcm.api.Page;
import com.vonage.core.beans.CTA;
import com.vonage.core.models.CTAModel;
import com.vonage.core.services.PageContextService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import org.apache.sling.api.resource.Resource;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })

class MainNavigationComponentModelTest {

    MainNavigationComponentModel model;

    PrimaryNavigationComponentModel primaryNavigationComponentModel;
    @Mock
    CTAModel ctamodel;

    @Mock
    CTA cta;
    @Mock
    Page parentPage;

    @Mock
    Page currentPage;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private transient PageContextService pageContextService;

    @Mock
    Resource resource;

    @Mock
    List<PrimaryNavigationComponentModel> primaryNavList;

    @Mock
    Resource secondaryCta;

    @Mock
    Resource ucSecondaryCta;

    @Mock
    Resource ccSecondaryCta;

    @Mock
    Resource apiTokboxSecondaryCta;

    @Mock
    Resource apiNexmoSecondaryCta;

    @Mock
    CTA link;

    String Href;

    private String apiPrimary = "/content/vonage/language-masters/en/communications-apis";
    private String ucPrimary = "/content/vonage/language-masters/en/unified-communications";
    private String ccPrimary = "/content/vonage/language-masters/en/contact-centers";

    boolean isApiPage = true;
    boolean isUcPage = true;
    boolean isCcPage = true;

    private Object abbandonedCartLinkHref;

    @BeforeEach
    public void setUp() throws Exception {
        model = new MainNavigationComponentModel();
        PrivateAccessor.setField(model, "currentPage", currentPage);
        PrivateAccessor.setField(model, "pageContextService", pageContextService);
        PrivateAccessor.setField(model, "request", request);

        PrivateAccessor.setField(model, "isCcPage", isApiPage);
        primaryNavList = new ArrayList<>();
    }

    @Test
    void iscurrentpage() {
        Mockito.lenient().when(pageContextService.getIsApiPage(currentPage)).thenReturn(isApiPage);
        Mockito.lenient().when(pageContextService.getIsUcPage(currentPage)).thenReturn(isUcPage);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(isCcPage);
        // model.init();

    }

    @Test
    void testNull() {

        try {
            PrivateAccessor.setField(model, "secondaryCta", null);
            PrivateAccessor.setField(model, "ucSecondaryCta", null);
            PrivateAccessor.setField(model, "ccSecondaryCta", null);
            PrivateAccessor.setField(model, "apiTokboxSecondaryCta", null);
            PrivateAccessor.setField(model, "apiNexmoSecondaryCta", null);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getSecondaryCta());
        assertNull(model.getUcSecondaryCta());
        assertNull(model.getCcSecondaryCta());
        assertNull(model.getApiTokboxSecondaryCta());
        assertNull(model.getApiNexmoSecondaryCta());
        assertNull(model.getSecondaryCta());

    }

    @Test
    void getAlternativeCtaLabel() {
        Mockito.lenient().when(pageContextService.getIsApiPage(currentPage)).thenReturn(isApiPage);
        Mockito.lenient().when(pageContextService.getIsUcPage(currentPage)).thenReturn(isUcPage);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(isCcPage);

    }

    @Test
    void testGetContactUsFormUrlisApiPageTrueAndapiPrimaryNotNull() throws NoSuchFieldException {
        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        PrivateAccessor.setField(model, "apiPrimary", apiPrimary);
        Mockito.lenient().when(pageContextService.getIsApiPage(currentPage)).thenReturn(true);

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisApiPageTrueAndapiPrimaryNull() throws NoSuchFieldException {
        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        Mockito.lenient().when(pageContextService.getIsApiPage(currentPage)).thenReturn(true);
        when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
        when(currentPage.getAbsoluteParent(4).getPath())
                .thenReturn("/content/vonage/live-copies/us/en/communications-api/");

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisUCPageTrueAnducPrimaryNotNull() throws NoSuchFieldException {
        String pageCountry = "US";
        PrivateAccessor.setField(model, "isUcPage", isUcPage);

        PrivateAccessor.setField(model, "ucPrimary", ucPrimary);
        PrivateAccessor.setField(model, "pageCountry", pageCountry);
        Mockito.lenient().when(pageContextService.getIsUcPage(currentPage)).thenReturn(true);

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisUCPageTrueAnducPrimaryNull() throws NoSuchFieldException {
        String pageCountry = "US";
        PrivateAccessor.setField(model, "pageCountry", pageCountry);

        Mockito.lenient().when(pageContextService.getIsUcPage(currentPage)).thenReturn(true);
        when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
        when(currentPage.getAbsoluteParent(4).getPath())
                .thenReturn("/content/vonage/language-masters/en/unified-communications");

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisCCPageTrueAndccPrimaryNotNull() throws NoSuchFieldException {
        String pageCountry = "US";
        PrivateAccessor.setField(model, "isCcPage", isCcPage);

        PrivateAccessor.setField(model, "ccPrimary", ccPrimary);
        PrivateAccessor.setField(model, "pageCountry", pageCountry);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisCCPageTrueAndccPrimaryNull() throws NoSuchFieldException {
        String pageCountry = "US";
        PrivateAccessor.setField(model, "pageCountry", pageCountry);

        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);
        when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
        when(currentPage.getAbsoluteParent(4).getPath())
                .thenReturn("/content/vonage/live-copies/us/en/contact-centers/");

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisUCPageTrueAndUcPrimaryNotNullGBcountry() throws NoSuchFieldException {
        String pageCountry = "GB";
        PrivateAccessor.setField(model, "isUcPage", isUcPage);

        PrivateAccessor.setField(model, "ucPrimary", ucPrimary);
        PrivateAccessor.setField(model, "pageCountry", pageCountry);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);
        when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
        when(currentPage.getAbsoluteParent(4).getPath())
                .thenReturn("/content/vonage/live-copies/us/en/contact-centers/");

        model.getContactUsFormUrl();

    }

    @Test
    void testGetContactUsFormUrlisUCPageTrueAndUcPrimaryNullUScountry() throws NoSuchFieldException {
        String pageCountry = "US";
        PrivateAccessor.setField(model, "isUcPage", isUcPage);

        PrivateAccessor.setField(model, "pageCountry", pageCountry);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);
        when(currentPage.getAbsoluteParent(4)).thenReturn(parentPage);
        when(currentPage.getAbsoluteParent(4).getPath())
                .thenReturn("/content/vonage/live-copies/us/en/contact-centers/");

        model.getContactUsFormUrl();

    }

    @Test
    void testgetAlternativeCtaLabelForNull() {

        model.getAlternativeCtaLabel();
    }

    @Test
    void testgetAlternativeCtaLabelForCC() {
        String ccCtaLabel = "ccctalabeltest";
        try {
            PrivateAccessor.setField(model, "ccCtaLabel", ccCtaLabel);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertEquals("ccctalabeltest", model.getAlternativeCtaLabel());
        assertNull(null, model.getAlternativeCtaLabel());
        assertNotNull(model.getAlternativeCtaLabel());
        assertNotEquals("notequal", model.getAlternativeCtaLabel());

        model.getAlternativeCtaLabel();
    }

    @Test
    void testgetSecondaryCta() {
        try {
            PrivateAccessor.setField(model, "secondaryCta", secondaryCta);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getSecondaryCta());
    }

    @Test
    void testgetUcSecondaryCta() {
        try {
            PrivateAccessor.setField(model, "ucSecondaryCta", ucSecondaryCta);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getUcSecondaryCta());
    }

    @Test
    void testgetCcSecondaryCta() {
        try {
            PrivateAccessor.setField(model, "ccSecondaryCta", ccSecondaryCta);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getCcSecondaryCta());
    }

    @Test
    void testgetApiNexmoSecondaryCta() {
        try {
            PrivateAccessor.setField(model, "apiNexmoSecondaryCta", apiNexmoSecondaryCta);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getApiNexmoSecondaryCta());
    }

    @Test
    void testgetApiTokboxSecondaryCta() {
        try {
            PrivateAccessor.setField(model, "apiTokboxSecondaryCta", apiTokboxSecondaryCta);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getApiTokboxSecondaryCta());
    }

    @Test
    void testgetAbbandonedCartLinkHref() {
        Mockito.lenient().when(request.getAttribute("abbandonedCartLinkHref")).thenReturn(abbandonedCartLinkHref);
        model.getAbbandonedCartLinkHref();

    }

    @Test
    void testctanull() {
        try {
            PrivateAccessor.setField(model, "link", null);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNull(model.getLink());

    }

    @Test
    void getAlternativeCtaLabelAPI() throws NoSuchFieldException {
        String apiCtaLabel = "apictalabeltest";
        PrivateAccessor.setField(model, "isApiPage", isApiPage);

        PrivateAccessor.setField(model, "apiCtaLabel", apiCtaLabel);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);

        model.getAlternativeCtaLabel();

    }

    @Test
    void getAlternativeCtaLabelCC() throws NoSuchFieldException {
        String ucCtaLabel = "ucctalabeltest";

        PrivateAccessor.setField(model, "isUcPage", isUcPage);

        PrivateAccessor.setField(model, "ucCtaLabel", ucCtaLabel);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);

        model.getAlternativeCtaLabel();

    }

    @Test
    void getAlternativeCtaLabelUC() throws NoSuchFieldException {
        String ccCtaLabel = "ccctalabeltest";

        PrivateAccessor.setField(model, "isCcPage", isCcPage);

        PrivateAccessor.setField(model, "ccCtaLabel", ccCtaLabel);
        Mockito.lenient().when(pageContextService.getIsCcPage(currentPage)).thenReturn(true);

        model.getAlternativeCtaLabel();

    }

    @Test
    void getFinalSecondaryCtaNull() throws NoSuchFieldException {

        model.getFinalSecondaryCta();

    }

    @Test
    void testgetFinalSecondaryCta() {
        try {
            PrivateAccessor.setField(model, "secondaryCta", secondaryCta);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertNull(model.getSecondaryCta());
    }

    @Test
    void testgetAlternativeCtaLabelCC() throws NoSuchFieldException {
        boolean isCcPage = false;

        PrivateAccessor.setField(model, "isCcPage", isCcPage);

        model.getAlternativeCtaLabel();

    }

    @Test
    void test1getFinalSecondaryCtaEqualsCC() throws NoSuchFieldException {
        boolean isApiPage = false;
        boolean isCcPage = true;
        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        PrivateAccessor.setField(model, "isCcPage", isCcPage);
        PrivateAccessor.setField(model, "ccSecondaryCta", ccSecondaryCta);

        model.getFinalSecondaryCta();

    }

    @Test
    void test1getFinalSecondaryCtaEqualsUC() throws NoSuchFieldException {
        boolean isApiPage = false;
        boolean isUcPage = true;
        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        PrivateAccessor.setField(model, "isUcPage", isUcPage);
        PrivateAccessor.setField(model, "ucSecondaryCta", ucSecondaryCta);

        model.getFinalSecondaryCta();

    }

    @Test
    void test1getFinalSecondaryCtaEqualsSecondary() throws NoSuchFieldException {
        boolean isApiPage = false;
        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        PrivateAccessor.setField(model, "secondaryCta", secondaryCta);

        model.getFinalSecondaryCta();

    }

    @Test
    void test1getFinalSecondary() throws NoSuchFieldException {
        boolean isApiPage = false;
        boolean isCcPage = false;
        boolean isUcPage = false;

        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        PrivateAccessor.setField(model, "isCcPage", isCcPage);
        PrivateAccessor.setField(model, "isUcPage", isUcPage);

        model.getFinalSecondaryCta();

    }

    @Test
    void test3getFinalSecondary() throws NoSuchFieldException {
        boolean isApiPage = true;

        PrivateAccessor.setField(model, "isApiPage", isApiPage);

        model.getFinalSecondaryCta();
    }

    @Test
    void test3getFinalSecondaryAPINEXMO() throws NoSuchFieldException {
        boolean isApiPage = true;

        PrivateAccessor.setField(model, "isApiPage", isApiPage);
        PrivateAccessor.setField(model, "apiNexmoSecondaryCta", apiNexmoSecondaryCta);

        model.getFinalSecondaryCta();
    }

}