package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.validation.constraints.NotNull;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

public abstract class LoanAccount extends NamedAccount {

	@NotNull
	private CurrencyAmount balance;

	public CurrencyAmount getBalance() {
		return balance;
	}

	public void setBalance(CurrencyAmount balance) {
		this.balance = balance;
	}
}
