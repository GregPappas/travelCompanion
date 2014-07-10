/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lloydstsb.rest.v1.valueobjects.Page;
import com.sun.java.util.jar.pack.Instruction.Switch;

public abstract class BaseIntegrationTest {
	/**
	 * The extension used for JSON
	 */
	protected static final String JSON_EXTENSION = ".json";
	protected static final String XML_EXTENSION = ".xml";
	protected static final String APPLICATION_PATH = "api/v1";
	protected static final String AUTHENTICATION = APPLICATION_PATH + "/session";
	protected static final String AUTHENTICATION_MEMORABLE_INFO = AUTHENTICATION + "/memorablecharacters";
	protected static final String URL_DELIMITER = "/";
	protected static final String CUSTOMER = "/customer";
	protected static final String CUSTOMER_TERMS_AND_CONDITIONS = CUSTOMER + "/termsandconditions";
	protected static final String ARRANGEMENTS = "/arrangements";
	protected static final String BENEFICIARIES = "/beneficiaries";
	protected static final String PAYMENTS = "/payments";
	protected static final String TRANSFERS = "/transfers";
	protected static final String SESSION = "/session";
	protected static final String SESSION_MEMORABLECHARACTERS = "/session/memorablecharacters";
	protected static final String SESSION_REENTER_MEMORABLECHARACTERS = "/session/reentermemorablecharacters";
	protected static final String SWITCHES = "/switches";
	protected static final String STATEMENTS = "/statements";
	protected static final String SESSION_TOKEN = "/session/token";

	protected static final String MAIN_SRC = "src/test";
	protected static final String WEBAPP_SRC = MAIN_SRC + "/webapp";

	private static final Logger LOG = LoggerFactory.getLogger(BaseIntegrationTest.class);

	private static final String USER_ID = "822226688";
	private static final String USER_PASSWORD = "aaaaaaaaaaaaaaa";
	private static final String USER_MEMORABLE_CHARACTERS = "t35ting";
	private static final String CONTEXT_PATH = "http://localhost:8080/personal/retail/internetbanking/api/v1";
	private static final int MAX_MEMORABLE_CHARACTERS_LENGTH = 3;
	private static final long SWITCH_RETRY_INTERVAL = 10000L;
	private static final long PREFETCH_WAIT = 1000L;
	private static boolean switchesAvailable;

	protected HttpContext httpContext;
	protected DefaultHttpClient client;

	private ObjectMapper mapper;
	private Channels channel;

	@Before
	public void setUp() throws Exception {
		this.httpContext = new BasicHttpContext();
		this.mapper = new ObjectMapper();

		this.client = new DefaultHttpClient();
		this.client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
	}

	protected void doPrimaryLogin(String userId) throws Exception {
		doPrimaryLogin(userId, USER_PASSWORD);
	}
	protected void doPrimaryLogin(String userId, String password) throws Exception {
		doPrimaryLogin(userId, USER_PASSWORD, null);
	}
	protected void doPrimaryLogin(String userId, String password, Channels channel) throws Exception {
		this.channel = channel;
		Map<String, String> data = new HashMap<String, String>();
		data.put("username", userId);
		data.put("password", password);

		Response response = doPost(getContextPath() + SESSION + JSON_EXTENSION, data);

		assertEquals("Log in failed for " + userId + ":" + password, HttpServletResponse.SC_CREATED, response.code);
	}

	protected MemorableCharacters getMemorableCharacters() throws Exception {
		Response response = doGet(getContextPath() + SESSION_MEMORABLECHARACTERS + JSON_EXTENSION);

		assertEquals("Getting memorable characters failed", HttpServletResponse.SC_OK, response.code);

		return readFromResponse(response, MemorableCharacters.class);
	}

	protected void sendMemorableCharacters(MemorableCharacters memorableCharacters) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < MAX_MEMORABLE_CHARACTERS_LENGTH; i++) {
			int position = memorableCharacters.getIndexes().get(i);

			stringBuilder.append(USER_MEMORABLE_CHARACTERS.charAt(position - 1));
		}

		Map<String, String> data = new HashMap<String, String>();
		data.put("characters", stringBuilder.toString());

		Response response = doPost(getContextPath() + SESSION_MEMORABLECHARACTERS + JSON_EXTENSION, data);

		assertEquals("Could not verify memorable characters " + stringBuilder.toString(), HttpServletResponse.SC_CREATED, response.code);
	}

	protected void waitForSwitchesToBecomeAvailable() throws Exception {
		if (switchesAvailable) {
			// we've been called before, abort
			return;
		}

		while (true) {
			logIn();

			Response response = doGet(getContextPath() + SWITCHES + JSON_EXTENSION);

			assertEquals(HttpServletResponse.SC_OK, response.code);

			Page<Switch> switches = readFromResponse(response, new TypeReference<Page<Switch>>() {
			});

			// Switches are stored in the user session so we must reset our
			// HttpClient if
			// we expect the switches to be present in our next request. We
			// probably want
			// to log in next anyway so we should reset it reguardless.
			setUp();

			if (switches.getItems().isEmpty()) {
				// the list was empty so the switches have not been loaded yet
				// sleep a while and try again.
				Method m = Thread.class.getDeclaredMethod("sleep", long.class);
				m.invoke(null, SWITCH_RETRY_INTERVAL);
			} else {
				// the switches were loaded so break out of the loop
				switchesAvailable = true;

				return;
			}
		}
	}

	protected void logIn() throws Exception {
		logIn(USER_ID);
	}

	protected void logIn(String userId) throws Exception {
		logIn(userId, USER_PASSWORD);
	}

	protected void logIn(String userId, String password) throws Exception {
		logIn(userId, password, null);
	}
	
	protected void logIn(String userId, String password, Channels channel) throws Exception {
		doPrimaryLogin(userId, password, channel);
		MemorableCharacters memorableCharacters = getMemorableCharacters();

		sendMemorableCharacters(memorableCharacters);

		// this is to give the prefetch time to complete..
		Method m = Thread.class.getDeclaredMethod("sleep", long.class);
		m.invoke(null, PREFETCH_WAIT);		
	}

	protected static String getContextPath() {
		String output = CONTEXT_PATH;

		// If the LUIS server port has been specified, respect it.
		if (System.getProperty("com.lloydstsb.ib.webapp.endpoint.url") != null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(System.getProperty("com.lloydstsb.ib.webapp.endpoint.url"));
			stringBuilder.append("/retail/internetbanking/api/v1");

			output = stringBuilder.toString();
		}

		return output;
	}

	protected <T> T readFromResponse(Response response, Class<T> clazz) {
		try {
			if (MediaType.APPLICATION_XML.equals(response.contentType)) {
				return JAXB.unmarshal(new ByteArrayInputStream(response.responseBody.getBytes()), clazz);
			}

			return mapper.readValue(response.responseBody, clazz);
		} catch (UnrecognizedPropertyException e) {
			fail("Could not read " + clazz.getSimpleName() + " from response body: " + response.responseBody);

			// will never get thrown because fail will abort the test
			throw new RuntimeException("Could not read " + clazz.getSimpleName() + " from response body: " + response.responseBody, e);
		} catch (Exception e) {
			LOG.error("Could not read " + clazz.getSimpleName() + " from response body: ");
			LOG.error(response.responseBody);
			LOG.error("Exception thrown: ", e);

			throw new RuntimeException("Could not read " + clazz.getSimpleName() + " from response body: " + response.responseBody, e);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> T readFromResponse(Response response, TypeReference<T> typeReference) {
		try {
			return (T) mapper.readValue(response.responseBody, typeReference);
		} catch (UnrecognizedPropertyException e) {
			fail("Could not read " + typeReference.getType() + " from response body: " + response.responseBody);

			return null;
		} catch (Exception e) {
			LOG.error("Could not read " + typeReference.getType() + " from response body: ");
			LOG.error(response.responseBody);
			LOG.error("Exception thrown: ", e);

			throw new RuntimeException("Could not read " + typeReference.getType() + " from response body: " + response.responseBody, e);
		}
	}

	protected Response doGet(String url) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpGet httpRequest = new HttpGet(new URI(encodeUrl(url)));

		return doRequest(httpRequest, null);
	}
	
	protected Response doGet(String url, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpGet httpRequest = new HttpGet(new URI(encodeUrl(url)));
		
		setHeaders(headers, httpRequest);

		return doRequest(httpRequest, null);
	}

	protected Response doPost(String url, Map<String, String> params) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httpRequest = new HttpPost(new URI(encodeUrl(url)));

		return doRequest(httpRequest, params);
	}
	
	protected Response doPost(String url, Map<String, String> params, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPost httpRequest = new HttpPost(new URI(encodeUrl(url)));
		
		setHeaders(headers, httpRequest);

		return doRequest(httpRequest, params);
	}

	protected Response doDelete(String url) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		return doDelete(url, null);
	}
	
	protected Response doDelete(String url, Map<String, String> headers) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpDelete httpRequest = new HttpDelete(new URI(encodeUrl(url)));
		
		setHeaders(headers, httpRequest);

		return doRequest(httpRequest, null);
	}

	private void setHeaders(Map<String, String> headers, HttpRequestBase httpRequest) {
		if(headers == null){
			return;
		}
		for(String key : headers.keySet()){
			httpRequest.setHeader(key, headers.get(key));
		}
	}

	protected Response doPut(String url, Map<String, String> params) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		HttpPut httpRequest = new HttpPut(new URI(encodeUrl(url)));

		return doRequest(httpRequest, params);
	}
	
	private String encodeUrl(String url) {
		String encodedUrl = url.replaceAll("\\|", "%7C");
		if (this.channel != null) {
			if (encodedUrl.indexOf('?') > -1) {
				encodedUrl += "&channel=" + this.channel;
			} else {
				encodedUrl += "?channel=" + this.channel;
			}
		}
		return encodedUrl;
	}

	private Response doRequest(HttpEntityEnclosingRequest httpRequest, Map<String, String> params) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("?");

		if(params != null){
			for (Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	
				stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		
		String paramString = stringBuilder.toString();
		paramString = paramString.substring(0, paramString.length() - 1);

		httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		return doRequest((HttpUriRequest) httpRequest, paramString);
	}

	private Response doRequest(HttpUriRequest httpRequest, String paramString) throws URISyntaxException, UnsupportedEncodingException, IOException, ClientProtocolException {
		logRequest(httpRequest, paramString);

		HttpResponse httpResponse = this.client.execute(httpRequest, this.httpContext);

		Response response = new Response(httpResponse);

		logResponse(response, httpResponse);

		return response;
	}

	private void logRequest(HttpRequest httpRequest, String paramString) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\r\n");
		stringBuilder.append("Request:\r\n");
		stringBuilder.append("\r\n");

		for (Header header : httpRequest.getAllHeaders()) {
			stringBuilder.append(header.getName());
			stringBuilder.append(": ");
			stringBuilder.append(header.getValue());
			stringBuilder.append("\r\n");
		}

		for (Cookie cookie : this.client.getCookieStore().getCookies()) {
			stringBuilder.append("Cookie: ");
			stringBuilder.append(cookie.getName());
			stringBuilder.append("=");
			stringBuilder.append(cookie.getValue());
			stringBuilder.append(";");
			stringBuilder.append("Path=");
			stringBuilder.append(cookie.getPath());
			stringBuilder.append(";Domain=");
			stringBuilder.append(cookie.getDomain());
			stringBuilder.append("\r\n");
		}

		if (httpRequest.getAllHeaders().length > 0 || this.client.getCookieStore().getCookies().size() > 0) {
			stringBuilder.append("\r\n");
		}

		stringBuilder.append(httpRequest.getRequestLine().toString());

		if (paramString != null) {
			stringBuilder.append(paramString);
			stringBuilder.append("\r\n");
		}

		LOG.info(stringBuilder.toString());
	}

	private void logResponse(Response response, HttpResponse httpResponse) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\r\n");
		stringBuilder.append("Response:");
		stringBuilder.append("\r\n");

		stringBuilder.append(httpResponse.getStatusLine().toString());
		stringBuilder.append("\r\n");

		for (Header header : httpResponse.getAllHeaders()) {
			stringBuilder.append(header.getName());
			stringBuilder.append(": ");
			stringBuilder.append(header.getValue());
			stringBuilder.append("\r\n");
		}

		stringBuilder.append("\r\n");
		stringBuilder.append(response.responseBody);
		stringBuilder.append("\r\n");

		LOG.info(stringBuilder.toString());
	}

	protected enum Channels {
		LTSBRetail,
		LTSBCommercial,
		BOSRetail,
		BOSCommercial,
		HalifaxRetail,
		VerdeRetail,
		VerdeCommercial
	}

	protected static class Response {
		public int code;
		public String contentType;
		public String responseBody;
		public Map<String, String> headers = new HashMap<String, String>();

		public Response(HttpResponse response) {
			this.code = response.getStatusLine().getStatusCode();
			
			Header[] allHeaders = response.getAllHeaders();
			for(Header header : allHeaders){
				headers.put(header.getName(), header.getValue());
			}

			Header contentType = response.getFirstHeader("Content-Type");

			if (contentType != null) {
				this.contentType = contentType.getValue();
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					this.responseBody = IOUtils.toString(entity.getContent());
				} catch (Exception e) {
					this.responseBody = null;

					throw new RuntimeException(e);
				}
			}
		}
	}
}
