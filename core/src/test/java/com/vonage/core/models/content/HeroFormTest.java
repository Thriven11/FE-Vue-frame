package com.vonage.core.models.content;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class HeroFormTest {
    HeroForm model;

    @Mock
    Resource resource;
    @Mock
    Resource childResource;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    Page currentPage;
    @Mock
    ValueMap pageProperties;


    @BeforeEach
    public void setUp() throws Exception {
        model = new HeroForm();
        PrivateAccessor.setField(model,"currentPage",currentPage);
        PrivateAccessor.setField(model,"resourceResolver",resourceResolver);
        PrivateAccessor.setField(model,"bulletPointss",childResource);
    }

    @Test
    void testEqualsForInjectedValues(){
        try {
            PrivateAccessor.setField(model,"eyebrowContent","TestEyebro");
            PrivateAccessor.setField(model,"title","Test title");
            PrivateAccessor.setField(model,"description","Test Descritpion");
            PrivateAccessor.setField(model,"confMessagePath","/content/experience-fragments/vonage/");

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals("TestEyebro", model.getEyebrowContent());
        assertEquals("Test title", model.getTitle());
        assertEquals("Test Descritpion", model.getDescription());
        assertEquals("/content/experience-fragments/vonage/", model.getConfMessagePath());
    }
    @Test
    void testNullForInjectedValues() {
        try {
            PrivateAccessor.setField(model,"eyebrowContent",null);
            PrivateAccessor.setField(model,"title",null);
            PrivateAccessor.setField(model,"description",null);
            PrivateAccessor.setField(model,"confMessagePath",null);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNull(model.getEyebrowContent());
        assertNull(model.getTitle());
        assertNull(model.getDescription());
        assertNull(model.getConfMessagePath());

    }

    @Test
    void testForNotNullValues(){
        try {
            PrivateAccessor.setField(model,"eyebrowContent","TestEyebro");
            PrivateAccessor.setField(model,"title","Test title");
            PrivateAccessor.setField(model,"description","Test Descritpion");
            PrivateAccessor.setField(model,"confMessagePath","/content/experience-fragments/vonage/");

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(model.getEyebrowContent());
        assertNotNull(model.getTitle());
        assertNotNull(model.getDescription());
        assertNotNull(model.getConfMessagePath());

    }

}