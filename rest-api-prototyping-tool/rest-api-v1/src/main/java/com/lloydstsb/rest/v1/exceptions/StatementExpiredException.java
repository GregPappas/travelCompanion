package com.lloydstsb.rest.v1.exceptions;

import javax.servlet.http.HttpServletResponse;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public class StatementExpiredException extends ApiException {
	private static final long serialVersionUID = 1L;

	public StatementExpiredException(IbErrorCode code) {
		super(code);
	}

	@Override
	public int getStatus() {
		return HttpServletResponse.SC_GONE;
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.BAD_REQUEST;
	}
}
