package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public abstract class NonLoggingException extends ApiException {
	private static final long serialVersionUID = 1L;
    
	public NonLoggingException(IbErrorCode code) {
		super(code);
	}
}