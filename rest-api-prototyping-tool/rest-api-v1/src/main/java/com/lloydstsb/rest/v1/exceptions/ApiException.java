/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public abstract class ApiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final IbErrorCode code;

	public ApiException(final IbErrorCode code) {
		super(code.getErrorDesc());
		this.code = code;
	}

	public IbErrorCode getCode() {
		return code;
	}

	public abstract int getStatus();
	public abstract ErrorCategory getCategory();
}
