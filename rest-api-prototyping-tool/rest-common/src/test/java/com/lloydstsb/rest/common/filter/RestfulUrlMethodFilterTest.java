package com.lloydstsb.rest.common.filter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lloydstsb.rest.common.filter.RestfulUrlMethodFilter;

public class RestfulUrlMethodFilterTest {
	
	private static final String BASE_URL = "http://localhost:8080/api/v1";

	private static final String ARRANGEMENT_ID_VALUE_ENCODED = "ACCOUNT%7C%7C12345678%7C%7C123456";

	private static final String ARRANGEMENT_ID_VALUE = "ACCOUNT||12345678||123456";

	private static final String ARRANGEMENTS_URI = "/arrangements.json";

	private static final String ARRANGEMENTS_URL = BASE_URL + "/arrangements.json";

	private static final String ARRANGEMENTS_BENEFICIARIES_BY_ID_URL = BASE_URL + "/arrangements/{arrangementId}/beneficiaries/{beneficiaryId}.json";

	private static final String ARRANGEMENTS_BENEFICIARIES_BY_ID_URI = "/arrangements/{arrangementId}/beneficiaries/{beneficiaryId}.json";
	
	private static final String PAYMENTS_URL = BASE_URL + "/arrangements/{arrangementId}/beneficiaries/{beneficiaryId}/payments.json";

	private static final String PAYMENTS_URI = "/arrangements/{arrangementId}/beneficiaries/{beneficiaryId}/payments.json";

	private static final String ARRANGEMENTS_BY_ID_URL = BASE_URL + "/arrangements/{arrangementId}.json";
	
	private static final String ARRANGEMENTS_BY_ID_ENCODED_URL = BASE_URL + "/arrangements/%7BarrangementId%7D.json";

	private static final String ARRANGEMENTS_BY_ID_URI = "/arrangements/{arrangementId}.json";
	
	private static final String ARRANGEMENTS_BY_ID_ENCODED_URI = "/arrangements/%7BarrangementId%7D.json";

	RestfulUrlMethodFilter restfulUrlMethodFilter = new RestfulUrlMethodFilter();

	@Mock
	private HttpServletRequest httpServletRequest;

	@Mock
	private HttpServletResponse httpServletResponse;

	@Mock
	private FilterChain filterChain;

	private Map<String, String> parameters;
	
	private Enumeration<String> parameterNames;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		parameters = new HashMap<String, String>();
		when(httpServletRequest.getParameterMap()).thenReturn(parameters);
		restfulUrlMethodFilter.init(null);
	}

	@Test
	public void testChangePostToGetWithTemplate() throws IOException, ServletException {
		parameters.put("arrangementId", ARRANGEMENT_ID_VALUE);
		parameterNames = new Vector<String>(parameters.keySet()).elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("GET");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_BY_ID_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_BY_ID_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("GET", argument.getValue().getMethod());
		assertEquals("/arrangements/" + ARRANGEMENT_ID_VALUE_ENCODED + ".json", argument.getValue().getRequestURI());
		assertEquals(BASE_URL + "/arrangements/" + ARRANGEMENT_ID_VALUE_ENCODED + ".json", argument.getValue().getRequestURL().toString());
	}
	
	@Test
	public void testChangePostToGetWithEncodedTemplate() throws IOException, ServletException {
		parameters.put("arrangementId", ARRANGEMENT_ID_VALUE);
		parameterNames = new Vector<String>(parameters.keySet()).elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("GET");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_BY_ID_ENCODED_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_BY_ID_ENCODED_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("GET", argument.getValue().getMethod());
		assertEquals("/arrangements/" + ARRANGEMENT_ID_VALUE_ENCODED + ".json", argument.getValue().getRequestURI());
		assertEquals(BASE_URL + "/arrangements/" + ARRANGEMENT_ID_VALUE_ENCODED + ".json", argument.getValue().getRequestURL().toString());
	}

	@Test
	public void testChangePostToGetWithTwoTemplateValues() throws IOException, ServletException {
		parameters.put("arrangementId", ARRANGEMENT_ID_VALUE);
		parameters.put("beneficiaryId", "ACCOUNT||87654321||654321");
		parameterNames = new Vector<String>(parameters.keySet()).elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("GET");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_BENEFICIARIES_BY_ID_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_BENEFICIARIES_BY_ID_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		when(httpServletRequest.getParameter("beneficiaryId")).thenReturn(parameters.get("beneficiaryId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("GET", argument.getValue().getMethod());
		assertEquals("/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456/beneficiaries/ACCOUNT%7C%7C87654321%7C%7C654321.json", argument.getValue().getRequestURI());
		assertEquals(BASE_URL + "/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456/beneficiaries/ACCOUNT%7C%7C87654321%7C%7C654321.json", argument.getValue().getRequestURL().toString());
	}
	
	@Test
	public void testChangePostToGetWithTwoTemplateValuesAndStringOnEnd() throws IOException, ServletException {
		parameters.put("arrangementId", ARRANGEMENT_ID_VALUE);
		parameters.put("beneficiaryId", "ACCOUNT||87654321||654321");
		parameterNames = new Vector<String>(parameters.keySet()).elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("POST");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(PAYMENTS_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(PAYMENTS_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		when(httpServletRequest.getParameter("beneficiaryId")).thenReturn(parameters.get("beneficiaryId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("POST", argument.getValue().getMethod());
		assertEquals("/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456/beneficiaries/ACCOUNT%7C%7C87654321%7C%7C654321/payments.json", argument.getValue().getRequestURI());
		assertEquals(BASE_URL + "/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456/beneficiaries/ACCOUNT%7C%7C87654321%7C%7C654321/payments.json", argument.getValue().getRequestURL().toString());
	}

	@Test
	public void testChangePostToDeleteWithTemplate() throws IOException, ServletException {
		parameters.put("arrangementId", ARRANGEMENT_ID_VALUE);
		parameterNames = new Vector<String>(parameters.keySet()).elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("DELETE");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_BY_ID_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_BY_ID_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("DELETE", argument.getValue().getMethod());
		assertEquals("/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456.json", argument.getValue().getRequestURI());
		assertEquals(BASE_URL + "/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456.json", argument.getValue().getRequestURL().toString());
	}

	@Test
	public void testChangePostToPutWithTemplate() throws IOException, ServletException {
		parameters.put("arrangementId", ARRANGEMENT_ID_VALUE);
		parameterNames = new Vector<String>(parameters.keySet()).elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("PUT");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_BY_ID_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_BY_ID_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("PUT", argument.getValue().getMethod());
		assertEquals("/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456.json", argument.getValue().getRequestURI());
		assertEquals(BASE_URL + "/arrangements/ACCOUNT%7C%7C12345678%7C%7C123456.json", argument.getValue().getRequestURL().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testChangePostToGetWithMissingTemplateValue() throws IOException, ServletException {
		parameterNames = new Vector<String>().elements();
		when(httpServletRequest.getParameterNames()).thenReturn(parameterNames);
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("GET");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_BY_ID_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_BY_ID_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("GET", argument.getValue().getMethod());
		assertEquals(ARRANGEMENTS_URI, argument.getValue().getRequestURI());
		assertEquals(BASE_URL + ARRANGEMENTS_URI, argument.getValue().getRequestURL().toString());
	}

	@Test
	public void testChangePostToGetWithoutTemplate() throws IOException, ServletException {
		when(httpServletRequest.getParameter(RestfulUrlMethodTransformer.DEFAULT_METHOD_PARAM)).thenReturn("GET");
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("GET", argument.getValue().getMethod());
		assertEquals(ARRANGEMENTS_URI, argument.getValue().getRequestURI());
		assertEquals(BASE_URL + ARRANGEMENTS_URI, argument.getValue().getRequestURL().toString());
	}

	@Test
	public void testNothingChangedWithoutMethodParam() throws IOException, ServletException {
		when(httpServletRequest.getMethod()).thenReturn("POST");
		when(httpServletRequest.getRequestURI()).thenReturn(ARRANGEMENTS_URI);
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(ARRANGEMENTS_URL));
		when(httpServletRequest.getParameter("arrangementId")).thenReturn(parameters.get("arrangementId"));
		restfulUrlMethodFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		ArgumentCaptor<HttpServletRequest> argument = ArgumentCaptor.forClass(HttpServletRequest.class);
		verify(filterChain).doFilter(argument.capture(), any(HttpServletResponse.class));

		assertEquals("POST", argument.getValue().getMethod());
		assertEquals(ARRANGEMENTS_URI, argument.getValue().getRequestURI());
		assertEquals(ARRANGEMENTS_URL, argument.getValue().getRequestURL().toString());
	}

}
