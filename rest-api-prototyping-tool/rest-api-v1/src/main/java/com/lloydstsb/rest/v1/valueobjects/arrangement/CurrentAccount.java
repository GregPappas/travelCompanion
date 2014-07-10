/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrentAccount extends DepositAccount {

	private CurrencyAmount availableBalance;

	private CurrencyAmount overdraftLimit;

	public CurrencyAmount getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(CurrencyAmount availableBalance) {
		this.availableBalance = availableBalance;
	}

	public CurrencyAmount getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(CurrencyAmount overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	public ArrangementType getType() {
		return ArrangementType.CURRENT_ACCOUNT;
	}
}
