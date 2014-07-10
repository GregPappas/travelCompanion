package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.PhoneNumbersDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.demo.CustomerDetailsServiceImpl;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Customer;

import java.io.IOException;

public class CustomerDetailsServiceTest {

	PhoneNumbersDataIpsum phoneNumbers;
	CustomersDataIpsum customers;
	ArrangementServiceDataIpsum arrangementData;
	TransactionsDataIpsum transactionData;
	BeneficiaryDataIpsum beneficiaryData;
	@Before
	public void initialiseIpsums() throws IOException {
		phoneNumbers = new PhoneNumbersDataIpsum();
		phoneNumbers.instantiatePhoneNumbers();
		customers = new CustomersDataIpsum();
		customers.instantiateCustomers();
		arrangementData = new ArrangementServiceDataIpsum();
		arrangementData.instantiateExchangeLockCustomersArrangements();
		transactionData = new TransactionsDataIpsum();
		transactionData.readAndParseCSVFile();
		beneficiaryData = new BeneficiaryDataIpsum();
		beneficiaryData.generateHashMapOfBeneficiaries();
	}
	
	@After
	public void deconstruct()
	{	phoneNumbers.getPhoneNumbersMap().clear();
		customers.getCustomers().clear();
    	arrangementData.clearArrangementWrapperMap();
    	transactionData.getTransactionMap().clear();
    	beneficiaryData.getBeneficiaryMap().clear();
	}
	
	@Test
	public void getDetails_should_return_customer_object_which_has_same_id_as_userID() 
	{
		String userID = "69877";
		CustomerDetailsServiceImpl customerDetails = new CustomerDetailsServiceImpl();
		Customer customer = customerDetails.getDetails(userID);
		assertNotNull(customer);
		assertEquals(userID,customer.getId());
	}

	@Test
	public void getCustomerShouldReturnResponse200WhenCustomerHasDetailsAndAreSubmitted()
	{
		//Set up
		 	HttpServletRequest request = mock(HttpServletRequest.class);       
		 	HttpServletResponse response = mock(HttpServletResponse.class);
		 	SessionHelper session = mock(SessionHelper.class);
			CustomerDetailsServiceImpl customer= new CustomerDetailsServiceImpl();
			customer.setSessionHelper(session);
			Mockito.when(session.getUserId(request)).thenReturn("69877");
		//Act
		    Response returnedResponse=customer.getCustomer(request, response);
			
		//Verify
			assertEquals(returnedResponse.getStatus(),200);
			assertEquals(returnedResponse.getEntity().getClass(),Customer.class);
			assertNotNull(customer);
			String userID  = session.getUserId(request);
			assertEquals(userID,customer.getDetails(userID).getId());
	}
	
	@Test
	public void getCustomerShouldReturnResponse403SessionOrCustomerCannotBeFound()
	{
		//Set up
		 	HttpServletRequest request = mock(HttpServletRequest.class);       
		 	HttpServletResponse response = mock(HttpServletResponse.class);
		 	SessionHelper session = mock(SessionHelper.class);
			CustomerDetailsServiceImpl customer= new CustomerDetailsServiceImpl();
			customer.setSessionHelper(session);
			Mockito.when(session.getUserId(request)).thenReturn(null);
		//Act
		    Response returnedResponse=customer.getCustomer(request, response);
			
		//Verify  
			assertEquals(403,returnedResponse.getStatus());
			
	}
	@Test
	public void getUserIdShouldReturnCorrectUserId() {
		//Set up
			HttpServletRequest request = mock(HttpServletRequest.class);
			SessionHelper sessionHelper=mock(SessionHelper.class);
		    Mockito.when(sessionHelper.getUserId(request)).thenReturn("69877");//We mocked that we have an user already.		
		    CustomerDetailsServiceImpl service=new CustomerDetailsServiceImpl();
			service.setSessionHelper(sessionHelper);//Injection
		//Act
			String result=sessionHelper.getUserId(request);
		//verify
			assertEquals("69877",result);
	}
	
	@Test
	public void getUserIdShouldReturnNullIfThereIsNoUser() {
		//Set up
			HttpServletRequest request = mock(HttpServletRequest.class);
			SessionHelper sessionHelper=mock(SessionHelper.class);
		    Mockito.when(sessionHelper.getUserId(request)).thenReturn(null);//We mocked that we have not an user already.		
		    CustomerDetailsServiceImpl service=new CustomerDetailsServiceImpl();
			service.setSessionHelper(sessionHelper);//Injection
		 //Act
			String result=sessionHelper.getUserId(request);
		//verify
			assertEquals(result,null);
	}


}
