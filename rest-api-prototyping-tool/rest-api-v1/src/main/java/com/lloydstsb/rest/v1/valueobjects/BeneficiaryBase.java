/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: ExistingBeneficiary  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 26 Feb 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class BeneficiaryBase implements Comparable<BeneficiaryBase> {
	private String name;
	
	private String accountNumber;
	
	private String sortCode;
	
	private boolean enabled;

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	// Added for sorting the beneficiaries, implementing interface Comparable
	public int compareTo(BeneficiaryBase beneficiary) {
	    return this.name.compareToIgnoreCase(beneficiary.getName());
	}	
}