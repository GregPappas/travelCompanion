package com.lloydstsb.rest.v1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArrangementTest.class);

	protected static final String JSON_EXTENSION = ".json";
	protected static final String APPLICATION_PATH = "api/v1";
	protected static final String AUTHENTICATION = APPLICATION_PATH + "/session";
	protected static final String AUTHENTICATION_MEMORABLE_INFO = AUTHENTICATION + "/memorablecharacters";
	protected static final String PHONE_AUTHENTICATION = APPLICATION_PATH + "/authentication/eia";
	protected static final String ARRANGEMENTS = APPLICATION_PATH + "/arrangements";
	protected static final String CUSTOMER = APPLICATION_PATH + "/customer";
	protected static final String BENEFICIARIES = "/beneficiaries";
	protected static final String ENROLMENT = CUSTOMER + "/enrolment";
	
	protected static final String SEARCH_BENEFICIARIES = APPLICATION_PATH + BENEFICIARIES;
	
	protected static final String PAYMENTS = "/payments";
	protected static final String TRANSFERS = "/transfers";

	protected static final String MAIN_SRC = "src/test";
	protected static final String WEBAPP_SRC = MAIN_SRC + "/webapp";

	@ArquillianResource
	protected URL contextPath;

	protected static HttpClient client = new DefaultHttpClient();

	@Before
	public void setUp() throws Exception {
		contextPath = new URL("http://localhost:9090/test/");
		client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
	}

	@After
	public void tearDown() throws Exception {

	}

	protected Response doPut(String url) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		return doPut(url, Collections.<String, String> emptyMap());
	}
	protected Response doPut(String url, Map<String, String> params) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		url = url.replaceAll("\\|", "%7C");
		HttpPut httpPut = new HttpPut(new URI(url));
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
		}

		httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		HttpResponse httpResponse = client.execute(httpPut);

		Response response = new Response(httpResponse);

		LOGGER.debug("Response : {}", response);

		return response;		
	}
	
	protected Response doGet(String url) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		return doGet(url, Collections.<String, String> emptyMap());
	}

	protected Response doGet(String url, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		url = url.replaceAll("\\|", "%7C");

		HttpGet httpRequest = new HttpGet(new URI(url));

		for (String key : headers.keySet()) {
			httpRequest.addHeader(key, headers.get(key));
		}

		HttpResponse httpResponse = client.execute(httpRequest);

		Response response = new Response(httpResponse);

		LOGGER.debug("Response : {}", response);

		return response;
	}

	protected Response doPost(String url, Map<String, String> params) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		return doPost(url, params, Collections.<String, String> emptyMap());
	}

	protected Response doPost(String url, String body, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httpRequest = buildRequest(url, headers);
		httpRequest.setEntity(new StringEntity(body));

		return processRequest(httpRequest);
	}

	protected HttpPost buildRequest(String url, Map<String, String> headers) throws URISyntaxException {
		url = url.replaceAll("\\|", "%7C");

		HttpPost httpRequest = new HttpPost(new URI(url));

		for (String key : headers.keySet()) {
			httpRequest.setHeader(key, headers.get(key));
		}

		return httpRequest;
	}

	protected Response doPost(String url, Map<String, String> params, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httpRequest = buildRequest(url, headers);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
		}

		httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		return processRequest(httpRequest);
	}

	protected Response processRequest(HttpPost httpRequest) throws ClientProtocolException, IOException {
		HttpResponse httpResponse = client.execute(httpRequest);

		Response response = new Response(httpResponse);

		LOGGER.debug("Response : {}", response);

		return response;
	}

	protected void logon() throws Exception {
		Map<String, String> authenticationParams = new HashMap<String, String>();
		authenticationParams.put("username", "874774678");
		authenticationParams.put("password", "aaaaaaaaaaaaaaa");

		doPost(contextPath + AUTHENTICATION + JSON_EXTENSION, authenticationParams);
		doGet(contextPath + AUTHENTICATION_MEMORABLE_INFO + JSON_EXTENSION);

		Map<String, String> headers = new HashMap<String, String>();
		//headers.put("Content-Type", "application/json");
		
		authenticationParams = new HashMap<String, String>();
		authenticationParams.put("characters", "t35");

		doPost(contextPath + AUTHENTICATION_MEMORABLE_INFO + JSON_EXTENSION, authenticationParams, headers);
	}

	protected class Response {

		public int code;
		public String responseBody;
		private Header[] headers;

		public Response(HttpResponse response) {
			this.code = response.getStatusLine().getStatusCode();
			headers = response.getAllHeaders();

			HttpEntity entity = response.getEntity();
			try {
				responseBody = IOUtils.toString(entity.getContent());
			} catch (Exception e) {
				responseBody = null;
				LOGGER.error(e.getMessage());
			}

			System.out.println(this);
		}

		public Header[] getHeaders() {
			return headers;
		}

		@Override
		public String toString() {
			return "Response [code=" + code + " responseBody=" + responseBody + "]";
		}
	}
}
