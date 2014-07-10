/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Date: 29 Oct 2012
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p> 
 * </p>
 *
 * @author Jesper Madsen (CT026780)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NewBeneficiary {
	private String transactionId;
	private AuthenticationType authenticationType;
	private List<PhoneNumber> phoneNumbers;

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
	 * @return the phoneNumbers
	 */
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}
	/**
	 * @param phoneNumbers the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
}