package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class DataNotFoundException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(IbErrorCode code) {
		super(code);
	}
}
