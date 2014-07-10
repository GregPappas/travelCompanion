package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class ExceedsReceivingLimitException extends BadTransferRequestException {
	private static final long serialVersionUID = 1L;

	public ExceedsReceivingLimitException(final IbErrorCode code) {
		super(code);
	}
}
