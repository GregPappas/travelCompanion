package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.PersistenceContainer;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.valueobjects.Transaction;

public class TransactionsDataIpsumTest {

	private String userId = "69000";
	private String arrangementId = "20756882123456";
	private ArrangementServiceDataIpsum arrangementData;
	private TransactionsDataIpsum transactionsData;
	private ObjectGenerator objectGenerator;

	@Before
	public void instantiateObjectsAndAssign() throws IOException {
		objectGenerator = new ObjectGenerator();
		arrangementData =new ArrangementServiceDataIpsum();
		transactionsData = new TransactionsDataIpsum();
		arrangementData.instantiateCustomersArrangements();
		transactionsData.readAndParseCSVFile();
	}
	@After
	public void deconstruct()
	{	
		transactionsData.getTransactionMap().clear();
		arrangementData.clearArrangementWrapperMap();
		objectGenerator = null;
		PersistenceContainer.getInstance().setTransactionLimitAmount(new BigDecimal(10000));
	}
	
	
	@Test
	public void readerShouldReturnCorrectArrangementIdAfterReadFromFile() {
	//SetUp
		HashMap<String, ArrayList<Transaction>> customersTransactions=PersistenceContainer.getInstance().getTransactionMap();
		ArrayList<ArrangementWrapper> arrangementList = arrangementData.getArrangementWrappers(userId);
	//Act
		String arrangementIdReturned = arrangementList.get(0).getId();
	//Verify
		assertTrue(customersTransactions.containsKey(arrangementIdReturned));		
	}
	
	@Test
	public void filterTransactionsByDateShouldReturnFilteredListOfTransactionsThatSitBetweenAndOnDatesGiven()
	{
		//setup
		String dateFrom = "01/01/2001";
		String dateTo = "05/01/2001";
		ArrayList<Transaction> unsortedList = new ArrayList<Transaction>();
	 	ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/01/2000", "GBP", arrangementId, "DEPOSIT", "",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "02/01/2001", "GBP", arrangementId, "DEPOSIT", "",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/02/2000", "GBP", arrangementId, "DEPOSIT", "",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", dateFrom, "GBP", arrangementId, "DEPOSIT", "",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", dateTo, "GBP", arrangementId, "DEPOSIT", "",wrapper));
		 
		//act
		ArrayList<Transaction> sortedList= transactionsData.filterTransactionsByDate(unsortedList, dateFrom, dateTo);
		
		//verify
		assertEquals(3, sortedList.size());
	}

	@Test
	public void filterTransactionsByNameShouldReturnFilteredListOfTransactionsThatContainTheQueryStringInTheName()
	{
		//setup
		String dateFrom = "01/01/2001";
		String dateTo = "05/01/2001";
		String query = "James";
		ArrayList<Transaction> unsortedList = new ArrayList<Transaction>();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/01/2000", "GBP", arrangementId, "DEPOSIT", "James",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "02/01/2001", "GBP", arrangementId, "DEPOSIT", "James",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/02/2000", "GBP", arrangementId, "DEPOSIT", "Don't touch",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", dateFrom, "GBP", arrangementId, "DEPOSIT", "no no",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", dateTo, "GBP", arrangementId, "DEPOSIT", "James",wrapper));
		
		//act
		ArrayList<Transaction> sortedList = transactionsData.filterTransactionsByName(unsortedList, query);
		//verify
		assertEquals(3, sortedList.size());
	}
	
	@Test
	public void filterTransactionsByNameShouldReturnFilteredListOfTransactionsThatContainTheQueryStringInTheNameIgnoringCase()
	{
		//setup
		String dateFrom = "01/01/2001";
		String dateTo = "05/01/2001";
		String query = "JAMES";
		ArrayList<Transaction> unsortedList = new ArrayList<Transaction>();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/01/2000", "GBP", arrangementId, "DEPOSIT", "James",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "02/01/2001", "GBP", arrangementId, "DEPOSIT", "James",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/02/2000", "GBP", arrangementId, "DEPOSIT", "Don't touch",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", dateFrom, "GBP", arrangementId, "DEPOSIT", "no no",wrapper));
		unsortedList.add(objectGenerator.createNewTransaction("50", "50", dateTo, "GBP", arrangementId, "DEPOSIT", "James",wrapper));
		
		//act
		ArrayList<Transaction> sortedList = transactionsData.filterTransactionsByName(unsortedList, query);
		//verify
		assertEquals(3, sortedList.size());
	}
	

	@Test
	public void getTransactionLimitShouldReturnTransactionLimitAmount()
	{
		//setup
		BigDecimal expectedAmount = new BigDecimal(300);
		PersistenceContainer.getInstance().setTransactionLimitAmount(expectedAmount);
		//act
		BigDecimal limitAmount = transactionsData.getTransactionLimitAmount();
		//verify
		assertEquals(expectedAmount, limitAmount);
	}
}
