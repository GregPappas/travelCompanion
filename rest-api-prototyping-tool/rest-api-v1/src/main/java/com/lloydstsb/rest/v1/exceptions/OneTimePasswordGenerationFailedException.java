package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class OneTimePasswordGenerationFailedException extends BadRequestException {
	private static final long serialVersionUID = 1L;
	
	public OneTimePasswordGenerationFailedException(final IbErrorCode code) {
		super(code);
	}
}
