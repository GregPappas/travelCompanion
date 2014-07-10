package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lloydstsb.rest.v1.helpers.BeneficiaryHelper;

public class BeneficiaryHelperTest {
private BeneficiaryHelper beneficiatyHelper = new BeneficiaryHelper();
	
	@Test
	public void validateAccountNumberAndSortCodeShouldReturnTrueIfBothAreNull() 
	{
//		setup
		String accountNumber = null;
		String sortCode = null; 
//		act
		boolean result = beneficiatyHelper.validateAccountNumberAndSortCodeNull(accountNumber, sortCode);
//		verify
		assertTrue(result);
	}
	
	@Test
	public void checkAccountNumberValidShouldReturnFalseIfNullPassedIn()
	{
		boolean result = beneficiatyHelper.checkAccountNumberValid(null);
		assertFalse(result);
	}
	
	@Test
	public void checkAccountNumberValidShouldReturnFalseIfInvalidAccountnumberPassedIn()
	{
		boolean result = beneficiatyHelper.checkAccountNumberValid("123");
		boolean result2 = beneficiatyHelper.checkAccountNumberValid("adsasf");
		boolean result3 = beneficiatyHelper.checkAccountNumberValid("123456789");
		assertFalse(result);
		assertFalse(result2);
		assertFalse(result3);
	}
	
	@Test
	public void checkAccountNumberValidShouldReturnTrueIfEightDigitNumberPassedIn()
	{
		boolean result = beneficiatyHelper.checkAccountNumberValid("12345678");
		assertTrue(result);
	}
	
	@Test
	public void checkSortCodeValidShouldReturnFalseIfNullPassedIn()
	{
		boolean result = beneficiatyHelper.checkSortCodeValid(null);
		assertFalse(result);
	}
	
	@Test
	public void checkSortCodeValidShouldReturnFalseIfInvalidSortCodePassedIn()
	{
		boolean result = beneficiatyHelper.checkSortCodeValid("123");
		boolean result2 = beneficiatyHelper.checkSortCodeValid("adsasf");
		boolean result3 = beneficiatyHelper.checkSortCodeValid("123456789");
		assertFalse(result);
		assertFalse(result2);
		assertFalse(result3);
	}
	
	@Test
	public void checkSortCodeValidShouldReturnTrueIfEightDigitNumberPassedIn()
	{
		boolean result = beneficiatyHelper.checkSortCodeValid("123456");
		assertTrue(result);
	}
//	
//	@Test
//	public void validateAccountNumberAndSortCodeShouldReturnFalseIfBothAreGivenButEitherOneIsInvalid()
//	{
//
//		//setup
//			String accountNumber = "86342516";
//			String invalidAccountNumber = "jasdh";
//			String sortCode = "123456";
//			String invalidSortCode = "adasf";
//		//act	
//		boolean result = beneficiatyHelper.validateAccountNumberAndSortCode(accountNumber, invalidSortCode);
//		boolean result2 = beneficiatyHelper.validateAccountNumberAndSortCode(invalidAccountNumber, sortCode);
//		//verify
//		assertFalse(result);
//		assertFalse(result2);	
//	}

}
