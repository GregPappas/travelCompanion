package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class ProhibitiveIndicatorException extends TransferDeclinedException {
	private static final long serialVersionUID = 1L;

	public ProhibitiveIndicatorException(final IbErrorCode code) {
		super(code);
	}

}
