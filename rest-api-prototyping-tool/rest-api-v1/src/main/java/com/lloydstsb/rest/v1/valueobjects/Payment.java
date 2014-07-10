/**
 * 
 */
package com.lloydstsb.rest.v1.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * Payment object returned from the validate payment API.
 * </p>
 * 
 * @author Jesper Madsen (CT026780)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Payment {
	private String paymentId;
	
	private AuthenticationType authenticationType;

	private String reference;

	private List<String> messages;

	/**
	 * FASTER_PAYMENT_AVAILABLE_FLAG.
	 */
	private boolean fasterPaymentOffered;
	
	/**
	 * PROCESS_AS_FASTER_PAYMENT_FLAG.
	 */
	private boolean processAsFasterPayment;

	/**
	 * If internalPayment is true and this is true it means the payment is immediate. 
	 */
	private boolean internalPaymentAllowed;

	private boolean internalPayment;
	
	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the authenticationType
	 */
	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}
	/**
	 * @param authenticationType the authenticationType to set
	 */
	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}
	/**
	 * @return the fasterPaymentOffered
	 */
	public boolean isFasterPaymentOffered() {
		return fasterPaymentOffered;
	}
	/**
	 * @param fasterPaymentOffered the fasterPaymentOffered to set
	 */
	public void setFasterPaymentOffered(boolean fasterPaymentOffered) {
		this.fasterPaymentOffered = fasterPaymentOffered;
	}
	/**
	 * @return the processAsFasterPayment
	 */
	public boolean isProcessAsFasterPayment() {
		return processAsFasterPayment;
	}
	/**
	 * @param processAsFasterPayment the processAsFasterPayment to set
	 */
	public void setProcessAsFasterPayment(boolean processAsFasterPayment) {
		this.processAsFasterPayment = processAsFasterPayment;
	}
	/**
	 * @return the internalPaymentAllowed
	 */
	public boolean isInternalPaymentAllowed() {
		return internalPaymentAllowed;
	}
	/**
	 * @param internalPaymentAllowed the internalPaymentAllowed to set
	 */
	public void setInternalPaymentAllowed(boolean internalPaymentAllowed) {
		this.internalPaymentAllowed = internalPaymentAllowed;
	}
	/**
	 * @return the internalPayment
	 */
	public boolean isInternalPayment() {
		return internalPayment;
	}
	/**
	 * @param internalPayment the internalPayment to set
	 */
	public void setInternalPayment(boolean internalPayment) {
		this.internalPayment = internalPayment;
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
}