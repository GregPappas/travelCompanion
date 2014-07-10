/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *   
 * Date: 5 Nov 2012 
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

/**
 * <p>
 * Exception thrown when phone authentication fails.
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class PhoneAuthenticationException extends UnauthorisedException {
	private static final long serialVersionUID = 1L;

	public PhoneAuthenticationException(IbErrorCode code) {
		super(code);
	}
}
