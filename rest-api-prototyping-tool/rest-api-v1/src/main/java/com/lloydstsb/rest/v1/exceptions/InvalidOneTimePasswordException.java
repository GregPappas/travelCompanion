package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class InvalidOneTimePasswordException extends BadRequestException {
	private static final long serialVersionUID = 1L;
	
	public InvalidOneTimePasswordException(final IbErrorCode code) {
		super(code);
	}
}
