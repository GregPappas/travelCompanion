package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class NonceGenerationFailedException extends FailedOutcomeException {
	private static final long serialVersionUID = 1L;

	public NonceGenerationFailedException(IbErrorCode code) {
		super(code);
	}
}
