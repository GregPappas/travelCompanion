package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class InvalidUserAccountException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public InvalidUserAccountException(IbErrorCode code) {
		super(code);
	}
}
