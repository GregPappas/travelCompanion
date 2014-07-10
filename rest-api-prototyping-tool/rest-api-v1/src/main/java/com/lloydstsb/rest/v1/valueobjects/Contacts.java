/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: Contacts  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 17 Apr 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Contacts {
	private List<Contact> contacts = new ArrayList<Contact>();

	/**
	 * @return the contacts
	 */
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	/**
	 * @param contact
	 */
	public void addContact(Contact contact) {
		contacts.add(contact);
	}
}