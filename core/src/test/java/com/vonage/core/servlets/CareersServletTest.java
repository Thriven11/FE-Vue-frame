package com.vonage.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vonage.core.constants.VngCareersConstants;
import com.vonage.core.utils.ServiceUtils;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class})
class CareersServletTest {

	private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

	CareersServlet careersServlet;

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
	ResourceResolver resourceResolver;

	@Mock
	ResourceResolverFactory resolverFactory;
	
	@Mock
	QueryBuilder queryBuilder;

	@Mock
	private PredicateGroup predicate;

	@Mock
	private Query query;

	@Mock
	PrintWriter writer;
	
	@Mock
	Session session;

	@Mock
	private SearchResult searchResult;

	@Mock
	private Hit hit;

	@Mock
    private Resource jobResource;
 
    @Mock
    private Node jobNode;

	@Mock
	ValueFactory valueFactory;
	
	
	@Mock
	Property property,property1,property2,property3,property4,property5,property6;
	
	@Mock
	Value val;

	@Mock
	SimpleDateFormat dateParser, dateFormatter;

	@Mock
	Date parsedDate;

	
	

	HashMap<String, String> queryMap;
	
	JSONObject jsonObject = new JSONObject();

	private List<Hit> list;

	@BeforeEach
	public void setup() {
		  careersServlet = new CareersServlet();
		  try {
			PrivateAccessor.setField(careersServlet,"resolverFactory",resolverFactory);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  aemContext.registerAdapter(ResourceResolver.class, QueryBuilder.class, queryBuilder);
		  hit = Mockito.mock(Hit.class);
		  list = new ArrayList<>();
		  
		  dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		  dateFormatter = new SimpleDateFormat("MMM d, yyyy");
		  
	}
	
	@Test
	void testGet() throws Throwable {
		list.add(hit);
		when(ServiceUtils.getReadResourceResolver(resolverFactory)).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(request.getParameter("loc")).thenReturn("4009285002");
		when(request.getParameter("dept")).thenReturn("4044836002");
		when(request.getParameter("sort")).thenReturn("desc");
		when(request.getParameter("page")).thenReturn("1");
		queryMap = new HashMap<String, String>();
		queryMap.put("path", VngCareersConstants.CAREERS_JOBS_PATH);
		queryMap.put("type", "nt:unstructured");
	    queryMap.put("group.p.and", "true");
	    queryMap.put("group.1_property", "@deptIds");
	    queryMap.put("group.1_property.1_value", "0");
	    queryMap.put("group.2_property", "@officeIds");
	    queryMap.put("group.2_property.value", "0");
	    queryMap.put("p.limit", "-1");
	    queryMap.put("orderby", "@updatedAt");
	    queryMap.put("orderby.sort", "desc");

		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getHits()).thenReturn(list);
		
		
		when(hit.getPath()).thenReturn("hit/path");
		
		when(ServiceUtils.getReadResourceResolver(resolverFactory)).thenReturn(resourceResolver);
		when(resourceResolver.getResource("hit/path")).thenReturn(jobResource);
		when(jobResource.adaptTo(Node.class)).thenReturn(jobNode);
		Value[] jobItems = {val};
		when(jobNode.getProperty("officeNames")).thenReturn(property);
		when(property.getValues()).thenReturn(jobItems);
		when(val.getString()).thenReturn("test");
		when(jobNode.getProperty("deptName")).thenReturn(property1);
		when(property1.getString()).thenReturn("test");
		when(jobNode.getProperty("jobId")).thenReturn(property2);
		when(property2.getString()).thenReturn("test");
		when(jobNode.getProperty("jobRequisitionId")).thenReturn(property3);
		when(property3.getString()).thenReturn("test");
		when(jobNode.getProperty("title")).thenReturn(property4);
		when(property4.getString()).thenReturn("test");
		when(jobNode.getProperty("updatedAt")).thenReturn(property5);
		when(property5.getString()).thenReturn("test");
		String dateString = "2020-04-12T15:10:52-04:00";
		when(jobNode.getProperty("updatedAt").getString()).thenReturn(dateString);
		when(jobNode.getProperty("officeNames").getValues()).thenReturn(jobItems);
		
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
		when( response.getWriter()).thenReturn(writer);
		careersServlet.doGet(request, response);
		assertEquals(0, response.getStatus());
	}
}
