package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error;

@Component
@Provider
public class AuthorisationExceptionMapper implements ExceptionMapper<AuthorisationException> {

	public Response toResponse(AuthorisationException exception) {
		final IbErrorCode errorCode = exception.getCode();
		Error error = new Error(errorCode.getErrorCode(), errorCode.getErrorDesc(), exception.getCategory());
		return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
	}
}
