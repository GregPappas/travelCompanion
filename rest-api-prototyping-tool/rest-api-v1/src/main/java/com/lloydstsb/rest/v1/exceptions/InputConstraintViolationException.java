/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.   
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.constants.RestErrorConstants;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public class InputConstraintViolationException extends ApiException {
	private static final long serialVersionUID = 1L;
	private final Set<ConstraintViolation<?>> constraintViolations;
	
	public InputConstraintViolationException(Set<ConstraintViolation<?>> constraintViolations) {
		super(RestErrorConstants.CONSTRAINT_VIOLATION);
		this.constraintViolations = constraintViolations;
	}

	public Set<ConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}
	
	public int getStatus() {
		return Response.Status.BAD_REQUEST.getStatusCode();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.BAD_REQUEST;
	}
}
