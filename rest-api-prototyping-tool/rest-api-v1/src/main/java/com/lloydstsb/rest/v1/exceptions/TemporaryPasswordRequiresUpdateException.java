package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class TemporaryPasswordRequiresUpdateException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public TemporaryPasswordRequiresUpdateException(IbErrorCode code) {
		super(code);
	}
}
