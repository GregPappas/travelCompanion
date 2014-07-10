package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.helpers.ArrangementHelper;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount.CURRENCY_CODE;

public class ArrangementHelperTest {

	ObjectGenerator objectGenerator = new ObjectGenerator(); 
	ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
	BeneficiaryDataIpsum beneficiaryData = new BeneficiaryDataIpsum();
	TransactionsDataIpsum transactionData = new TransactionsDataIpsum();
	ArrangementHelper arrangementHelper = new ArrangementHelper();
	
	@Before
	public void initialize() throws IOException {
		arrangementData = new ArrangementServiceDataIpsum();
		arrangementData.instantiateCustomersArrangements();
		
		transactionData = new TransactionsDataIpsum();
		transactionData.readAndParseCSVFile();
				
		beneficiaryData = new BeneficiaryDataIpsum();
		beneficiaryData.generateHashMapOfBeneficiaries();
		
		objectGenerator = new ObjectGenerator();
	}

	
	@After
	public void deconstruct()
	{
    	arrangementData.clearArrangementWrapperMap();
    	transactionData.getTransactionMap().clear();
    	beneficiaryData.getBeneficiaryMap().clear();
    	objectGenerator = null;
   }
	
	
	@Test
	public void updateBalanceShouldChangeBalanceOfArrangementWithIdPassedInToBalancePassedIn() {
		
		//Setup
			String arrangementId = "20756882123456";
			CurrencyAmount runningBalance = new CurrencyAmount();
			double amount = 134.43;
			BigDecimal bigDecimal = BigDecimal.valueOf(amount);
			runningBalance.setAmount(bigDecimal);
			runningBalance.setCurrency(CURRENCY_CODE.GBP);
			ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		//Act
			arrangementHelper.updateBalance(arrangementId, runningBalance);
		//Verify
			double newAmount = Double.parseDouble(wrapper.getAvailableBalance().getAmount().toString());
			assertEquals(amount, newAmount, 0.00001);
			assertNotSame(runningBalance, wrapper.getAvailableBalance()); //make sure only the value is the same, not the reference
	}
	

	@Test
	public void sumOfBalancesShouldCalculateTheSumOfTwoCurrencyAmountsAndReturnACurrencyAmmountWithTheResult()
	{
		//Setup
		CurrencyAmount balance1 = new CurrencyAmount();
		CurrencyAmount balance2 = new CurrencyAmount();
		CurrencyAmount resultingBalance;
		CurrencyAmount resultingBalance2;
		double amount1 = 134.43;
		BigDecimal bigDecimal1 = BigDecimal.valueOf(amount1).setScale(2, BigDecimal.ROUND_HALF_UP);
		balance1.setAmount(bigDecimal1);
		balance1.setCurrency(CURRENCY_CODE.GBP);
		double amount2 = 65.57;
		BigDecimal bigDecimal2 = BigDecimal.valueOf(amount2).setScale(2, BigDecimal.ROUND_HALF_UP);
		balance2.setAmount(bigDecimal2);
		balance2.setCurrency(CURRENCY_CODE.GBP);
		double expectedAmount = amount1+ amount2;
	//Act
		resultingBalance = arrangementHelper.sumOfBalances(balance1, balance2);
		resultingBalance2 = arrangementHelper.sumOfBalances(balance1, balance2);
	//Verify
		double resultingAmont = Double.parseDouble(resultingBalance.getAmount().toString());
		double resultingAmont2 = Double.parseDouble(resultingBalance2.getAmount().toString());
		assertEquals(expectedAmount, resultingAmont,  0.0000001);
		assertEquals(expectedAmount, resultingAmont2,  0.0000001);
	
	}
	
	@Test
	public void makePaymentToLocalCustomerShouldAddTransactionAndUpdateBalanceOfArrangementCorrespondingToBeneficiary()
	{
		//Setup
		String arrangementId = "20756882123456";
		String targetArrangementId="28194341123456";
		String newBeneficiaryId = beneficiaryData.getNewBeneficiaryID();
		Beneficiary beneficiary = objectGenerator.createBeneficiary("28194341", "123456", "test",newBeneficiaryId);
		beneficiaryData.getBeneficiaryList(arrangementId).add(beneficiary);
		String beneficiaryId = beneficiary.getId();
		CurrencyAmount amount = new CurrencyAmount();
		double amountDouble = 134.43;
		BigDecimal bigDecimal = BigDecimal.valueOf(amountDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
		amount.setAmount(bigDecimal);
		amount.setCurrency(CURRENCY_CODE.GBP);
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(targetArrangementId);
		
		//Act

		double oldAmount = Double.parseDouble(wrapper.getAvailableBalance().getAmount().toString());
		double expectedNewAmount = oldAmount + amountDouble;
		arrangementHelper.makePaymentToLocalCustomer(arrangementId, beneficiaryId, amount);
		double newAmount = Double.parseDouble(wrapper.getAvailableBalance().getAmount().toString());
		double oldAmount2 = Double.parseDouble(wrapper.getAvailableBalance().getAmount().toString());
		double expectedNewAmount2 = oldAmount2 + amountDouble;
		arrangementHelper.makePaymentToLocalCustomer(arrangementId, beneficiaryId, amount);
		double newAmount2 = Double.parseDouble(wrapper.getAvailableBalance().getAmount().toString());
		
		//Verify

		assertEquals(expectedNewAmount, newAmount, 0.000001);
		assertEquals(expectedNewAmount2, newAmount2, 0.000001);


	}

}
