package com.lloydstsb.rest.v1;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.http.Header;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.lloydstsb.rest.v1.valueobjects.PhoneAuthentication;
import com.lloydstsb.rest.v1.valueobjects.PhoneAuthenticationStatus;

@RunWith(Arquillian.class)
public class AuthenticationTest extends BaseTest {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final JsonFactory factory = mapper.getJsonFactory();

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/liquibase.xml"));
	}

	@Test
	public void testAuthenticationPost() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "874774678");
		params.put("password", "aaaaaaaaaaaaaaa");

		Response response = doPost(contextPath + AUTHENTICATION + JSON_EXTENSION, params);

		// Authentication failure would have resulted in a 401
		assertEquals(Status.CREATED.getStatusCode(), response.code);
	}

	@Test
	public void testAuthenticationMemInfoGet() throws Exception {
		// ensure session exists
		testAuthenticationPost();

		Response response = doGet(contextPath + AUTHENTICATION_MEMORABLE_INFO + JSON_EXTENSION);

		JsonParser jsonParser = factory.createJsonParser(response.responseBody);
		JsonNode jsonNode = mapper.readTree(jsonParser);

		assertEquals(Status.OK.getStatusCode(), response.code);

		assertEquals(1, jsonNode.get("indexes").get(0).asInt());
		assertEquals(2, jsonNode.get("indexes").get(1).asInt());
		assertEquals(3, jsonNode.get("indexes").get(2).asInt());
	}

	@Test
	public void testAuthenticationMemInfoPost() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "874774678");
		params.put("password", "aaaaaaaaaaaaaaa");

		// ensure we have a session first...
		Response response = doPost(contextPath + AUTHENTICATION + JSON_EXTENSION, params);
		String cookies = findHeader("Set-Cookie", response);

		assertEquals("Could not get redirect cookie", Status.CREATED.getStatusCode(), response.code);
		assertNotNull("Did not get a redirect cookie", cookies);

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookies);
		response = doGet(contextPath + AUTHENTICATION_MEMORABLE_INFO + JSON_EXTENSION, headers);

		assertEquals("Could not get memorable into", Status.OK.getStatusCode(), response.code);
		
		//headers.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED);

		params = new HashMap<String, String>();
		params.put("characters", "t35");

		response = doPost(contextPath + AUTHENTICATION_MEMORABLE_INFO + JSON_EXTENSION, params, headers);

		assertEquals(Status.CREATED.getStatusCode(), response.code);
	}

	private String findHeader(String name, Response response) {
		for (Header header : response.getHeaders()) {
			if (header.getName().equals(name)) {
				return header.getValue();
			}
		}

		return null;
	}

	@Test
	public void testInitiatePhoneAuthenticationPost() throws Exception {
		this.logon();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("phoneNumberType", "Mobile");
		Response response = doPost(contextPath + PHONE_AUTHENTICATION + "/SomeTransactionId" + JSON_EXTENSION, params);
		assertEquals(Status.CREATED.getStatusCode(), response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);
		PhoneAuthentication pa = mapper.readValue(jsonParser, PhoneAuthentication.class);
		assertEquals(PhoneAuthenticationStatus.CALLINITIATED, pa.getStatus());
	}
	
	@Test
	public void testGetPhoneAuthenticationStatusGet() throws Exception {
		this.logon();
		
		Response response = doGet(contextPath + PHONE_AUTHENTICATION + "/SomeTransactionId" + JSON_EXTENSION);
		assertEquals(Status.OK.getStatusCode(), response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);
		PhoneAuthentication pa = mapper.readValue(jsonParser, PhoneAuthentication.class);
		assertEquals(PhoneAuthenticationStatus.CALLSUCCESS, pa.getStatus());
	}
}
