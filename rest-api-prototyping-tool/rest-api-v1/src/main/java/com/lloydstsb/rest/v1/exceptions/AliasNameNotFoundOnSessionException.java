package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class AliasNameNotFoundOnSessionException extends NotFoundException {
	private static final long serialVersionUID = 1L;

	public AliasNameNotFoundOnSessionException(final IbErrorCode code) {
		super(code);
	}

}
