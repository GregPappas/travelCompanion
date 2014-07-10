package com.lloydstsb.rest.v1;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.lloydstsb.rest.v1.valueobjects.Customer;
import com.lloydstsb.rest.v1.valueobjects.EnrolmentAuthentication;

@RunWith(Arquillian.class)
public class CustomerTest extends BaseTest {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final JsonFactory factory = mapper.getJsonFactory();

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml")).addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/liquibase.xml"));
	}

	@Test
	@Ignore
	public void shouldModifyUserDetails() throws Exception {
		this.logon();
		
		// get customer details
		Response getResponse = doGet(contextPath + CUSTOMER + JSON_EXTENSION);
		assertEquals(200, getResponse.code);
		
		JsonParser jsonParser = factory.createJsonParser(getResponse.responseBody);

		Customer customer = mapper.readValue(jsonParser, new TypeReference<Customer>() {
		});
		
		// should not start equal
		assertFalse("someguy@gmail.com".equals(customer.getEmail()));

		Map<String, String> params = new HashMap<String, String>();
		params.put("email", "someguy@gmail.com");
		params.put("_method", "PATCH");

		Response response = doPost(contextPath + CUSTOMER + JSON_EXTENSION, params);

		// update should not have a response body
		assertEquals(204, response.code);

		// now get the user again, email should have changed
		getResponse = doGet(contextPath + CUSTOMER + JSON_EXTENSION);

		assertEquals(200, getResponse.code);
		jsonParser = factory.createJsonParser(getResponse.responseBody);

		customer = mapper.readValue(jsonParser, new TypeReference<Customer>() {
		});

		// should now be equal
		assertEquals("someguy@gmail.com", customer.getEmail());
	}
	
	@Test
	public void testCaptureAliasNamePost() throws Exception {
		this.logon();
		Map<String, String> params = new HashMap<String, String>();
		params.put("aliasName", "MyPhone");

		Response response = doPost(contextPath + ENROLMENT + "/alias" + JSON_EXTENSION, params);

		// Authentication failure would have resulted in a 401
		assertEquals(Status.CREATED.getStatusCode(), response.code);
		
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);

		EnrolmentAuthentication phoneNumbers = mapper.readValue(jsonParser, new TypeReference<EnrolmentAuthentication>() {});
		assertNotNull(phoneNumbers.getPhoneNumbers());
		assertEquals(2, phoneNumbers.getPhoneNumbers().size());
	}

}
