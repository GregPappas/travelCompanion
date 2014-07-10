package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.TransactionType;

public class SessionHelperTest {

	@Test
	public void sessionHelperConstructorWithRequestShouldSetTheRequest()
	{
		//setup
			HttpServletRequest request = mock(HttpServletRequest.class);       
		 //act
		 	SessionHelper sessionHelper = new SessionHelper(request);
		//verify
			assertEquals(request,sessionHelper.getRequest());
	}
	
	
	@Test
	public void addVariableToSessionShouldRunSetAttributMethodInSession() 
	{
	//setup
		HttpServletRequest request = mock(HttpServletRequest.class);       
	 	HttpSession session = mock(HttpSession.class);
	 	SessionHelper sessionHelper = new SessionHelper();
	 	sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
	 //act
		sessionHelper.addVariableToSession("sessionVariable", "sessionKey");
	//verify
		Mockito.verify(session).setAttribute("sessionKey", "sessionVariable");
	}
	
	@Test
	public void setRequestShouldSetTheHttpServletRequest()
	{
		//setup
		HttpServletRequest request = mock(HttpServletRequest.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		//act
		sessionHelper.setRequest(request);
		//verify
		Mockito.verify(sessionHelper).setRequest(request);
	}
	
	@Test
	public void getRequestShouldgetTheHttpServletRequest()
	{
		//setup
		SessionHelper sessionHelper = mock(SessionHelper.class);
		//act
		sessionHelper.getRequest();
		//verify
		Mockito.verify(sessionHelper).getRequest();
	}
	
	@Test
	public void returnVariableToSessionShouldRunGetAttributMethodInSession() 
	{
	//setup
		HttpServletRequest request = mock(HttpServletRequest.class);       
	 	HttpSession session = mock(HttpSession.class);
	 	SessionHelper sessionHelper = new SessionHelper();
	 	sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
	 //act
		sessionHelper.returnSessionVariable("userId");
	//verify
		Mockito.verify(session).getAttribute("userId");
	}
	
	
	@Test
	public void returnSessionVariableShouldReturnNullIfNoUserLoggedIn()
	{
		//setup
	 	SessionHelper sessionHelper = new SessionHelper();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("userId")).thenReturn(null);
		//act
		String result = sessionHelper.returnSessionVariable("userId");
		//verify
		assertEquals(null, result);
	}
	
	
	@Test
	public void returnSessionVariablemShouldReturnUserIdIfUserLoggedIn()
	{
		//setup
		SessionHelper sessionHelper = new SessionHelper();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("userId")).thenReturn("69877");
		//act
		String result = sessionHelper.returnSessionVariable("userId");
		//verify
		assertEquals("69877", result);
	}
	
	@Test
	public void getUserIdShouldReturnUserIdIfUserLoggedIn()
	{
		//setup
		SessionHelper sessionHelper = new SessionHelper();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("userId")).thenReturn("69877");
		//act
		String result = sessionHelper.getUserId(request);
		//verify
		assertEquals("69877", result);
	}
	
	
	@Test
	public void getSessionShouldReturnTheSession()
	{
	//setup
		HttpServletRequest request = mock(HttpServletRequest.class);
		SessionHelper sessionHelper = new SessionHelper();
		sessionHelper.setRequest(request);
	//act
		sessionHelper.getSession();
	//verify
		Mockito.verify(request).getSession(true);
	}

	@Test
	public void returnTransactionSessionVariableShouldReturnTransactionIfUserLoggedIn()
	{
		//setup
		SessionHelper sessionHelper = new SessionHelper();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		//setting up payment		 	
	 		Transaction transaction =new Transaction();
			CurrencyAmount currencyAmount = new CurrencyAmount();
			BigDecimal decimal =  new BigDecimal(25).setScale(2, BigDecimal.ROUND_HALF_UP);
			currencyAmount.setAmount(decimal);
			currencyAmount.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);		
			transaction.setAmount(currencyAmount);
				Date newDate;
				try {
					newDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2010");
					transaction.setDate(newDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			transaction.setDescription("payment");
			transaction.setId("1234");
				CurrencyAmount runningBalance = new CurrencyAmount();
				BigDecimal newDecimal =  new BigDecimal(Double.valueOf("1234")).setScale(2, BigDecimal.ROUND_HALF_UP);
				runningBalance.setAmount(newDecimal);
				runningBalance.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
			transaction.setRunningBalance(runningBalance);
			transaction.setType(TransactionType.TRANSFER);
			Mockito.when(session.getAttribute("transactionId")).thenReturn(transaction);
		//act
		Transaction result = sessionHelper.returnTransactionSessionVariable("transactionId");
		//verify
		assertEquals("1234", result.getId());
	}
	
	@Test
	public void returnBeneficiarySessionVariableShouldReturnBeneficiaryIfUserLoggedIn()
	{
		//setup
		SessionHelper sessionHelper = new SessionHelper();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		sessionHelper.setRequest(request);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		//setting up beneficiary    
		Beneficiary beneficiary = new Beneficiary();
		 	String arrangementId = "61345555132535"; // arrangement id of credit card account belonging to the selected user
		 	String sortCode = "sd2530";
		 	String accountNumber = "613455551";
		 	beneficiary.setAccountNumber(accountNumber);
		 	beneficiary.setId(arrangementId);
		 	beneficiary.setSortCode(sortCode);

		 Mockito.when(session.getAttribute("beneficiaryId")).thenReturn(beneficiary);
		//act
		Beneficiary result = sessionHelper.returnBeneficiarySessionVariable("beneficiaryId");
		//verify
		assertEquals(beneficiary.getId(), result.getId());
	}

	@Test
	public void setBeneficiaryShouldSetTheBeneficiary()
	{
		//setup
		HttpServletRequest request = mock(HttpServletRequest.class);
		SessionHelper sessionHelper = new SessionHelper();
		sessionHelper.setRequest(request);
		HttpSession session = mock(HttpSession.class);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		//setting up beneficiary    
			Beneficiary beneficiary = new Beneficiary();
				 	String arrangementId = "61345555132535"; // arrangement id of credit card account belonging to the selected user
				 	String sortCode = "sd2530";
				 	String accountNumber = "613455551";
				 	beneficiary.setAccountNumber(accountNumber);
				 	beneficiary.setId(arrangementId);
				 	beneficiary.setSortCode(sortCode);
		//act
		sessionHelper.setBeneficiary(beneficiary);
		//verify
		Mockito.verify(session).setAttribute(beneficiary.getId(), beneficiary);
	}
	@Test
	public void setTransactionShouldSetTheTransaction()
	{
		//setup
		HttpServletRequest request = mock(HttpServletRequest.class);
		SessionHelper sessionHelper = new SessionHelper();
		sessionHelper.setRequest(request);
		HttpSession session = mock(HttpSession.class);
		Mockito.when(sessionHelper.getSession()).thenReturn(session);
		//setting up payment		 	
	 		Transaction transaction =new Transaction();
			CurrencyAmount currencyAmount = new CurrencyAmount();
			BigDecimal decimal =  new BigDecimal(25).setScale(2, BigDecimal.ROUND_HALF_UP);
			currencyAmount.setAmount(decimal);
			currencyAmount.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);		
			transaction.setAmount(currencyAmount);
				Date newDate;
				try {
					newDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2010");
					transaction.setDate(newDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			transaction.setDescription("payment");
			transaction.setId("1234");
				CurrencyAmount runningBalance = new CurrencyAmount();
				BigDecimal newDecimal =  new BigDecimal(Double.valueOf("1234")).setScale(2, BigDecimal.ROUND_HALF_UP);
				runningBalance.setAmount(newDecimal);
				runningBalance.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
			transaction.setRunningBalance(runningBalance);
			transaction.setType(TransactionType.TRANSFER);
		//act
		sessionHelper.setTransaction(transaction);
		//verify
		Mockito.verify(session).setAttribute(transaction.getId(), transaction);

	}
	@Test
	public void returnBeneficiarySessionVariableShouldReturnNullIfBeneficiaryIsNotStored()
	{
		//setup
			SessionHelper sessionHelper = new SessionHelper();
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpSession session = mock(HttpSession.class);
			sessionHelper.setRequest(request);
			Mockito.when(sessionHelper.getSession()).thenReturn(session);
			Mockito.when(sessionHelper.getSession().getAttribute("something")).thenReturn(null);
			
		//act
			Beneficiary result = sessionHelper.returnBeneficiarySessionVariable("something");
		//verify
			assertNull(result);
	}
	
	@Test
	public void returnTransactionSessionVariableShouldReturnNullIfBeneficiaryIsNotStored()
	{
		//setup
			SessionHelper sessionHelper = new SessionHelper();
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpSession session = mock(HttpSession.class);
			sessionHelper.setRequest(request);
			Mockito.when(sessionHelper.getSession()).thenReturn(session);
			Mockito.when(sessionHelper.getSession().getAttribute("something")).thenReturn(null);
			
		//act
			Transaction result = sessionHelper.returnTransactionSessionVariable("something");
		//verify
			assertNull(result);
	}

}


