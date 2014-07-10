package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class UserSuspendedException extends ForcedLogoutException {
	private static final long serialVersionUID = 1L;

	public UserSuspendedException(IbErrorCode code) {
		super(code);
	}
}
