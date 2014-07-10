/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Beneficiary extends BeneficiaryBase implements Serializable{
	private String id;

	private String reference;

	private boolean referenceAllowed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * @return the referenceAllowed
	 */
	public boolean isReferenceAllowed() {
		return referenceAllowed;
	}

	/**
	 * @param referenceAllowed the referenceAllowed to set
	 */
	public void setReferenceAllowed(boolean referenceAllowed) {
		this.referenceAllowed = referenceAllowed;
	}
}