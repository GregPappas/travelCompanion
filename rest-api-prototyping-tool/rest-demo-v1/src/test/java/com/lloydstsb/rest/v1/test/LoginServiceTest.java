package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.PhoneNumbersDataIpsum;
import com.lloydstsb.rest.v1.demo.LoginServiceImpl;
import com.lloydstsb.rest.v1.helpers.MiscHelper;
import com.lloydstsb.rest.v1.helpers.SessionHelper;

public class LoginServiceTest {
	private MiscHelper miscHelper = new MiscHelper();
	
	PhoneNumbersDataIpsum phoneNumbers;
	CustomersDataIpsum customers;
	@Before
	public void initialise()
	{
		phoneNumbers = new PhoneNumbersDataIpsum();
		phoneNumbers.instantiatePhoneNumbers();
		customers = new CustomersDataIpsum();
		customers.instantiateCustomers();
	}
	@After
	public void deconstruct()
	{
		phoneNumbers.getPhoneNumbersMap().clear();
		customers.getCustomers().clear();
	}
	@Test
	public void authenticateShouldReturnTrueIfIDExistsInCustomersAndReturnFalseIfNot() 
	{
		//Set up
			String ID1 = "678932";
			String ID2 = "69877";
		//Verify
			assertFalse(miscHelper.authenticate(ID1, ID1));
			assertTrue(miscHelper.authenticate(ID2, ID2));
	}
	
	@Test
	public void loginShouldReturnResponse401WhenAuthenticateFails(){
		//Set up
		 	HttpServletRequest request = mock(HttpServletRequest.class);       
		 	HttpServletResponse response = mock(HttpServletResponse.class);
		 	SessionHelper sessionHelper=mock(SessionHelper.class);
			LoginServiceImpl login=new LoginServiceImpl();
			login.setSessionHelper(sessionHelper);
		//Act
			Response returnedResponse=login.login("John", "John", request, response);			
		//Verify
			assertEquals(401, returnedResponse.getStatus());
	}
	
	@Test
	public void loginShouldReturnResponse201WhenAuthenticatePass(){
		//Set up
		 	HttpServletRequest request = mock(HttpServletRequest.class);       
		 	HttpServletResponse response = mock(HttpServletResponse.class);
		 	SessionHelper sessionHelper=mock(SessionHelper.class);
			LoginServiceImpl login=new LoginServiceImpl();
			login.setSessionHelper(sessionHelper);
		//Act
			Response returnedResponse=login.login("69877", "69877", request, response);
			
		//Verify
			Mockito.verify(sessionHelper).addVariableToSession("69877", LoginServiceImpl.USER_ID);
			assertEquals(201, returnedResponse.getStatus());
	}
   
    @Test
    public void logoutShouldReturn204WhenSessionIsInvalidated()
    {
    	//Set up
			HttpServletRequest request = mock(HttpServletRequest.class);       
    		HttpServletResponse response = mock(HttpServletResponse.class);
    		SessionHelper sessionHelper=mock(SessionHelper.class);
    		HttpSession sessionMock = mock(HttpSession.class);
    		sessionMock.setAttribute("userId", "69877");
    	    Mockito.when(sessionHelper.returnSessionVariable(LoginServiceImpl.USER_ID)).thenReturn("69877");
            Mockito.when(sessionHelper.getSession()).thenReturn(sessionMock);        
            LoginServiceImpl session= new LoginServiceImpl();
            session.setSessionHelper(sessionHelper);
    	    		
         //Act
            Response returnedResponse = session.logout(request, response);
          
        //Verify
            assertEquals(returnedResponse.getStatus(),204);
    }
}
