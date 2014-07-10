/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyAmount {
	public enum CURRENCY_CODE {
		GBP
	}

	private BigDecimal amount;
	private CURRENCY_CODE currency;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CURRENCY_CODE getCurrency() {
		return currency;
	}

	public void setCurrency(CURRENCY_CODE currency) {
		this.currency = currency;
	}
}
