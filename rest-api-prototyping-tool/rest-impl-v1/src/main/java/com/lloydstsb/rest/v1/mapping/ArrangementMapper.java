package com.lloydstsb.rest.v1.mapping;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.lloydstsb.rest.common.mapping.AbstractMapper;
import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CbsPersonalLoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CreditCardAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CurrentAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.DepositAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.InvestmentAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.IsaAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.LoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.MortgageAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.NamedAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.NonCbsPersonalLoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ProductArrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.SavedLoanAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.SavingsAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.TermDepositAccount;

@Component
public class ArrangementMapper extends AbstractMapper<ArrangementDomain, Arrangement> {

	private static final String CURRENT_ACCOUNT_TYPE = "CUR";
	private static final String SAVINGS_ACCOUNT_TYPE = "SAV";
	private static final String CREDIT_ACCOUNT_TYPE = "CRD";
	private static final String ISA_ACCOUNT_TYPE = "ISA";
	private static final String MORTGAGE_ACCOUNT_TYPE = "MOR";
	private static final String PERSONAL_LOAN_ACCOUNT_TYPE = "PLN";
	private static final String TERM_DEPOSIT_ACCOUNT_TYPE = "TDS";
	private static final String CBS_LOAN_ACCOUNT_TYPE = "CBS";
	private static final String SAVED_LOAN_ACCOUNT_TYPE = "SLN";
	private static final String INVESTMENT_ACCOUNT_TYPE = "INV";

	public Arrangement map(ArrangementDomain source) {
        if(source == null){
            return null;
        }
        Arrangement arrangement = createAndMapConcreteArrangement(source);
        if (arrangement instanceof ProductArrangement) {
        	((ProductArrangement) arrangement).setId(source.getArrangementId());
        	if(source.getStartDate()!=null){
        		((ProductArrangement) arrangement).setStartDate(source.getStartDate());
        	}
        }
        return arrangement;
    }

	private Arrangement createAndMapConcreteArrangement(ArrangementDomain source) {
		Arrangement arrangement = null;

		if (CURRENT_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapCurrentAccount(source);
		}
		if (SAVINGS_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapSavingsAccount(source);
		}
		if (CREDIT_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapCreditCardAccount(source);
		}
		if (ISA_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapIsaAccount(source);
		}
		if (MORTGAGE_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapMortgageAccount(source);
		}
		if (PERSONAL_LOAN_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapCbsPersonalLoanAccount(source);
		}
		if (CBS_LOAN_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapNonCbsPersonalLoanAccount(source);
		}
		if (TERM_DEPOSIT_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapTermDepositAccount(source);
		}
		if (SAVED_LOAN_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapSavedLoanAccount(source);
		}
		if (INVESTMENT_ACCOUNT_TYPE.equals(source.getAccountType())) {
			arrangement = mapInvestmentAccount(source);
		}
		return arrangement;
	}

	private Arrangement mapTermDepositAccount(ArrangementDomain source) {
		TermDepositAccount destination = new TermDepositAccount();
		mapDepositAccount(source, destination);
		destination.setMaturityDate(source.getMaturityDate());
		return destination;
	}

	private Arrangement mapSavedLoanAccount(ArrangementDomain source) {
		SavedLoanAccount destination = new SavedLoanAccount();
		destination.setExpiryDate(source.getExpiryDate());
		destination.setLoanAmount(convertToCurrencyAmount(source.getOriginalAmount()));
		return destination;
	}

	private Arrangement mapInvestmentAccount(ArrangementDomain source) {
		InvestmentAccount destination = new InvestmentAccount();
		destination.setAccountName(source.getAccountName());
		return destination;
	}

	private Arrangement mapIsaAccount(ArrangementDomain source) {
		IsaAccount destination = new IsaAccount();
		mapDepositAccount(source, destination);
		destination.setRemainingAllowance(convertToCurrencyAmount(source.getRemainingAllowance()));
		return destination;
	}

	private Arrangement mapMortgageAccount(ArrangementDomain source) {
		MortgageAccount destination = new MortgageAccount();
		mapLoanAccount(source, destination);
		destination.setMonthlyPaymentAmount(convertToCurrencyAmount(source.getMonthlyPayment()));
		destination.setMortgageAsAt(source.getBalanceAsAtDate());
		return destination;
	}

	private Arrangement mapNonCbsPersonalLoanAccount(ArrangementDomain source) {
		NonCbsPersonalLoanAccount destination = new NonCbsPersonalLoanAccount();
		destination.setLoanNumber(source.getLoanNumber());
		destination.setOriginalLoanAmount(convertToCurrencyAmount(source.getOriginalAmount()));
		mapLoanAccount(source, destination);
		return destination;
	}

	private Arrangement mapCbsPersonalLoanAccount(ArrangementDomain source) {
		CbsPersonalLoanAccount destination = new CbsPersonalLoanAccount();
		mapLoanAccount(source, destination);
		destination.setAccountNumber(source.getAccountNumber());
		destination.setSortCode(source.getSortCode());
		return destination;
	}

	private Arrangement mapCreditCardAccount(ArrangementDomain source) {
		CreditCardAccount destination = new CreditCardAccount();
		mapNamedAccount(source, destination);
		destination.setCardNumber(source.getCardNumber());
		destination.setLimit(convertToCurrencyAmount(source.getCreditLimit()));
		destination.setBalance(convertToCurrencyAmount(source.getBalanceAmount()));
		return destination;
	}

	private Arrangement mapSavingsAccount(ArrangementDomain source) {
		SavingsAccount destination = new SavingsAccount();
		mapDepositAccount(source, destination);
		return destination;
	}

	private CurrentAccount mapCurrentAccount(ArrangementDomain source) {
		CurrentAccount destination = new CurrentAccount();
		mapDepositAccount(source, destination);
		destination.setAvailableBalance(convertToCurrencyAmount((source.getAvailableBalance())));
		destination.setOverdraftLimit(convertToCurrencyAmount((source.getOverdraftLimit())));
		return destination;
	}

	private void mapNamedAccount(ArrangementDomain source, NamedAccount destination) {
		destination.setAccountName(source.getAccountName());
	}

	private void mapDepositAccount(ArrangementDomain source, DepositAccount destination) {
		mapNamedAccount(source, destination);
		destination.setAccountNumber(source.getAccountNumber());
		destination.setSortCode(source.getSortCode());
		destination.setBalance(convertToCurrencyAmount(source.getBalanceAmount()));
	}

	private void mapLoanAccount(ArrangementDomain source, LoanAccount destination) {
		mapNamedAccount(source, destination);
		destination.setBalance(convertToCurrencyAmount(source.getBalanceAmount()));
	}

	private CurrencyAmount convertToCurrencyAmount(String source) {
		CurrencyAmount destination = new CurrencyAmount();
		if (source != null) {
			destination.setAmount(new BigDecimal(source));
			destination.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
		}
		return destination;
	}
}
