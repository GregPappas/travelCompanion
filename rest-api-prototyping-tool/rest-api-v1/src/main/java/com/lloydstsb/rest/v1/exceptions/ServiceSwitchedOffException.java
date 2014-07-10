/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.constants.RestErrorConstants;
import com.lloydstsb.rest.v1.valueobjects.Error;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 16/04/2013 16:35
 */
public class ServiceSwitchedOffException extends ApiException {
	private static final long serialVersionUID = 1L;

	public ServiceSwitchedOffException() {
		super(RestErrorConstants.SERVICE_UNAVAILABLE);
	}

	@Override
	public int getStatus() {
		return Response.Status.SERVICE_UNAVAILABLE.getStatusCode();
	}

	@Override
	public Error.ErrorCategory getCategory() {
		return Error.ErrorCategory.TRY_AGAIN;
	}
}
