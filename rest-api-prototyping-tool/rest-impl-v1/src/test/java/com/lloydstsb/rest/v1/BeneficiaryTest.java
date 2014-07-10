package com.lloydstsb.rest.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import com.lloydstsb.rest.v1.valueobjects.Page;
import com.lloydstsb.rest.v1.valueobjects.SearchBeneficiary;

@RunWith(Arquillian.class)
public class BeneficiaryTest extends BaseTest {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final JsonFactory factory = mapper.getJsonFactory();

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/liquibase.xml"));
	}

	@Test
	@Ignore
	public void testSearchBeneficiaries() throws Exception {
		this.logon();
		Response response = doGet(contextPath + SEARCH_BENEFICIARIES + JSON_EXTENSION + "?sortCode=873564&accountNumber=12345678");//&name=Test&size=20&page=0		

		assertEquals(200, response.code);
		
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);

		Page<SearchBeneficiary> list = mapper.readValue(jsonParser, new TypeReference<Page<SearchBeneficiary>>() { });
		assertNotNull(list);
		assertEquals(2, list.getItems().size());
	}
}
