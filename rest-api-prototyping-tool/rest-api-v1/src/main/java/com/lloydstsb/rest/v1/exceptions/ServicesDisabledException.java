/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import org.springframework.http.HttpStatus;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * <p>
 * Thrown if switch SW_NGA_Enablejsn is set to false
 * </p>
 * 
 * @author Dermot Burke (ct026780)
 * 
 */
public class ServicesDisabledException extends ApiRuntimeException {
	private static final long serialVersionUID = 1L;

	public ServicesDisabledException(IbErrorCode code) {
		super(code);
	}

	public int getStatus() {
		return HttpStatus.FORBIDDEN.value();
	}

	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
