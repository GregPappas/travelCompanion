package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public class DormantArrangementException extends ApiException {
	private static final long serialVersionUID = 1L;

	// from https://tools.ietf.org/html/rfc4918#page-78
	private static final int LOCKED = 423;

	public DormantArrangementException(IbErrorCode code) {
		super(code);
	}

	@Override
	public int getStatus() {
		return LOCKED;
	}

	@Override
	public ErrorCategory getCategory() {
		return ErrorCategory.BAD_REQUEST;
	}
}
