/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *   
 * Date: 31 Oct 2012 
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
@XmlRootElement
 */
public class SearchBeneficiaries extends Beneficiary {
	private List<String> messages = new ArrayList<String>();
	private Page<Beneficiary> beneficiaries = new Page<Beneficiary>();
	
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public Page<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}
	public void setBeneficiaries(Page<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
}
