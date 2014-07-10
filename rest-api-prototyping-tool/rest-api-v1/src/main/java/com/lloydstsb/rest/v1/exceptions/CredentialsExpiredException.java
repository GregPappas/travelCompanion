package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class CredentialsExpiredException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public CredentialsExpiredException(IbErrorCode code) {
		super(code);
	}
}
