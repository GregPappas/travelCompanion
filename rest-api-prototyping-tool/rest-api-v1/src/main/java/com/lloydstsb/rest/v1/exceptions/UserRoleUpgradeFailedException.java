package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;


public class UserRoleUpgradeFailedException extends ApiException {
	private static final long serialVersionUID = 1L;

	public UserRoleUpgradeFailedException(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
