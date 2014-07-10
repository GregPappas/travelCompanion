/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.validation.constraints.NotNull;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

public abstract class DepositAccount extends NamedAccount {

	@NotNull
	private String accountNumber;

	@NotNull
	private String sortCode;

	private CurrencyAmount balance;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public CurrencyAmount getBalance() {
		return balance;
	}

	public void setBalance(CurrencyAmount balance) {
		this.balance = balance;
	}
}
