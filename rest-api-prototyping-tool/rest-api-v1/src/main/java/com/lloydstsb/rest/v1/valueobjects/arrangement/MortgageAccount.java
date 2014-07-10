package com.lloydstsb.rest.v1.valueobjects.arrangement;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lloydstsb.rest.common.types.DateAdapter;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MortgageAccount extends LoanAccount {

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date mortgageAsAt;

	private CurrencyAmount monthlyPaymentAmount;

	private String referenceNumber;

	public Date getMortgageAsAt() {
		return mortgageAsAt;
	}

	public void setMortgageAsAt(Date mortgageAsAt) {
		this.mortgageAsAt = mortgageAsAt;
	}

	public CurrencyAmount getMonthlyPaymentAmount() {
		return monthlyPaymentAmount;
	}

	public void setMonthlyPaymentAmount(CurrencyAmount monthlyPaymentAmount) {
		this.monthlyPaymentAmount = monthlyPaymentAmount;
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.MORTGAGE_ACCOUNT;
	}
}
