package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

@Component
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	public Response toResponse(NotFoundException exception) {
		final IbErrorCode errorCode = exception.getCode();
		Error error = new Error(errorCode.getErrorCode(), errorCode.getErrorDesc(), ErrorCategory.BAD_REQUEST);
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}
}
