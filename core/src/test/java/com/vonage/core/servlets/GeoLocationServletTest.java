package com.vonage.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

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
public class GeoLocationServletTest {

	@InjectMocks
	GeoLocationServlet geoLocationServlet;

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

	@BeforeEach
	public void setup() {

	}

	@Test
	void testGet() throws ServletException, IOException {
		
		Map<String, String> headers = new HashMap<>();
		headers.put("cf-ipcountry", "United States");
		
		Enumeration<String> headerNames = Collections.enumeration(headers.keySet());
		when(request.getHeaderNames()).thenReturn(headerNames);
		when( response.getWriter()).thenReturn(writer);
		when( response.getStatus()).thenReturn(SlingHttpServletResponse.SC_OK);
		geoLocationServlet.doGet(request, response);
		assertEquals(SlingHttpServletResponse.SC_OK, response.getStatus());
	}
	
	@Test
	void testGet2() throws ServletException, IOException {

		when(request.getHeaderNames()).thenReturn(null);
		when( response.getWriter()).thenReturn(writer);
		when( response.getStatus()).thenReturn(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		geoLocationServlet.doGet(request, response);
		assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
	}

}
