package com.lloydstsb.rest.v1;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.lloydstsb.rest.v1.valueobjects.Arrangements;
import com.lloydstsb.rest.v1.valueobjects.AuthenticationType;
import com.lloydstsb.rest.v1.valueobjects.Beneficiaries;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;
import com.lloydstsb.rest.v1.valueobjects.Payment;
import com.lloydstsb.rest.v1.valueobjects.PaymentConfirmation;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ArrangementType;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CurrentAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.DepositAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.NamedAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ProductArrangement;

@RunWith(Arquillian.class)
public class ArrangementTest extends BaseTest {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final JsonFactory factory = mapper.getJsonFactory();

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
				.addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/liquibase.xml"));
	}

	// GET ARRANGEMENTS

	@Test
	public void testArrangements() throws Exception {
		this.logon();

		Response response = doGet(contextPath + ARRANGEMENTS + JSON_EXTENSION);

		assertEquals(200, response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);

		Arrangements arrangements = mapper.readValue(jsonParser, new TypeReference<Arrangements>() { });

		assertEquals(200, response.code);
		ProductArrangement firstArrangement = (ProductArrangement) arrangements.getArrangements().getItems().get(0);

		assertEquals("ACCOUNT||12345678||200001", firstArrangement.getId());
		assertEquals(ArrangementType.CURRENT_ACCOUNT, firstArrangement.getType());

		NamedAccount namedAccount = (NamedAccount) firstArrangement;

		assertEquals("Joint", namedAccount.getAccountName());

		DepositAccount depositAccount = (DepositAccount) namedAccount;

		assertEquals("200001", depositAccount.getSortCode());
		assertEquals("12345678", depositAccount.getAccountNumber());
		assertEquals("150", depositAccount.getBalance().getAmount().toString());
		assertEquals("GBP", depositAccount.getBalance().getCurrency().toString());
	}

	@Test
	public void testArrangementsNotAuthorised() throws Exception {
		// ensure not logged on
		client = new DefaultHttpClient();

		Response response = doGet(contextPath + ARRANGEMENTS + JSON_EXTENSION);

		assertEquals(401, response.code);

	}

	// GET ARRANGEMENTS BY ID

	@Test
	public void testArrangementById() throws Exception {
		this.logon();

		Response response = doGet(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + JSON_EXTENSION);

		assertEquals(200, response.code);
		
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);
		CurrentAccount account = mapper.readValue(jsonParser, CurrentAccount.class);

		assertEquals("ACCOUNT||12345678||200001", account.getId());
		assertEquals(ArrangementType.CURRENT_ACCOUNT, account.getType());
		assertEquals("Joint", account.getAccountName());
		assertEquals("200001", account.getSortCode());
		assertEquals("12345678", account.getAccountNumber());
		assertEquals("150", account.getBalance().getAmount().toString());
		assertEquals("GBP", account.getBalance().getCurrency().toString());
	}

	@Test
	public void testArrangementByIdNotFound() throws Exception {
		this.logon();

		Response response = doGet(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||DUMMY" + JSON_EXTENSION);

		assertEquals(404, response.code);

	}

	@Test
	public void testArrangementByIdNotAuthorised() throws Exception {
		// ensure not logged on
		client = new DefaultHttpClient();

		Response response = doGet(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||DUMMY" + JSON_EXTENSION);

		assertEquals(401, response.code);

	}

	// GET BENEFICIARIES BY ARRANGEMENT ID

	@Test
	public void testArrangementByIdBeneficiaries() throws Exception {
		this.logon();

		Response response = doGet(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + BENEFICIARIES + JSON_EXTENSION);

		assertEquals(200, response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);
		
		Beneficiaries beneficiaries = mapper.readValue(jsonParser, new TypeReference<Beneficiaries>() {
		});

		assertEquals(200, response.code);
		assertEquals(2, beneficiaries.getBeneficiaries().getItems().size());
	}
	
	@Test
	public void testArrangementByIdBeneficiariesWithPost() throws Exception {
		this.logon();

		Map<String, String> data = new HashMap<String, String>();
		data.put("arrangementId", "ACCOUNT||12345678||200001");
		data.put("_method", "GET");
		Response response = doPost(contextPath + ARRANGEMENTS + "/%7BarrangementId%7D" + BENEFICIARIES + JSON_EXTENSION, data);

		assertEquals(200, response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);
		
		Beneficiaries beneficiaries = mapper.readValue(jsonParser, new TypeReference<Beneficiaries>() {
		});

		assertEquals(200, response.code);
		assertEquals(2, beneficiaries.getBeneficiaries().getItems().size());
	}

	@Test
	public void testArrangementByIdBeneficiariesNotFound() throws Exception {
		this.logon();

		Response response = doGet(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||DUMMY" + BENEFICIARIES + JSON_EXTENSION);

		assertEquals(404, response.code);
	}

	@Test
	public void testArrangementByIdBeneficiariesNotAuthorised() throws Exception {
		// ensure not logged on
		client = new DefaultHttpClient();

		Response response = doGet(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + BENEFICIARIES + JSON_EXTENSION);

		assertEquals(401, response.code);
	}
	
	@Test
	public void testPayments() throws Exception {
		this.logon();

		Map<String, String> data = new HashMap<String, String>();
		data.put("date", ISODateTimeFormat.dateTime().print(new Date().getTime()));
		data.put("amount", "100.50");
		data.put("currencyCode", "GBP");
		Response response = doPost(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + BENEFICIARIES + 
				"/ACCOUNT||87654321||112233" + PAYMENTS + JSON_EXTENSION, data);

		assertEquals(201, response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);

		Payment payment = mapper.readValue(jsonParser, Payment.class);

		assertNotNull(payment.getPaymentId());
		assertEquals(AuthenticationType.PASSWORD, payment.getAuthenticationType());
		assertEquals(Boolean.TRUE, payment.isFasterPaymentOffered());
		assertEquals(Boolean.TRUE, payment.isProcessAsFasterPayment());
		
		
		data = new HashMap<String, String>();
		data.put("password", "password");
		response = doPut(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + BENEFICIARIES + 
				"/ACCOUNT||87654321||112233" + PAYMENTS + "/" + payment.getPaymentId() + JSON_EXTENSION, data);
		assertEquals(200, response.code);

		jsonParser = factory.createJsonParser(response.responseBody);

		PaymentConfirmation paymentConfirm = mapper.readValue(jsonParser, PaymentConfirmation.class);		
		assertNotNull(paymentConfirm.getActualPaymentDate());
	}
	
	@Test
	public void testTransfers() throws Exception {
		this.logon();

		Map<String, String> data = new HashMap<String, String>();
		data.put("amount", "100.50");
		data.put("currencyCode", "GBP");
		data.put("targetArrangementId", "ACCOUNT||87654321||200002");
		Response response = doPost(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + TRANSFERS + JSON_EXTENSION, data);

		assertEquals(201, response.code);
		//JsonParser jsonParser = factory.createJsonParser(response.responseBody);
	}
	
	@Test
	public void testCreateBeneficiary() throws Exception {
		this.logon();

		Map<String, String> data = new HashMap<String, String>();
		data.put("sortCode", "334455");
		data.put("accountNumber", "98765432");
		data.put("name", "Some person");
		data.put("reference", "Some ref");
		
		Response response = doPost(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + BENEFICIARIES 
				+ JSON_EXTENSION, data);

		assertEquals(201, response.code);
		JsonParser jsonParser = factory.createJsonParser(response.responseBody);

		NewBeneficiary ben = mapper.readValue(jsonParser, NewBeneficiary.class);

		assertNotNull(ben.getTransactionId());
		assertEquals(AuthenticationType.PASSWORD, ben.getAuthenticationType());		
		
		data = new HashMap<String, String>();
		data.put("password", "password");
		response = doPut(contextPath + ARRANGEMENTS + "/ACCOUNT||12345678||200001" + BENEFICIARIES + "/" + 
				ben.getTransactionId() + JSON_EXTENSION, data);
		assertEquals(200, response.code);
	}	
}
