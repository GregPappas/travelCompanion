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
public class Error {
	private String code;
	private String message;
	private ErrorCategory category;

	public Error() {
		super();
	}

	public Error(String code, String message, ErrorCategory category) {
		super();
		this.code = code;
		this.message = message;
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public ErrorCategory getCategory() {
		return category;
	}

	public void setCategory(ErrorCategory category) {
		this.category = category;
	}

	public static enum ErrorCategory {
		FAILED_OUTCOME,
		BAD_REQUEST,
		FORCED_LOGOUT,
		TRY_AGAIN;
	}
}
