/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: ContactUs  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 17 Apr 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class Contact {
	private String name;
	private String phone;
	private String text;
	private String openingTimes;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the openingTimes
	 */
	public String getOpeningTimes() {
		return openingTimes;
	}
	/**
	 * @param openingTimes the openingTimes to set
	 */
	public void setOpeningTimes(String openingTimes) {
		this.openingTimes = openingTimes;
	}
}
