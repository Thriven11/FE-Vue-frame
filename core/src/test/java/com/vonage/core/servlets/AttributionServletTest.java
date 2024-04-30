package com.vonage.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;

import com.vonage.core.services.AttributionService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ MockitoExtension.class })
public class AttributionServletTest {

	@InjectMocks
	AttributionServlet attributionServlet;

	@Mock
	AttributionService attributionService;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	SlingHttpServletResponse response;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	ResourceResolverFactory resolverFactory;

	@Mock
	PrintWriter writer;

	String[] campaignNameArr;

	String cmpName;

	HashMap<String, String> campaign;

	@BeforeEach
	public void setup() {

	}

	@Test
	void testGet() throws ServletException, IOException {
		when(request.getRequestURI()).thenReturn("/bin/vonage/api/attribution");
		// when(request.getParameter("name")).thenReturn("some-url-api-test");
		HashMap<String, String> campaign = new HashMap<>();
		campaign.put("name", "some-url-api-test");
		campaign.put("id", "7011O000002cfuc");
		campaign.put("tfn", "1.844.365.9460");
		// when(attributionService.getCampaign("some-url-api-test")).thenReturn(campaign);
		when(response.getWriter()).thenReturn(writer);
		attributionServlet.doGet(request, response);
		assertEquals(0, response.getStatus());
	}

	@Test
	void testGet3() throws ServletException, IOException {
		when(request.getRequestURI()).thenReturn("/bin/vonage/api/attribution");
		// when(request.getParameter("name")).thenReturn(null);
		// when(attributionService.getCampaign("some-url-api-test")).thenReturn(null);
		// when(campaign.get("test")).thenThrow(IOException.class);
		when(response.getWriter()).thenReturn(writer);
		attributionServlet.doGet(request, response);
		assertEquals(0, response.getStatus());
	}

}
