package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class NotLoggedInException extends UnauthorisedException {
	private static final long serialVersionUID = 1L;

	public NotLoggedInException(IbErrorCode code) {
		super(code);
	}
}
