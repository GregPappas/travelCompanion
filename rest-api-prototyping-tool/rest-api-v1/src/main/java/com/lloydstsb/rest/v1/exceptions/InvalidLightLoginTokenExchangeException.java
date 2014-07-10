package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public class InvalidLightLoginTokenExchangeException extends ApiException {
	private static final long serialVersionUID = 1L;
	
	public InvalidLightLoginTokenExchangeException(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return Response.Status.BAD_REQUEST.getStatusCode();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.FORCED_LOGOUT;
	}
}
