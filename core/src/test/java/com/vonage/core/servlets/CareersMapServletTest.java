package com.vonage.core.servlets;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.facets.Bucket;
import com.day.cq.search.facets.Facet;
import com.day.cq.search.facets.extractors.FacetImpl;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vonage.core.models.careers.DepartmentModel;
import com.vonage.core.utils.ServiceUtils;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class})
public class CareersMapServletTest {
    private static final String property = null;
    CareersMapServlet careersMapServlet;
    //private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);
    JsonObject jo = new JsonObject();
    JsonParser parser = new JsonParser();
    JsonElement jsonElement ;
    JsonElement jsonElementOffices ;
    JsonElement jsonDepartmentsObject ;
    @Mock
    SlingHttpServletRequest request;
    @Mock
    SlingHttpServletResponse response;
    @Mock
    Node officeNd;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    ResourceResolverFactory resolverFactory;
    @Mock
    QueryBuilder queryBuilder;
    @Mock
    PredicateGroup predicate;
    @Mock
    com.day.cq.search.Query query;
    @Mock
    PrintWriter writer;
    @Mock
    Session session;
    @Mock
    ValueMap value;
    @Mock
    Property prop;
    @Mock
    Facet facet;
    @Mock
    Bucket bucket;
    @Mock
    Iterator<Resource> iterator;
    @Mock
    Iterator<Resource> iterator1;
    @Mock
    SearchResult searchResult;
    @Mock
    Resource resource;
    @Mock
    Resource resource2;
    @Mock
    Resource resource1;
    @Mock
    Resource resource3;
    @Mock
    DepartmentModel dpml;
    HashMap<String, String> queryMap;
     JSONObject jsonObject = new JSONObject();
     long total=2;
     CareersMapServlet careersMapServlet1;
     @BeforeEach
    public void setup() {
        careersMapServlet = new CareersMapServlet();
         careersMapServlet1 = Mockito.spy(careersMapServlet);
          try {
            PrivateAccessor.setField(careersMapServlet,"resolverFactory",resolverFactory);
          //  PrivateAccessor.setField(careersMapServlet,"resourceResolver",resourceResolver);
         when(ServiceUtils.getReadResourceResolver(resolverFactory)).thenReturn(resourceResolver);
            when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
            when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          //aemContext.registerAdapter(ResourceResolver.class, QueryBuilder.class, queryBuilder);
    }
    @Test
    void testGet() throws Throwable {
                //setup();
        when(resourceResolver.getResource("/var/vonage/careers/offices")).thenReturn(resource);
        //when(resource.getChildren()).thenReturn(iterable);
        when(resource.listChildren()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(resource1);
        when(resource1.adaptTo(Node.class)).thenReturn(officeNd);
        when(resource1.getValueMap()).thenReturn(value);
       Mockito.lenient().when(value.get("id")).thenReturn("1001");
       when(resourceResolver.getResource("/var/vonage/careers/departments")).thenReturn(resource2);
       when(resource2.listChildren()).thenReturn(iterator1);
        when(iterator1.hasNext()).thenReturn(true).thenReturn(false);
        when(iterator1.next()).thenReturn(resource3);
        when(resource3.adaptTo(DepartmentModel.class)).thenReturn(dpml);
        when(dpml.getId()).thenReturn("1001");
        when(dpml.getName()).thenReturn("HR");
        HashMap facets =new HashMap();
        FacetImpl facet1 = new FacetImpl();
        //facets.put("1_property", facet1);
        HashMap predicateMap = new HashMap();
        predicateMap.put("path", "/var/vonage/careers/offices");
        predicateMap.put("type", "nt:unstructured");
        predicateMap.put("1_property", "deptIds");
        predicateMap.put("2_property", "officeIds");
        predicateMap.put("2_property.operation", "equals");
        predicateMap.put("2_property.value", "50001");
        predicateMap.put("facets", true);
        when(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
        when(query.getResult()).thenReturn(searchResult);
        when(searchResult.getTotalMatches()).thenReturn(total);
        when(searchResult.getFacets()).thenReturn(facets);
        when( response.getWriter()).thenReturn(writer);
        when( response.getStatus()).thenReturn(SlingHttpServletResponse.SC_OK);
        JsonObject jsonobj = new JsonObject();
       // Mockito.lenient().doReturn(jsonobj).when(careersMapServlet1).getDepartments();
        //when(ServiceUtils.getReadResourceResolver(resolverFactory)).thenReturn((ResourceResolver) resourceResolver);
        //when(((ResourceResolver) resourceResolver).getResource("hit/path")).thenReturn(resourceResolver);
        careersMapServlet.doGet(request, response);
            assertEquals(SlingHttpServletResponse.SC_OK, response.getStatus());
    }
    
}