package com.vonage.core.models.content;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vonage.core.services.CareersService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ResourceResolver;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith({MockitoExtension.class })
class CareersJobDetailComponentModelTest {
    CareersJobDetailComponentModel model;
    JsonObject jo = new JsonObject();
    JsonParser parser = new JsonParser();
    JsonElement jsonElement ;
    JsonElement jsonElementOffices ;
    JsonElement jsonDepartmentsObject ;
    @Mock
    private ResourceResolver resourceResolver;
    @Mock
    private Page currentPage;
    @Mock
    private Externalizer externalizer;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private RequestPathInfo requestPathInfo;
    @Mock
    private CareersService careersService;
    @BeforeEach
    public void setUp() throws Exception {
        model = new CareersJobDetailComponentModel();
        PrivateAccessor.setField(model,"request",request);
        PrivateAccessor.setField(model,"careersService",careersService);
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        PrivateAccessor.setField(model,"currentPage",currentPage);
        PrivateAccessor.setField(model,"externalizer",externalizer);
        when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    }
    @Test
    void testNotNull() throws NoSuchFieldException {
        jsonElement = parser.parse("{\"name\":\"London\"}");
        jsonElementOffices = parser.parse("[{\"name\":\"abc\"}]");
        jsonDepartmentsObject = parser.parse("[{\"name\":\"HR\"}]");
        jo.add("location",jsonElement);
        jo.add("offices", jsonElementOffices);
        jo.add("departments", jsonDepartmentsObject);
        jo.addProperty("id", "100");
        jo.addProperty("absolute_url", "https://boards.greenhouse.io/vonage/jobs/5393495002");
        jo.addProperty("content", "Sample JD");
        jo.addProperty("requisition_id", "40201");
        jo.addProperty("title", "Application Developer");
        jo.addProperty("updated_at", "2020-04-12T15:10:52-04:00");
        when(requestPathInfo.getSelectorString()).thenReturn("5109516002");
        when(careersService.getJobResourceAsJsonObject(requestPathInfo.getSelectorString())).thenReturn(jo);
        when(this.currentPage.getPath()).thenReturn("/careers/job-details");
        when(externalizer.publishLink(resourceResolver, "https", this.currentPage.getPath())).thenReturn("https://www.vonage.com/careers/job-details/");
        model.init();
        assertNotNull(model.getID());
        assertNotNull(model.getBrowserUrl());
        assertNotNull(model.getJobLocation());
        assertNotNull(model.getRequisitionID());
        assertNotNull(model.getJobTitle());
        assertNotNull(model.getCareer());
        assertNotNull(model.getDatePosted());
        assertNotNull(model.getApplyURL());
        assertNotNull(model.getJobDescription());
    }
    @Test
    void testNullSelectorID() {
        when(requestPathInfo.getSelectorString()).thenReturn(null);
        model.init();
        assertEquals("NO JOB FOUND", model.getJobTitle());
    }
    @Test
    void testNulljobDetailObject() {
         when(requestPathInfo.getSelectorString()).thenReturn("5109516002");
        when(careersService.getJobResourceAsJsonObject(requestPathInfo.getSelectorString())).thenReturn(null);
        model.init();
        assertEquals("NO JOB FOUND", model.getJobTitle());
    }
}