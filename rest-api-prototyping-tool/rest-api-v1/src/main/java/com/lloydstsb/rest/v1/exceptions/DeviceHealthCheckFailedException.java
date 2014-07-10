package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class DeviceHealthCheckFailedException extends ForcedLogoutException {
	private static final long serialVersionUID = 1L;
	
	public DeviceHealthCheckFailedException(IbErrorCode code) {
		super(code);
	}
}
