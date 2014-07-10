/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *   
 * Date: 16 Jan 2013 
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

/**
 * <p>
 * Exception thrown when the creation of a new customer enrolment fails.
 * </p>
 * 
 * @author Dermot Burke (9423066)
 */
public class CustomerUnenrolmentFailedException extends FailedOutcomeException {
	private static final long serialVersionUID = 1L;

	public CustomerUnenrolmentFailedException(IbErrorCode code) {
		super(code);
	}
}
