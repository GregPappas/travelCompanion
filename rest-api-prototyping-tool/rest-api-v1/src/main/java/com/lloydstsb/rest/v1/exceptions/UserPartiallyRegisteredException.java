/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.rest.v1.constants.RestErrorConstants;
import com.lloydstsb.rest.v1.valueobjects.Error;

import javax.ws.rs.core.Response;

/**
 * @author Lukasz Mrenca (9953138)
 * @since 22/07/2013 17:55
 */
public class UserPartiallyRegisteredException extends ApiException {
	private static final long serialVersionUID = 1L;

	public UserPartiallyRegisteredException() {
		super(RestErrorConstants.USER_PARTIALLY_REGISTERED);
	}

	@Override
	public int getStatus() {
		return Response.Status.FORBIDDEN.getStatusCode();
	}

	@Override
	public Error.ErrorCategory getCategory() {
		return Error.ErrorCategory.FORCED_LOGOUT;
	}
}
