package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class ThrottleCookieException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public ThrottleCookieException(IbErrorCode code) {
		super(code);
	}
}
