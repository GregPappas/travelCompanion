package com.lloydstsb.rest.v1.data;

import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

public class BeneficiaryObject 
{
	private String name;
	private String accountNumber;
	private String sortCode;
	private boolean enabled;
	private String notes;
	
	public BeneficiaryObject(Beneficiary beneficiary)
	{
		this.name = beneficiary.getName();
		this.accountNumber = beneficiary.getSortCode();
		this.enabled = beneficiary.getEnabled();
		this.sortCode=beneficiary.getSortCode();
		this.notes = "beneficiary notes + " + beneficiary.getName();
	}

	public String getName() {
		return name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getSortCode() {
		return sortCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getNotes() {
		return notes;
	}
	
	
	
}
