package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public class UnknownErrorException extends ApiRuntimeException {
	private static final long serialVersionUID = 1L;

	public UnknownErrorException(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.TRY_AGAIN;
	}
}
