package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NonCbsPersonalLoanAccount extends LoanAccount {

	@NotNull
	private String loanNumber;

	private CurrencyAmount originalLoanAmount;

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public CurrencyAmount getOriginalLoanAmount() {
		return originalLoanAmount;
	}

	public void setOriginalLoanAmount(CurrencyAmount originalLoanAmount) {
		this.originalLoanAmount = originalLoanAmount;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.NON_CBS_PERSONAL_LOAN_ACCOUNT;
	}
}
