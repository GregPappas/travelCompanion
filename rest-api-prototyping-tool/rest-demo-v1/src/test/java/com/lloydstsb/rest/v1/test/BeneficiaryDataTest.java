package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.PersistenceContainer;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

public class BeneficiaryDataTest {
	
	ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
	BeneficiaryDataIpsum beneficiaryData = new BeneficiaryDataIpsum();
	private String userId = "69000";

	@Before
	public void initialise()
	{
		arrangementData.instantiateExchangeLockCustomersArrangements();
		beneficiaryData.generateHashMapOfBeneficiaries();		
	}
	
	@Test 
	public void returnBeneficiaryWhiteListShouldReturnAStringArrayContainingDifferentBeneficiaryIds()
	{
		//setup
		//act
		ArrayList<String> whiteListArray = beneficiaryData.returnBeneficiaryWhiteList();
		boolean hasDifferentIds = !whiteListArray.get(0).equals(whiteListArray.get(1));
		//verify
		
		assertTrue(hasDifferentIds);
		assertEquals("22222222222222",whiteListArray.get(0));
	}
	
	
	
	@Test
	public void generateHashMapOfBeneficiariesShouldReturnAHashMapWithOnePayeeForEachArrangementAnd3ForTheFirst() 
	{

		//Setup
			HashMap<String, ArrayList<Beneficiary>> result = PersistenceContainer.getInstance().getBeneficiaries();
		//Verify
			assertEquals(result.get("20756882123456").size(),3);
			assertEquals(result.get("28004089123456").size(),1);
			assertEquals(result.size(),8);
	}
	
	@Test
	public void getBeneficiariesListFilteredByNameShouldReturnFilteredListOfBeneficiariesThatContainTheQueryStringInTheName()
	{
		//Setup
			String name = "Vodafone";
		//Act
			ArrayList<Beneficiary> sortedList = beneficiaryData.addBeneficiariesToListFromNameSearch(userId,name);
		//Verify
			assertEquals("Vodafone Pay Monthly",sortedList.get(0).getName());
			assertEquals(1,sortedList.size());		
	}
	
	@Test
	public void getBeneficiariesListFilteredByNameShouldReturnFilteredListOfBeneficiariesThatContainTheQueryStringInTheNameIgnoringCase()
	{
		//Setup
			String name = "vodafone";
		//Act
			ArrayList<Beneficiary> sortedList = beneficiaryData.addBeneficiariesToListFromNameSearch(userId,name);
		//Verify
			assertEquals("Vodafone Pay Monthly",sortedList.get(0).getName());
			assertEquals(1,sortedList.size());		
	}
	
	@Test
	public void getBeneficiariesListFilteredByNameShouldReturnFilteredListOfBeneficiariesThatContainSortCodeAndAccountNumber()
	{
		//Setup
			String sortCode = "222222";
			String accountNumber="22222222";
		//Act
			ArrayList<Beneficiary> sortedList = beneficiaryData.addBeneficiariesToListFromAccountAndSortCodeSearch(userId, sortCode, accountNumber);
		//Verify
			assertEquals("222222",sortedList.get(0).getSortCode());
			assertEquals(1,sortedList.size());		
	}
}
