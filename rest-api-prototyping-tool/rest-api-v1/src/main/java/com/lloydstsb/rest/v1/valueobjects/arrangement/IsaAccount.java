package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IsaAccount extends DepositAccount {
	
	private CurrencyAmount remainingAllowance;

	public CurrencyAmount getRemainingAllowance() {
		return remainingAllowance;
	}

	public void setRemainingAllowance(CurrencyAmount remainingAllowance) {
		this.remainingAllowance = remainingAllowance;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.ISA_ACCOUNT;
	}
}
