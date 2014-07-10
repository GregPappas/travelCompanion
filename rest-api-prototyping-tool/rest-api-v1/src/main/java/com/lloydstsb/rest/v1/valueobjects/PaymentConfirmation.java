/**
 * 
 */
package com.lloydstsb.rest.v1.valueobjects;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lloydstsb.rest.common.types.DateAdapter;

/**
 * <p>
 * Payment object returned from the fulfil payment API.
 * </p>
 * 
 * @author Jesper Madsen (CT026780)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentConfirmation {
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date actualPaymentDate;

	private String reference;

	private List<String> messages;

	/**
	 * @return the actualPaymentDate
	 */
	public Date getActualPaymentDate() {
		return actualPaymentDate;
	}
	/**
	 * @param actualPaymentDate the actualPaymentDate to set
	 */
	public void setActualPaymentDate(Date actualPaymentDate) {
		this.actualPaymentDate = actualPaymentDate;
	}
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		return messages;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}