package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.helpers.MiscHelper;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount.CURRENCY_CODE;
import com.lloydstsb.rest.v1.valueobjects.TransactionType;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ArrangementType;

public class MiscHelperTest {
private MiscHelper miscHelper = new MiscHelper();
	
	@Test	
	public void validateIsNumericShouldThrowAnExceptionIfInputIsNotNumeric()
	{
		//Setup
			String userId="userId";
			String expected = userId + " is not a numeric value";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsNumeric(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateIsNumericShouldNotThrowAnExceptionIfInputIsNumericAndLengthGreaterThan0()
	{
		//Setup
			String userId="0000";
			String expected = "";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsNumeric(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateIsNumericShouldThrowAnExceptionIfInputIsNumericAndSmallerThan0()
	{
		//Setup
			String userId="-2";
			String expected = userId + " is not a numeric value";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsNumeric(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}

	@Test	
	public void validateIsDoubleNumberShouldThrowAnExceptionIfInputIsNotNumeric()
	{
		//Setup
			String userId="userId";
			String expected = userId + " is not a numeric value";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test	
	public void validateIsDoubleNumberShouldThrowAnExceptionIfInputHasDecimalPointButNoNumbersAftewards()
	{
		//Setup
			String userId="2.";
			String expected = userId + " is not a numeric value";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test	
	public void validateIsDoubleNumberShouldThrowAnExceptionIfInputIsJustADecimalPoint()
	{
		//Setup
			String userId=".";
			String expected = userId + " is not a numeric value";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test	
	public void validateIsDoubleNumberShouldNotThrowAnExceptionIfInputIsJustADecimalPointWithADigitBehindIt()
	{
		//Setup
			String userId=".1";
			String expected = "";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	
	@Test
	public void validateIsDoubleNumberShouldNotThrowAnExceptionIfInputIsDoubleNumberAndLengthGreaterThan0()
	{
		//Setup
			String userId="0000";
			String expected = "";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateIsDoubleNumberShouldNotThrowAnExceptionIfInputIsDoubleNumberAndSmallerThan0()
	{
		//Setup
			String userId="-2";
			String expected = "";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	@Test
	public void validateIsDoubleNumberShouldNotThrowAnExceptionIfInputIsDoubleNumberAndHasDecimalPoint()
	{
		//Setup
			String userId="2.23";
			String expected = "";
			String returned= "";
		//Act
			try{
				miscHelper.validateIsDoubleNumber(userId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	
	
	@Test	
	public void validateArrangementIdShouldThrowAnErrorIfInputIsNotNumeric()
	{
		//Setup
		String arrangementId="arrangementId";
		String expected =arrangementId + " is not a valid arrangementId";
		String returned = "";
		//Act
			
			try{
			miscHelper.validateArrangementId(arrangementId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateArrangementIdShouldNotThrowAnErrorIfInputIsNumericAndLengthEquals14()
	{
		//Setup
		String arrangementId="00000000000000";
		String expected ="";
		String returned = "";
		//Act
			
			try{
			miscHelper.validateArrangementId(arrangementId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateArrangementIdShouldThrowAnExceptionIfInputIsNumericAndLengthNotEquals14()
	{
		//Setup

		String arrangementId="0000";
		String expected =arrangementId + " is not a valid arrangementId";
		String returned = "";
		//Act
			
			try{
			miscHelper.validateArrangementId(arrangementId);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	@Test
	public void validateArrangementTypeShouldThrowAnExceptionIfTypeDoesNotExist()
	{
		//setup
			String type ="invalidType";
			String expected =type + " is not a valid type";
			String returned = "";
		//Act
			
			try{
			miscHelper.validateArrangementType(type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateArrangementTypeShouldNotThrowAnExceptionIfTypeExists()
	{
		//setup
			String type = ArrangementType.SAVINGS_ACCOUNT.toString();
			String expected ="";
			String returned = "";
		//Act
			
			try{
			miscHelper.validateArrangementType(type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateTransactionTypeShouldThrowAnExceptionIfTypeDoesNotExist()
	{
		//setup
			String type ="invalidType";
			String expected =type + " is not a valid type";
			String returned = "";
		//Act
			
			try{
			miscHelper.validateTransactionType(type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateTransactionTypeShouldNotThrowAnExceptionIfTypeExists()
	{
		//setup
			String type = "DEP";
			String expected ="";
			String returned = "";
		//Act
			
			try{
			miscHelper.validateTransactionType(type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateAccountNumberShouldThrowAnExceptionIfAccountNumberExpectedButIsNotNumeric()
	{
	//setup
		String accNumber = "account Number";
		String type = "CURRENT_ACCOUNT";
		String expected =type+" is not a valid account number";
		String returned = "";
	//Act
		
		try{
			miscHelper.validateAccountNumber(accNumber, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
	//Verify
		assertEquals(expected, returned);
	}
	
	@Test
	public void validateAccountNumberShouldThrowAnExceptionIfAccountNumberIsExpectedIsNumericButTheLenghtDoesntMatch()
	{
		//setup
			String type = "CURRENT_ACCOUNT";
			String accNumber = "123";
			String expected =type+" is not a valid account number";
			String returned = "";
		//Act
			
			try{
				miscHelper.validateAccountNumber(accNumber, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateAccountNumberShouldThrowAnExceptionIfAccountNumberIsNotExpectedButIsGiven()
	{
		//setup
			String type = "CREDIT_CARD_ACCOUNT";
			String accNumber = "123";
			String expected =type+" is not a valid account number";
			String returned = "";
		//Act
			
			try{
				miscHelper.validateAccountNumber(accNumber, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}

	@Test
	public void validateAccountNumberShouldNotThrowAnExceptionIfAccountNumberIsExpectedIsNumericAndLengthMatches()
	{
		//setup
			String type = "CURRENT_ACCOUNT";
			String accNumber = "12345678";
			String expected ="";
			String returned = "";
		//Act
			
			try{
				miscHelper.validateAccountNumber(accNumber, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}

	@Test
	public void validateAccountNumberShouldNotThrowAnExceptionIfAccountNumberIsNotExpectedAndNotGiven()
	{
		//setup
			String type = "CREDIT_CARD_ACCOUNT";
			String accNumber = "";
			String expected ="";
			String returned = "";
		//Act
			
			try{
				miscHelper.validateAccountNumber(accNumber, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateSortCodeShouldThrowAnExceptionIfSortCodeIsExpectedAndGivenButIsNotNumeric()
	{
		//setup
		String type = "CURRENT_ACCOUNT";
		String sortCode = "sortCode";
		String expected =sortCode + " is not a valid sort code";
		String returned = "";
		//act
		try{	
		miscHelper.validateSortCode(sortCode, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateSortCodeShouldThrowAnExceptionIfSortCodeIsExpectedAndGivenAndIsNumericButTheLenghtDoesntMatch()
	{
		//setup
		String type = "CURRENT_ACCOUNT";
		String sortCode = "123";
		String expected =sortCode + " is not a valid sort code";
		String returned = "";
		//act
			try{	
			miscHelper.validateSortCode(sortCode, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
				assertEquals(expected, returned);
	}
	
	@Test
	public void validateSortCodeShouldThrowAnExceptionIfSortCodeIsNotExpectedButIsGiven()
	{
		//setup
		String type = "CREDIT_CARD_ACCOUNT";
		String sortCode = "123";
		String expected ="This type of arrangement should not have a sort code";
		String returned = "";
			//act
				try{	
				miscHelper.validateSortCode(sortCode, type);
				}catch(IllegalArgumentException e)
				{
					returned = e.getMessage();
				}
			//verify
					assertEquals(expected, returned);
	}
	
	@Test
	public void validateSortCodeShouldNotThrowAnExceptionIfSortCodeIsExpectedAndIsNumericAndLengthMatches()
	{
		//setup
		String type = "CURRENT_ACCOUNT";
		String sortCode = "123456";
		String expected ="";
		String returned = "";
		//act
			try{	
			miscHelper.validateSortCode(sortCode, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
				assertEquals(expected, returned);
		}
		
	
	@Test
	public void validateSortCodeShouldNotThrowAnExceptionIfSortCodeNotExpectedAndNotGiven()
	{
		//setup
		String type = "CREDIT_CARD_ACCOUNT";
		String sortCode = "";
		String expected ="";
		String returned = "";
		//act
			try{	
			miscHelper.validateSortCode(sortCode, type);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
				assertEquals(expected, returned);
	}

	
	
	@Test
	public void validateCreditCardNumberShouldThrowAnExceptionIfIsNotNumeric()
	{
		//setup
			String creditCardNumber = "creditCardNumber";
			String type = ArrangementType.CREDITCARD_ACCOUNT.toString();
			String returned="";
			String expected=creditCardNumber + " is not a valid creditcard number";
		//act
			try
			{
				miscHelper.validateCreditCardNumber(creditCardNumber,type);
				
			}catch (IllegalArgumentException e){
				returned=e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateCreditCardNumberShouldThrowAnExceptionIfIsNumericAndTypeIsNotCreditCard()
	{
		//setup
			String creditCardNumber = "1234567891234567";
			String type = ArrangementType.INVESTMENT_ACCOUNT.toString();
			String returned="";
			String expected="This type of arrangement should not have a creditcard number";
		//act
			try
			{
				miscHelper.validateCreditCardNumber(creditCardNumber,type);
				
			}catch (IllegalArgumentException e){
				returned=e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateCreditCardNumberShouldThrowAnExceptionIfIsNumericButTheLenghtDoesntMatch()
	{
		//setup
			String creditCardNumber = "123";
			String type = "invalidType";
			String returned="";
			String expected="This type of arrangement should not have a creditcard number";
		//act
			try
			{
				miscHelper.validateCreditCardNumber(creditCardNumber,type);
				
			}catch (IllegalArgumentException e){
				returned=e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateCreditCardNumberShouldNotThrowAnExceptionIfIsNumericLengthMatchesAndTypeIsCreditCard()
	{
		//setup
			String creditCardNumber = "1234567891234567";
			String type = ArrangementType.CREDITCARD_ACCOUNT.toString();
			String returned="";
			String expected="";
		//act
			try
			{
				miscHelper.validateCreditCardNumber(creditCardNumber,type);
				
			}catch (IllegalArgumentException e){
				returned=e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateCreditCardNumberShouldNotThrowAnExceptionIfIsNotExpectedAndNotGiven()
	{
		//setup
			String creditCardNumber = "";
			String type = ArrangementType.CBS_PERSONAL_LOAN_ACCOUNT.toString();
			String returned="";
			String expected="";
		//act
			try
			{
				miscHelper.validateCreditCardNumber(creditCardNumber,type);
				
			}catch (IllegalArgumentException e){
				returned=e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateDateShouldThrowAnExceptionIfDateIsInFuture()
	{
		//setup
		String date = "23/11/2014";
		String expected = date + " is not a valid arrangement date";
		String returned = "";
		//act
			try{
			miscHelper.validateDate(date);
			}catch(IllegalArgumentException e){
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateDateShouldThrowAnExceptionIfDateIsInWrongFormat()
	{
		//setup
		String date = "23/114";
		String expected = date + " is not a valid arrangement date";
		String returned = "";
		//act
			try{
			miscHelper.validateDate(date);
			}catch(IllegalArgumentException e){
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateDateShouldThrowAnExceptionIfDateIsInvalidSuchAsHavingTooManyMonthsInAYear()
	{
		//setup
			String date = "23/14/2010";
			String expected = date + " is not a valid arrangement date";
			String returned = "";
		//act
			try{
			miscHelper.validateDate(date);
			}catch(IllegalArgumentException e){
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}
	@Test
	public void validateDateShouldNotThrowAnExceptionIfValidPastDateIsGiven()
	{
		//setup
			String date = "12/05/2010";
			String expected = "";
			String returned = "";
			//act
				try{
				miscHelper.validateDate(date);
				}catch(IllegalArgumentException e){
					returned = e.getMessage();
				}
			//verify
				assertEquals(expected, returned);
		}
	
	@Test
	public void validateCurrencyAmountShouldNotThrowAnExceptionIfAmountIsLessThanZero()
	{
		//Setup
		String amount= "-4";
		String currency="GBP";
		String expected = "";
		String returned = "";
		//act
			try{
				miscHelper.validateCurrencyAmount(amount,currency);
			}catch(IllegalArgumentException e){
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateCurrencyAmountThrowAnExceptionIfCurrencyIsInvalid()
	{
		//setup
			String amount= "4";
			String currency="DOLLARS";
			String expected = currency +" is not a valid currency";
			String returned = "";
			//act
				try{
					miscHelper.validateCurrencyAmount(amount,currency);
				}catch(IllegalArgumentException e){
					returned = e.getMessage();
				}
			//verify
				assertEquals(expected, returned);
	}	
	
	@Test
	public void validateCurrencyAmountNotThrowAnExceptionIfAmountIsPositiveAndCurrencyCodeExists()
	{
		//setup
			String amount= "40";
			String currency="GBP";
			String expected ="";
			String returned = "";
			//act
				try{
					miscHelper.validateCurrencyAmount(amount,currency);
				}catch(IllegalArgumentException e){
					returned = e.getMessage();
				}
			//verify
				assertEquals(expected, returned);
	}
	
	@Test
	public void validateInputBooleanShouldThrowAnExceptionIfInputIsNotTrueorFalse()
	{
		//setup
			String input = "";
			String expected = input +" is not a valid boolean";
			String returned = "";
		//act
			try{
				miscHelper.validateInputBoolean(input);
			}catch(IllegalArgumentException e){
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateInputBooleanShouldNotThrowAnExceptionIfInputIsTrueorFalse()
	{
		//setup
			String input = "True";
			String input2 = "False";
			String expected = "";
			String returned = "";
			String returned2 = "";
		//act
			try{
			miscHelper.validateInputBoolean(input);
			miscHelper.validateInputBoolean(input2);
			}catch(IllegalArgumentException e){
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
			assertEquals(expected, returned2);
		}
	
	@Test
	public void validateStatusShouldThrowAnExceptionIfInputDoesNotMatchEnum()
	{
		//setup
			String status = "InvalidInput";
			String expected = status + " is not a valid status";
			String returned = "";
		//act
			try{
				miscHelper.validateStatus(status);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateStatusShouldNotThrowAnExceptionIfInputMatchesEnum()
	{
		//setup
			String status = "ENABLED";
			String expected ="";
			String returned = "";
		//act
			try{
				miscHelper.validateStatus(status);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected,returned);
	}
	
	@Test
	public void validateBrandNameShouldThrowAnExceptionIfBrandNameExpectedButNotGiven()
	{
		//setup
			String isBrandNameExpected = "true";
			String brandName = "";
			String expected = "Brand name cannot be empty if arrangement is other brand name";
			String returned = "";
		//act
			try{
			miscHelper.validateOtherBrandBankName(isBrandNameExpected, brandName);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}

	@Test
	public void validateBrandNameShouldThrowAnExceptionIfBrandNameNotExpectedButGiven()
	{
		//setup
			String isBrandNameExpected = "false";
			String brandName = "someUnexpectedBrand";
			String expected =  brandName + " is not a valid brand name";
			String returned = "";
		//act
			try{
			miscHelper.validateOtherBrandBankName(isBrandNameExpected, brandName);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}

	@Test
	public void validateBrandNameShouldNotThrowAnExceptionIfBrandNameExpectedAndGiven()
	{
		//setup
			String isBrandNameExpected = "true";
			String brandName = "SomeBrand";
			String expected =  "";
			String returned = "";
		//act
			try{
			miscHelper.validateOtherBrandBankName(isBrandNameExpected, brandName);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}

	@Test
	public void validateBrandNameShouldNotThrowAnExceptionIfBrandNameNotExpectedAndNotGiven()
	{
		//setup
			String isBrandNameExpected = "false";
			String brandName = "";
			String expected =  "";
			String returned = "";
		//act
			try{
			miscHelper.validateOtherBrandBankName(isBrandNameExpected, brandName);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateReferenceNumberShouldThrowExceptionIfAccountTypeIsMortgage_AccountAndReferenceNumberIsNotGiven()
	{
		//Setup
		String type = "MORTGAGE_ACCOUNT";
		String ref = "";
		String returned = "";
		String expected=ref + " is not a valid reference number";
		//Act
			
			try{
				miscHelper.validateReferenceNumber(type, ref);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);

	}
	
	@Test
	public void validateReferenceNumberShouldThrowExceptionIfAccountTypeIsNotMortgage_AccountAndReferenceNumberIsGiven()
	{
		//Setup
		String type = "CURRENT_ACCOUNT";
		String ref = "111";
		String returned = "";
		String expected=ref + " is not a valid reference number";
		//Act
			
			try{
				miscHelper.validateReferenceNumber(type, ref);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateReferenceNumberShouldThrowExceptionIfAccountTypeIsMortgage_AccountAndReferenceNumberIsGivenButNotAValidNumber()
	{
		//Setup
		String type = "MORTGAGE_ACCOUNT";
		String ref = "notAvalidNumber";
		String returned = "";
		String expected=ref + " is not a valid reference number";
		//Act
			
			try{
				miscHelper.validateReferenceNumber(type, ref);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateReferenceNumberShouldNotThrowExceptionIfAccountTypeIsMortgage_AccountAndReferenceNumberIsGivenAsAValidNumber()
	{
		//setup
			String type = "MORTGAGE_ACCOUNT";
			String ref = "1111111";
			String returned = "";
			String expected="";
			//Act
				
				try{
					miscHelper.validateReferenceNumber(type, ref);
				}catch(IllegalArgumentException e)
				{
					returned = e.getMessage();
				}
			//Verify
				assertEquals(expected, returned);
	}
	
	@Test
	public void validateReferenceNumberShouldNotThrowExceptionIfAccountTypeIsNotMortgage_AccountAndReferenceNumberIsNotGiven()
	{
		//setup
			String type = "CURRENT_ACCOUNT";
			String ref = "";
			String returned = "";
			String expected="";
			//Act
				
				try{
					miscHelper.validateReferenceNumber(type, ref);
				}catch(IllegalArgumentException e)
				{
					returned = e.getMessage();
				}
			//Verify
				assertEquals(expected, returned);
	}
	
	@Test	
	public void validateStatementValiditySpanShouldThrowExceptionIfInputIsNotNumeric()
	{
		//Setup
		String statementValiditySpan="statement invalid";
		String expected = statementValiditySpan + " is not a valid statement validity span";
		String returned= "";
		//Act
			
			try{
			miscHelper.validateStatementValiditySpan(statementValiditySpan);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateStatementValiditySpanShouldThrowExceptionIfInputIsNumericAndLengthGreaterThan2()
	{
		//Setup
		String statementValiditySpan="123";
		String expected = statementValiditySpan + " is not a valid statement validity span";
		String returned= "";
		
		//Act

			try{
			miscHelper.validateStatementValiditySpan(statementValiditySpan);
			}catch(IllegalArgumentException e)
			{
				returned = e.getMessage();
			}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateStatementValiditySpanShouldThrowExceptionIfInputIsBlank()
	{
		//Setup
		String statementValiditySpan="";
		String expected = statementValiditySpan + " is not a valid statement validity span";
		String returned= "";
		//Act

		try{
		miscHelper.validateStatementValiditySpan(statementValiditySpan);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test	
	public void validateStatementValiditySpanShouldNotThrowAnExceptionIfValidInputIsNumericAndLengthIsFewerThan3()
	{
		//Setup
		String statementValiditySpan="12";
		String expected = "";
		String returned= "";
		//Act

		try{
		miscHelper.validateStatementValiditySpan(statementValiditySpan);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//Verify
			assertEquals(expected, returned);
	}
	
	@Test
	public void validateMonthlyPaymentAmmountShouldCallValidateCurrencyAmmountIfArrangementIsOfTypeMortgageAccount()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "";
		String currency = "GBP";
		String expected = amount + " is not a valid amount";
		String returned = "";
		//act
		try{
		miscHelper.validateMonthlyPaymentAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateMonthlyPaymentAmmountShouldThrowAnExceptionIfAmmountIsGivenButNotExpected()
	{
		//Setup
		String type = ArrangementType.CURRENT_ACCOUNT.toString();
		String amount = "20";
		String currency = "";
		String expected = "This type of arrangement should not have a Monthly Payment";
		String returned = "";
		//act
		try{
		miscHelper.validateMonthlyPaymentAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateMonthlyPaymentAmmountShouldThrowAnExceptionIfCurrencyIsGivenButNotExpected()
	{
		//Setup
		String type = ArrangementType.CURRENT_ACCOUNT.toString();
		String amount = "";
		String currency = "GBP";
		String expected = "This type of arrangement should not have a Monthly Payment";
		String returned = "";
		//act
		try{
		miscHelper.validateMonthlyPaymentAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}

	@Test
	public void validateMonthlyPaymentAmmountShouldNotThrowAnExceptionIfAmountAndCurrencyNotExpectedAndNotGiven()
	{
		//Setup
		String type = ArrangementType.CURRENT_ACCOUNT.toString();
		String amount = "";
		String currency = "";
		String expected = "";
		String returned = "";
		//act
		try{
		miscHelper.validateMonthlyPaymentAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateOverDraftLimitAmmountShouldCallValidateCurrencyAmmountIfArrangementIsOfTypeMortgageAccount()
	{
		//Setup
		String type = ArrangementType.CURRENT_ACCOUNT.toString();
		String amount = "";
		String currency = "GBP";
		String expected = amount + " is not a valid amount";
		String returned = "";
		//act
		try{
		miscHelper.validateOverDraftLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateOverDraftLimitAmmountShouldThrowAnExceptionIfAmmountIsGivenButNotExpected()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "20";
		String currency = "";
		String expected = "This type of arrangement should not have a Monthly Payment";
		String returned = "";
		//act
		try{
		miscHelper.validateOverDraftLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateOverDraftLimitAmmountShouldThrowAnExceptionIfCurrencyIsGivenButNotExpected()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "";
		String currency = "GBP";
		String expected = "This type of arrangement should not have a Monthly Payment";
		String returned = "";
		//act
		try{
		miscHelper.validateOverDraftLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}

	@Test
	public void validateOverDraftLimitAmmountShouldNotThrowAnExceptionIfAmountAndCurrencyNotExpectedAndNotGiven()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "";
		String currency = "";
		String expected = "";
		String returned = "";
		//act
		try{
		miscHelper.validateOverDraftLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	
	///////////////////////
	
	@Test
	public void validateCreditCardLimitAmmountShouldCallValidateCurrencyAmmountIfArrangementIsOfTypeMortgageAccount()
	{
		//Setup
		String type = ArrangementType.CREDITCARD_ACCOUNT.toString();
		String amount = "";
		String currency = "GBP";
		String expected = amount + " is not a valid amount";
		String returned = "";
		//act
		try{
		miscHelper.validateCreditCardLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateCreditCardLimitAmmountShouldThrowAnExceptionIfAmmountIsGivenButNotExpected()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "20";
		String currency = "";
		String expected = "This type of arrangement should not have a Monthly Payment";
		String returned = "";
		//act
		try{
		miscHelper.validateCreditCardLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void validateCreditCardLimitAmmountShouldThrowAnExceptionIfCurrencyIsGivenButNotExpected()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "";
		String currency = "GBP";
		String expected = "This type of arrangement should not have a Monthly Payment";
		String returned = "";
		//act
		try{
		miscHelper.validateCreditCardLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}

	@Test
	public void validateCreditCardLimitAmmountShouldNotThrowAnExceptionIfAmountAndCurrencyNotExpectedAndNotGiven()
	{
		//Setup
		String type = ArrangementType.MORTGAGE_ACCOUNT.toString();
		String amount = "";
		String currency = "";
		String expected = "";
		String returned = "";
		//act
		try{
		miscHelper.validateCreditCardLimitAmount(amount, currency, type);
		}catch(IllegalArgumentException e)
		{
			returned = e.getMessage();
		}
		//verify
		assertEquals(expected, returned);
		
	}
	
	@Test
	public void generateTypeShouldReturnDepositIfAmountIsPositive()
	{
		//setup
		String amount = "123456";
		//act
		String type = miscHelper.generateType(amount);
		//verify
		assertEquals("DEB", type);
	}
	
	@Test
	public void generateTypeShouldReturnDebitCardIfAmountIsNegative()
	{
		//setup
		String amount = "-123456";
		//act
		String type = miscHelper.generateType(amount);
		//verify
		assertEquals("DEP", type);
	}
	
	
	
}
