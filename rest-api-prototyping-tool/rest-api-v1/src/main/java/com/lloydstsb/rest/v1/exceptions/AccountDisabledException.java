package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class AccountDisabledException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public AccountDisabledException(IbErrorCode code) {
		super(code);
	}
}
