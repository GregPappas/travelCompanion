package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class DeviceUnenrolledServerSideException extends ForcedLogoutException {
	private static final long serialVersionUID = 1L;

	public DeviceUnenrolledServerSideException(IbErrorCode code) {
		super(code);
	}
}
