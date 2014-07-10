package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class AtLeastOneContactNumberRequiredException extends BadRequestException {
	private static final long serialVersionUID = 1L;

	public AtLeastOneContactNumberRequiredException(IbErrorCode code) {
		super(code);
	}
}
