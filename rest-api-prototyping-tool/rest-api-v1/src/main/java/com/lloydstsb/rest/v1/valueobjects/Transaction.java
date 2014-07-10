/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lloydstsb.rest.common.types.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlSeeAlso({ CreditCardTransaction.class })
public class Transaction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private CurrencyAmount amount;

	private String description;

	private CurrencyAmount runningBalance;

	private TransactionType type;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CurrencyAmount getAmount() {
		return amount;
	}

	public void setAmount(CurrencyAmount amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CurrencyAmount getRunningBalance() {
		return runningBalance;
	}

	public void setRunningBalance(CurrencyAmount runningBalance) {
		this.runningBalance = runningBalance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}
}
