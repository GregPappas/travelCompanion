package com.lloydstsb.rest.v1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank_beneficiary")
public class SearchBeneficiaryDomain extends BaseDomain {

	@Id
	@Column(name = "id")
	private String beneficiaryId;

	private String name;

	private String accountNumber;

	private String sortCode;

	private Boolean transactionPending;

	private String reference;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "arrangementId_id", nullable = false)
	private ArrangementDomain arrangement;

	private String notes;

	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Boolean getTransactionPending() {
		return transactionPending;
	}

	public void setTransactionPending(Boolean transactionPending) {
		this.transactionPending = transactionPending;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public ArrangementDomain getArrangement() {
		return arrangement;
	}

	public void setArrangement(ArrangementDomain arrangement) {
		this.arrangement = arrangement;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}