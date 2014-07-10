package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class OneTimePasswordAttemptsExceededException extends BadRequestException {
	private static final long serialVersionUID = 1L;
	
	public OneTimePasswordAttemptsExceededException(final IbErrorCode code) {
		super(code);
	}
}
