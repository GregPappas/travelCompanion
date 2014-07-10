/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: AuthenticationType  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 16 Jan 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public enum AuthenticationType {
	/**
	 * Password authentication.  
	 */
	PASSWORD;
	/**
	 * Phone authentication.
	 */
//	EIA,
	/**
	 * One time password authentication.
	 */
//	OTP,
	/**
	 * Transaction signing authentication.
	 */
//	TRANSACTION_SIGNING;

	public static AuthenticationType fromTypeCode(String authType) {
		for (AuthenticationType at : values()) {
			if (at.name().equalsIgnoreCase(authType)) {
				return at;
			}
		}
		throw new IllegalArgumentException("Invalid authentication type of: " + authType);
	}

}