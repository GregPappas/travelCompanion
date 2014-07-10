package com.lloydstsb.rest.v1.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

@Component
@Provider
public class MethodConstraintViolationExceptionMapper implements ExceptionMapper<MethodConstraintViolationException> {
	public static final String INVALID_ARGUMENT_CODE = "0013";

	public Response toResponse(MethodConstraintViolationException exception) {
		List<Error> errors = new ArrayList<Error>();

		for (MethodConstraintViolation<?> violation : exception.getConstraintViolations()) {
			errors.add(new Error(INVALID_ARGUMENT_CODE, violation.getMessage(), ErrorCategory.BAD_REQUEST));
		}

		ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);

		if (errors.size() == 1) {
			return response.entity(errors.get(0)).build();
		}

		// this class wraps generic entities to prevent JAXB from getting confused
		GenericEntity<?> errorList = new GenericEntity<List<Error>>(errors){};

		return response.entity(errorList).build();
	}
}
