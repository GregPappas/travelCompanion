/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Beneficiaries {
	
	private List<String> messages = new ArrayList<String>();
	private Page<Beneficiary> beneficiaries = new Page<Beneficiary>();
	
	public Beneficiaries() {
		super();
	}
	
	public Beneficiaries(Page<Beneficiary> beneficiaries) {
		super();
		this.beneficiaries = beneficiaries;
	}
	
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
