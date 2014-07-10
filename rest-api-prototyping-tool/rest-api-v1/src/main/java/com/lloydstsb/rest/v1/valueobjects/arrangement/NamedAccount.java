package com.lloydstsb.rest.v1.valueobjects.arrangement;

public abstract class NamedAccount extends ProductArrangement {

	private String accountName;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
