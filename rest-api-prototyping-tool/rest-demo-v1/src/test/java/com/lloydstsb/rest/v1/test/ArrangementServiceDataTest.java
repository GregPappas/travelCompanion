package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;

public class ArrangementServiceDataTest {

	@Test
	public void readLineFromReaderShouldReturnAWrapperWithDataInsideAsPassedToMethod() 
	{
		//Setup
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		String userId = "123";
		String arrangementId = "12345678901234";
		String arrangementType = "CURRENT_ACCOUNT";
		String accountNumber = "12345678";
		String sortCode = "123456";
		String creditCardNo = "";
		String accountName = "test current account";
		String startDate = "12/12/2010";
		String monthlyPaymentAmount = "";
		String monthlyPaymentCurrency = "";
		String overDraftAmount = "100.00";
		String overDraftCurrency = "GBP";
		String creditCardLimitAmount = "";
		String creditCardLimitCurrency = "";
		String acceptsTransfers = "true";
		String createBeneficiaryAvailable = "true";
		String state = "ENABLED";
		String makePaymentAvailable = "true";
		String makeTransferAvailable = "true";
		String nonMigratedAccount = "true";
		String otherBrand = "false";
		String otherBrandName = "";
		String referenceNumber = "";
		String statementValiditySpan="12";
		String prohibitiveIndicator = "FALSE";
		String line[] = {userId, arrangementId, arrangementType, accountNumber, sortCode, creditCardNo, accountName,
						startDate, monthlyPaymentAmount, monthlyPaymentCurrency, overDraftAmount, overDraftCurrency, 
						creditCardLimitAmount, creditCardLimitCurrency, acceptsTransfers, createBeneficiaryAvailable,
						state, makePaymentAvailable, makeTransferAvailable, nonMigratedAccount, otherBrand, otherBrandName, referenceNumber,statementValiditySpan,prohibitiveIndicator};
		//act
		ArrangementWrapper wrapper = arrangementData.readLineFromReader(line);
		String dateExpected = "";
		try {
			dateExpected = new SimpleDateFormat("dd/MM/yyyy").format(wrapper.getStartDate()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}  		
		//verify
		boolean monthlyPaymentIsNull = (wrapper.getMonthlyPayment()==null);
		boolean creditCardLimitIsNull = (wrapper.getLimit()==null);
		
		assertEquals(arrangementId, wrapper.getId());
		assertEquals(arrangementType, wrapper.getType().toString());
		assertEquals(accountNumber, wrapper.getAccountNumber().toString());
		assertEquals(sortCode, wrapper.getSortCode().toString());
		assertEquals(creditCardNo, wrapper.getCardNumber().toString());
		assertEquals(accountName, wrapper.getAccountName().toString());
		assertEquals(startDate, dateExpected);
		assertTrue(monthlyPaymentIsNull);
		assertEquals(overDraftAmount, wrapper.getOverdraftLimit().getAmount().toString());
		assertEquals(overDraftCurrency, wrapper.getOverdraftLimit().getCurrency().toString());
		assertTrue(creditCardLimitIsNull);
		assertEquals(acceptsTransfers, String.valueOf(wrapper.isAcceptsTransfers()));
		assertEquals(createBeneficiaryAvailable, String.valueOf(wrapper.isCreateBeneficiaryAvailable()));
		assertEquals(state, wrapper.getState().toString());
		assertEquals(makePaymentAvailable, String.valueOf(wrapper.isMakePaymentAvailable()));
		assertEquals(nonMigratedAccount, String.valueOf(wrapper.isNonMigratedAccount()));
		assertEquals(otherBrand, String.valueOf(wrapper.isOtherBrand()));
		assertEquals(otherBrandName, wrapper.getOtherBrandBankName().toString());
		assertEquals(referenceNumber, wrapper.getReferenceNumber().toString());
		assertEquals(statementValiditySpan, String.valueOf(wrapper.getStatementValiditySpan()));
	}
	
}
