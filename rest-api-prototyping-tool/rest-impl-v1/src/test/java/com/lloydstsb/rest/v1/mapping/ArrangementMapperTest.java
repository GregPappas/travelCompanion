package com.lloydstsb.rest.v1.mapping;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CbsPersonalLoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CreditCardAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CurrentAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.InvestmentAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.IsaAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.MortgageAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.NonCbsPersonalLoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ProductArrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.SavedLoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.SavingsAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.TermDepositAccount;

public class ArrangementMapperTest {
	private ArrangementMapper mapper;
	private ArrangementDomain domain;

	@Before
	public void setUp() {
		mapper = new ArrangementMapper();
		domain = new ArrangementDomain();
		domain.setAccountName("Current Account");
		domain.setAccountNumber("12345678");
		domain.setArrangementId("ACCOUNT||87654321||654321");
		domain.setBalanceAmount("20");
		domain.setBalanceCurrency("GBP");
		domain.setSortCode("123456");
	}

	@Test
	public void testMapCurrentAccount() throws Exception {
		domain.setAccountType("CUR");
		CurrentAccount value = (CurrentAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapSavingsAccount() throws Exception {
		domain.setAccountType("SAV");
		SavingsAccount value = (SavingsAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapCreditCard() throws Exception {
		domain.setAccountType("CRD");
		CreditCardAccount value = (CreditCardAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapIsa() throws Exception {
		domain.setAccountType("ISA");
		IsaAccount value = (IsaAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapMortgage() throws Exception {
		domain.setAccountType("MOR");
		MortgageAccount value = (MortgageAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapPersonalLoan() throws Exception {
		domain.setAccountType("PLN");
		CbsPersonalLoanAccount value = (CbsPersonalLoanAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapTermDeposit() throws Exception {
		domain.setAccountType("TDS");
		TermDepositAccount value = (TermDepositAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapCbsLoan() throws Exception {
		domain.setAccountType("CBS");
		NonCbsPersonalLoanAccount value = (NonCbsPersonalLoanAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapSavedLoan() throws Exception {
		domain.setAccountType("SLN");
		SavedLoanAccount value = (SavedLoanAccount) mapper.map(domain);
		checkCommonValues(value);
	}
	
	@Test
	public void testMapInvestment() throws Exception {
		domain.setAccountType("INV");
		InvestmentAccount value = (InvestmentAccount) mapper.map(domain);
		checkCommonValues(value);
	}

	private void checkCommonValues(ProductArrangement value) {
		assertEquals(domain.getArrangementId(), value.getId());
	}
	

}
