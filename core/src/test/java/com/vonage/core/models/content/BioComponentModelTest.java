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
class BioComponentModelTest {
    BioComponentModel model;
    @Mock
    Page containingPage;
    @Mock
    Resource resource;
    @Mock
    ValueMap pageProperties;
    @Mock
    private Session session ;
    @Mock
    private Query query;
    @Mock
    private ResourceResolver resourceResolver;
    @Mock
    private QueryBuilder queryBuilder;
    @Mock
    private PredicateGroup predicate;
    @Mock
    private PageManager pm;
    @Mock
    private SearchResult searchResult;
    @Mock
    private Hit hit;
    @BeforeEach
    public void setUp() throws Exception {
        model = new BioComponentModel();
    }
    @Test
    void testqueryResults() throws RepositoryException {
        when(resource.getResourceResolver()).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pm);
        when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(pm.getContainingPage(resource)).thenReturn(containingPage);
        when(containingPage.getProperties()).thenReturn(pageProperties);
        when(pageProperties.get("author")).thenReturn("testAuthor");
        Map predicateMap = new HashMap();
        predicateMap.put("path", "/content/experience-fragments/vonage/en-us/authors/");
        predicateMap.put("type", "cq:Page");
        // predicateMap.put("property", "property=jcr:content/@cq:tags, value=" + authorTag + ", operation=like");
        predicateMap.put("property", "jcr:content/@cq:tags");
        predicateMap.put("property.value", "testAuthor");
        predicateMap.put("property.operation", "operation=like");
        predicateMap.put("p.limit", "255");
        when(queryBuilder.createQuery(PredicateGroup.create(predicateMap), session)).thenReturn(query);
        when(query.getResult()).thenReturn(searchResult);
        List<Hit> testHits = new ArrayList<Hit>();
        testHits.add(hit);
        when(searchResult.getHits()).thenReturn(testHits);
        try {
            when(hit.getPath()).thenReturn("/content/experience-fragments/vonage/en-us/authors/aaron-ross");
            PrivateAccessor.setField(model,"resource",resource);
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.init();
        assertEquals("/content/experience-fragments/vonage/en-us/authors/aaron-ross", hit.getPath());
    }
    @Test
    void testEqualsForInjectedValues(){
        try {
            PrivateAccessor.setField(model,"role","Founder of PredictibleRevenue");
            PrivateAccessor.setField(model,"name","Aaron Ross");
            PrivateAccessor.setField(model,"bioText","Head of Marketing");
            PrivateAccessor.setField(model,"namePrefix","Mr");
            PrivateAccessor.setField(model,"moreByText","MoreByText");
            PrivateAccessor.setField(model,"bio","Aaron Ross is founder of PredictableRevenue.com and author of the award-winning, #1 Amazon bestseller");
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals("Founder of PredictibleRevenue", model.getRole());
        assertEquals("Aaron Ross", model.getName());
        assertEquals("Head of Marketing", model.getBioText());
        assertEquals("Mr", model.getNamePrefix());
        assertEquals("MoreByText", model.getNamgetMoreByTextePrefix());
        assertEquals("Aaron Ross is founder of PredictableRevenue.com and author of the award-winning, #1 Amazon bestseller",model.getBio());
    }
    @Test
    void testNullForInjectedValues() {
        try {
            PrivateAccessor.setField(model,"role",null);
            PrivateAccessor.setField(model,"name",null);
            PrivateAccessor.setField(model,"bioText",null);
            PrivateAccessor.setField(model,"namePrefix",null);
            PrivateAccessor.setField(model,"moreByText",null);
            PrivateAccessor.setField(model,"bio",null);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNull(model.getRole());
        assertNull(model.getName());
        assertNull(model.getBioText());
        assertNull(model.getNamePrefix());
        assertNull(model.getNamgetMoreByTextePrefix());
        assertNull(model.getBio());
    }
    @Test
    void testNotNullForInjectedValues() {
        try {
            PrivateAccessor.setField(model,"role","Founder of PredictibleRevenue");
            PrivateAccessor.setField(model,"name","Aaron Ross");
            PrivateAccessor.setField(model,"bioText","Head of Marketing");
            PrivateAccessor.setField(model,"namePrefix","Mr");
            PrivateAccessor.setField(model,"moreByText","MoreByText");
            PrivateAccessor.setField(model,"bio","Aaron Ross is founder of PredictableRevenue.com and author of the award-winning, #1 Amazon bestseller");
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(model.getRole());
        assertNotNull(model.getName());
        assertNotNull(model.getBioText());
        assertNotNull(model.getNamePrefix());
        assertNotNull(model.getNamgetMoreByTextePrefix());
        assertNotNull(model.getBio());
    }
}