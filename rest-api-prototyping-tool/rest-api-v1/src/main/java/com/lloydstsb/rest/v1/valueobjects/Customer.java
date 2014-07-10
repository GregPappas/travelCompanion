/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
	
	private String id;

	private String title;

	private String firstname;

	private String lastname;
	
	private String ocisID;

	private String email;

	private boolean showTermsAndConditions = true;

	private boolean showWelcomePage = true;

	private boolean marketingPreferenceEnabled;

	private boolean smsPreferenceEnabled;

	private boolean emailPreferenceEnabled;
	
	private List<PhoneNumber> telephoneNumbers;
	
	private Address address;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<PhoneNumber> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    public void setTelephoneNumbers(List<PhoneNumber> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getOcisID() {
		return ocisID;
	}

	public void setOcisID(String ocisID) {
		this.ocisID = ocisID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isShowTermsAndConditions() {
		return showTermsAndConditions;
	}

	public void setShowTermsAndConditions(boolean showTermsAndConditions) {
		this.showTermsAndConditions = showTermsAndConditions;
	}

	public boolean isShowWelcomePage() {
		return showWelcomePage;
	}

	public void setShowWelcomePage(boolean showWelcomePage) {
		this.showWelcomePage = showWelcomePage;
	}

	public boolean isMarketingPreferenceEnabled() {
		return marketingPreferenceEnabled;
	}

	public void setMarketingPreferenceEnabled(boolean marketingPreferenceEnabled) {
		this.marketingPreferenceEnabled = marketingPreferenceEnabled;
	}

	public boolean isSmsPreferenceEnabled() {
		return smsPreferenceEnabled;
	}

	public void setSmsPreferenceEnabled(boolean smsPreferenceEnabled) {
		this.smsPreferenceEnabled = smsPreferenceEnabled;
	}

	public boolean isEmailPreferenceEnabled() {
		return emailPreferenceEnabled;
	}

	public void setEmailPreferenceEnabled(boolean emailPreferenceEnabled) {
		this.emailPreferenceEnabled = emailPreferenceEnabled;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
