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
public class SavedLoanAccount extends ProductArrangement {
	private CurrencyAmount loanAmount;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date expiryDate;

	public CurrencyAmount getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(CurrencyAmount loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.SAVED_LOAN_ACCOUNT;
	}
}
