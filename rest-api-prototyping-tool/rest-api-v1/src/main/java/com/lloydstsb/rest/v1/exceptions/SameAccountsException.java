package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class SameAccountsException extends BadTransferRequestException {
	private static final long serialVersionUID = 1L;

	public SameAccountsException(IbErrorCode code) {
		super(code);
	}
}
