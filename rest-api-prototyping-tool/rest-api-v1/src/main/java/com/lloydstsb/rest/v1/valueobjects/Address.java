/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

	private String firstLine;
	
	private String secondLine;
	
	private String thirdLine;
	
	private String fourthLine;
	
	private String postCode;
	
	public String getFirstLine() {
		return firstLine;
	}
	
	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}
	
	public String getSecondLine() {
		return secondLine;
	}
	
	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}
	
	public String getThirdLine() {
		return thirdLine;
	}
	
	public void setThirdLine(String thirdLine) {
		this.thirdLine = thirdLine;
	}
	
	public String getFourthLine() {
		return fourthLine;
	}
	
	public void setFourthLine(String fourthLine) {
		this.fourthLine = fourthLine;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
