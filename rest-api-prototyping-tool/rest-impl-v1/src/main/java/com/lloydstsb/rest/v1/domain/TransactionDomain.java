package com.lloydstsb.rest.v1.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;

@Entity
@Table(name="bank_transaction")
public class TransactionDomain extends BaseDomain {
	
	@Id
	@Column(name="id")
	private String transactionId;
	
	private String amount;
	
	private String amountCurrency;
	
	private String description;
	
	private String runningBalance;
	
	private String runningBalanceCurrency;
	
	@Column(name="transactionDate")
	private Date transactionDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="arrangementId", nullable=false)
	private ArrangementDomain arrangement;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRunningBalance() {
		return runningBalance;
	}

	public void setRunningBalance(String runningBalance) {
		this.runningBalance = runningBalance;
	}

	public ArrangementDomain getArrangement() {
		return arrangement;
	}

	public void setArrangement(ArrangementDomain arrangement) {
		this.arrangement = arrangement;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public String getRunningBalanceCurrency() {
		return runningBalanceCurrency;
	}

	public void setRunningBalanceCurrency(String runningBalanceCurrency) {
		this.runningBalanceCurrency = runningBalanceCurrency;
	}
	
}
