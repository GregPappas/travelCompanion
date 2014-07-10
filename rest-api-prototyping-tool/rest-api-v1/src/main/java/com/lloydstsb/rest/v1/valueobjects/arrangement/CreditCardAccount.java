/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lloydstsb.rest.common.types.DateAdapter;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreditCardAccount extends NamedAccount {

	private String cardNumber;

	private CurrencyAmount balance;

	private CurrencyAmount limit;

	private CurrencyAmount overdueAmount;

	private CurrencyAmount availableBalance;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date lastStatement;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public CurrencyAmount getBalance() {
		return balance;
	}

	public void setBalance(CurrencyAmount balance) {
		this.balance = balance;
	}

	public CurrencyAmount getLimit() {
		return limit;
	}

	public void setLimit(CurrencyAmount limit) {
		this.limit = limit;
	}

	/**
	 * @return the overdueAmount
	 */
	public CurrencyAmount getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * @param overdueAmount
	 *            the overdueAmount to set
	 */
	public void setOverdueAmount(CurrencyAmount overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * @return the availableBalance
	 */
	public CurrencyAmount getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            the availableBalance to set
	 */
	public void setAvailableBalance(CurrencyAmount availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Date getLastStatement() {
		return lastStatement;
	}

	public void setLastStatement(Date lastStatement) {
		this.lastStatement = lastStatement;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.CREDITCARD_ACCOUNT;
	}
}
