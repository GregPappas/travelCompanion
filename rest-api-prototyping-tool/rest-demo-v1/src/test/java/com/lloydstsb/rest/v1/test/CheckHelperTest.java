package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.helpers.CheckHelper;

public class CheckHelperTest {

	private CheckHelper checkHelper= new CheckHelper();
	@Test 	
	public void validateDatesFromAndToShouldReturnFalseIfDateFromGreaterThanDateTo()
	{
		//Setup
		String dateFrom="10/10/2010";
		String dateTo="10/10/2000";
		ArrangementWrapper wrapper=mock(ArrangementWrapper.class);  
		Mockito.when(wrapper.getStatementValiditySpan()).thenReturn(12);
		Mockito.when(wrapper.getStartDate()).thenReturn(new Date());
		//Act
		boolean returned=checkHelper.checkDatesFromAndTo(dateFrom, dateTo, wrapper);
		//Verify
		assertFalse(returned);
	}
	
	@Test 	
	public void validateDatesFromAndToShouldReturnFalseIfDateToGreaterThanToday()
	{
		//Setup
		String dateFrom="10/10/2010";
		String dateTo="10/10/2020";
		ArrangementWrapper wrapper=mock(ArrangementWrapper.class);  
		Mockito.when(wrapper.getStatementValiditySpan()).thenReturn(12);
		Mockito.when(wrapper.getStartDate()).thenReturn(new Date());
		//Act
		boolean returned=checkHelper.checkDatesFromAndTo(dateFrom, dateTo, wrapper);
		//Verify
		assertFalse(returned);
	}
	
	@Test 	
	public void validateDatesFromAndToShouldReturnFalseIfDateFromFewerThanExpiryDate()
	{
		//Setup
		String dateFrom="10/10/2010";
		String dateTo="10/10/2012";
		ArrangementWrapper wrapper=mock(ArrangementWrapper.class);  
		Mockito.when(wrapper.getStatementValiditySpan()).thenReturn(12);
		Mockito.when(wrapper.getStartDate()).thenReturn(new Date());
		//Act
		boolean returned=checkHelper.checkDatesFromAndTo(dateFrom, dateTo, wrapper);
		//Verify
		assertFalse(returned);
	}
	
	@Test 	
	public void validateDatesFromAndToShouldReturnFalseIfDateFromFewerThanStartDateFromWrapper()
	{
		//Setup
		String dateFrom="09/10/2012";
		String dateTo="10/02/2013";
		ArrangementWrapper wrapper=mock(ArrangementWrapper.class);  
		Mockito.when(wrapper.getStatementValiditySpan()).thenReturn(12);
		Date startDate;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			startDate = df.parse("10/10/2012");		
			Mockito.when(wrapper.getStartDate()).thenReturn(startDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Act
		boolean returned=checkHelper.checkDatesFromAndTo(dateFrom, dateTo, wrapper);
		//Verify
		assertFalse(returned);
	}
	
	@Test 	
	public void validateDatesFromAndToShouldReturnTrueIfDateValidate()
	{
		//Setup
		String dateFrom="11/10/2012";
		String dateTo="10/02/2013";
		ArrangementWrapper wrapper=mock(ArrangementWrapper.class);  
		Mockito.when(wrapper.getStatementValiditySpan()).thenReturn(12);
		Date startDate;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			startDate = df.parse("10/10/2012");		
			Mockito.when(wrapper.getStartDate()).thenReturn(startDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Act
		boolean returned=checkHelper.checkDatesFromAndTo(dateFrom, dateTo, wrapper);
		//Verify
		assertTrue(returned);
	}
	
	@Test
	public void checkParamsForEmptyShouldReturnFalseIfArrayContainsEmptyValueIsEmpty()
	{
		//setup
		String[] paramArray = {"","something", "everything"};
		//act
		boolean returned = checkHelper.checkParamsForEmpty(paramArray);
		//verify
		assertFalse(returned);
	}
	
	@Test
	public void checkParamsForEmptyShouldReturnFalseIfArrayContainsEmptyValueAndIsNull()
	{
		//setup
		String[] paramArray = {null,"something", "everything"};
		//act
		boolean returned = checkHelper.checkParamsForEmpty(paramArray);
		//verify
		assertFalse(returned);
	}
	
	@Test
	public void checkParamsForEmptyShouldReturnTrueIfArrayContainsNoEmptyValues()
	{
		//setup
		String[] paramArray = {"nothing","something", "everything"};
		//act
		boolean returned = checkHelper.checkParamsForEmpty(paramArray);
		//verify
		assertTrue(returned);
	}
	
	@Test 
	public void checkAccountIsWhiteListedShouldReturnTrueIfWhiteListed()
	{
		//setup
		String accountNumber = "22222222";
		String sortCode = "222222";
		//act
		boolean returned = checkHelper.checkAccountIsWhiteListed(accountNumber, sortCode);
		//verify
		assertTrue(returned);
	}
	
	@Test
	public void checkAccountIsWhiteListedShouldReturnFalseIfNotWhiteListed()
	{
		//setup
		String accountNumber="12345678";
		String sortCode ="654789";
		//act
		boolean returned = checkHelper.checkAccountIsWhiteListed(accountNumber, sortCode);
		//verify
		assertFalse(returned);
	}

	@Test
	public void checkExceedsReceivingLimitShouldReturnFalseIfAmmountIsLessThanReceivingLimit()
	{
		//setup
		BigDecimal amount = new BigDecimal(100);		
		//act
		boolean returned = checkHelper.checkExceedsReceivingLimit(amount);
		//verify
		assertFalse(returned);
	}
	
	@Test
	public void checkExceedsReceivingLimitShouldReturnTrueIfAmmountIsMoreThanReceivingLimit()
	{
		//setup
		BigDecimal amount = new BigDecimal(100000001);
		//act
		boolean returned = checkHelper.checkExceedsReceivingLimit(amount);
		//verify
		assertTrue(returned);
	}
}
