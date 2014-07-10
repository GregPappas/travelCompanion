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
 * Exception thrown when customer update fails.
 * </p>
 * 
 * @author Dermot Burke (9423066)
 */
public class CustomerUpdateFailedException extends FailedOutcomeException {
	private static final long serialVersionUID = 1L;

	public CustomerUpdateFailedException(IbErrorCode code) {
		super(code);
	}
}
