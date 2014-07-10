package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SavingsAccount extends DepositAccount {
	private CurrencyAmount availableBalance;

	public CurrencyAmount getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(CurrencyAmount availableBalance) {
		this.availableBalance = availableBalance;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.SAVINGS_ACCOUNT;
	}
}