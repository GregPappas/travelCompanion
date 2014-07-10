package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementStateType;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.BeneficiaryObject;
import com.lloydstsb.rest.v1.demo.BeneficiariesServiceImpl;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.Page;

public class BeneficiaryServiceTest {

	private String userId = "69000";
	ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
	BeneficiaryDataIpsum beneficiaries = new BeneficiaryDataIpsum();
	
	@Before
	public void initialise()
	{
		arrangementData.instantiateExchangeLockCustomersArrangements();
		beneficiaries.generateHashMapOfBeneficiaries();		
		
	}
	
	@Test
	public void GetBeneficiariesShouldReturnResponse200IfNameAndAccountNumberAndSortCodeAreNull()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		String sortCode=null;
		String accountNumber=null;
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		//verify
		assertEquals(200,returnedResponse.getStatus());
	}
	
	@Test
	public void GetBeneficiariesShouldIgnoreBeneficiariesOfDisabledArrangements()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		ArrayList<ArrangementWrapper> arrangementWrappers=arrangementData.getArrangementWrappers(userId);
		
		String sortCode=null;
		String accountNumber=null;
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		arrangementWrappers.get(0).setState(ArrangementStateType.DISABLED);
		Response returnedResponseWithDisabled=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);

		//verify
		Page<BeneficiaryObject> beneficiaries=(Page<BeneficiaryObject>)returnedResponse.getEntity();
		Page<BeneficiaryObject> beneficiariesDisabled=(Page<BeneficiaryObject>)returnedResponseWithDisabled.getEntity();
		boolean check=beneficiaries.getItems().size() > beneficiariesDisabled.getItems().size();
		
		assertEquals(200,returnedResponse.getStatus());
		assertTrue(check);
		
	}
	
	@Test
	public void GetBeneficiariesShouldIgnoreBeneficiariesOfDormantArrangements()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		ArrayList<ArrangementWrapper> arrangementWrappers=arrangementData.getArrangementWrappers(userId);
		
		String sortCode=null;
		String accountNumber=null;
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		arrangementWrappers.get(0).setState(ArrangementStateType.DORMANT);
		Response returnedResponseWithDisabled=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);

		//verify
		Page<BeneficiaryObject> beneficiaries=(Page<BeneficiaryObject>)returnedResponse.getEntity();
		Page<BeneficiaryObject> beneficiariesDisabled=(Page<BeneficiaryObject>)returnedResponseWithDisabled.getEntity();
		boolean check=beneficiaries.getItems().size() > beneficiariesDisabled.getItems().size();
		
		assertEquals(200,returnedResponse.getStatus());
		assertTrue(check);
		
	}
	
	@Test
	public void GetBeneficiariesShouldReturnResponse200IfNameIsNotNull()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		String sortCode=null;
		String accountNumber=null;
		String name="Andrei";
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		//verify
		assertEquals(200,returnedResponse.getStatus());
	}
	
	@Test
	public void GetBeneficiariesShouldReturnResponse404IfNameIsNullAndAccountNumberAndSortCodeAreGivenButAccountNumberIsInvalid()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		String sortCode="123456";
		String accountNumber="1234";
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		//verify
		assertEquals(404,returnedResponse.getStatus());
	}
	
	
	@Test
	public void GetBeneficiariesShouldReturnResponse404IfNameAndAccountNumberAreNullAndSortCodeIsGiven()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		String sortCode="123456";
		String accountNumber=null;
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		//verify
		assertEquals(404,returnedResponse.getStatus());
	}
	
	@Test
	public void GetBeneficiariesShouldReturnResponse400IfNameIsNullAndAccountNumberAndSortCodeAreGivenButSortCodeIsInvalid()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		String sortCode="1234";
		String accountNumber="12345678";
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		//verify
		assertEquals(400,returnedResponse.getStatus());
	}
	
	
	@Test
	public void GetBeneficiariesShouldReturnResponse400IfNameAndSortCodeAreNullAndAccountNumberIsGiven()
	{
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		String sortCode=null;
		String accountNumber="12345678";
		String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

		//Act
		Response returnedResponse=beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, name, pageIndex, size);
		//verify
		assertEquals(400,returnedResponse.getStatus());
	}
	
	@Test
	public void GetBeneficiariesShouldReturnResponse200AndPageOfBeneficiariesContainingTheNameString()
	{	
		//SetUp
			BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
			BeneficiaryDataIpsum beneficiaryData=mock(BeneficiaryDataIpsum.class);
			HttpServletRequest request = mock(HttpServletRequest.class);
			HttpServletResponse response = mock(HttpServletResponse.class);
			SessionHelper sessionHelper = mock(SessionHelper.class);
			String name="Vodafone";
			int pageIndex=0;
			int size=50;
			
			beneficiariesService.beneficiaryData = beneficiaryData;
			beneficiariesService.setSessionHelper(sessionHelper);
			Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);
			ArrayList<Beneficiary> unsortedList = new ArrayList<Beneficiary>();
			unsortedList.add(new Beneficiary());

		//Act
			
			Mockito.when(beneficiaryData.getAllBeneficiariesBelongingToUser(userId)).thenReturn(unsortedList);
			beneficiariesService.getBeneficiaries(request, response, null, null, name, pageIndex, size);
		//Verify			
			Mockito.verify(beneficiaryData).addBeneficiariesToListFromNameSearch(userId, name);
	}
	
	@Test
	public void GetBeneficiariesShouldReturnResponse200AndPageOfBeneficiariesContainingAccountAndSortCodeString()
	{	
		//SetUp
		BeneficiariesServiceImpl beneficiariesService= new BeneficiariesServiceImpl();
		BeneficiaryDataIpsum beneficiaryData=mock(BeneficiaryDataIpsum.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		SessionHelper sessionHelper = mock(SessionHelper.class);
		beneficiariesService.beneficiaryData = beneficiaryData;
		String sortCode="222222";
		String accountNumber="22222222";
		//String name=null;
		int pageIndex=0;
		int size=50;
		beneficiariesService.setSessionHelper(sessionHelper);
		Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);
		ArrayList<Beneficiary> unsortedList = new ArrayList<Beneficiary>();
			Beneficiary beneficiary = new Beneficiary();
			beneficiary.setAccountNumber("22222222");
			beneficiary.setSortCode("222222");
			beneficiary.setEnabled(true);
			beneficiary.setReferenceAllowed(false);
			beneficiary.setName("Nosmo King");
			beneficiary.setId("22222222222222");
			unsortedList.add(beneficiary);
		
	//Act
		Mockito.when(beneficiaryData.getAllBeneficiariesBelongingToUser(userId)).thenReturn(unsortedList);
		beneficiariesService.getBeneficiaries(request, response, sortCode, accountNumber, null, pageIndex, size);
	//Verify
		
		Mockito.verify(beneficiaryData).addBeneficiariesToListFromAccountAndSortCodeSearch(userId, sortCode, accountNumber);
	}
}
