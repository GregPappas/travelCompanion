/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.joda.time.DateTime;

import com.lloydstsb.rest.common.types.DateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlSeeAlso({ CreditCardStatement.class })
@JsonTypeInfo(use = Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = Statement.class, name = "STATEMENT"),
//	@Type(value = CreditCardStatement.class, name = "CREDIT_CARD_STATEMENT")
})
@Description(target = DocTarget.RESOURCE, value = "A statement is a record of customer activity on an account during a given time period.")
public class Statement<T extends Transaction> {

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private DateTime lastTransactionDate;
	private List<String> messages = new ArrayList<String>();
	private Page<T> transactions;
	
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Page<T> getTransactions() {
		return transactions;
	}

	public void setTransactions(Page<T> transactions) {
		this.transactions = transactions;
	}

	public DateTime getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(DateTime lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}
}
