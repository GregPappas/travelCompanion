package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class PasswordExpiredException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public PasswordExpiredException(IbErrorCode code) {
		super(code);
	}
}
