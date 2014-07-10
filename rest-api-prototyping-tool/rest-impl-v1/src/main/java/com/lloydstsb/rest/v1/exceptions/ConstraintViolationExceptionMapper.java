package com.lloydstsb.rest.v1.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	public static final String INVALID_FIELD_CODE = "0012";

	public Response toResponse(ConstraintViolationException exception) {
		List<Error> errors = new ArrayList<Error>();

		for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
			errors.add(new Error(INVALID_FIELD_CODE, violation.getMessage(), ErrorCategory.TRY_AGAIN));
		}

		ResponseBuilder response = Response.status(Response.Status.INTERNAL_SERVER_ERROR);

		if (errors.size() == 1) {
			return response.entity(errors.get(0)).build();
		}

		// this class wraps generic entities to prevent JAXB from getting confused
		GenericEntity<?> errorList = new GenericEntity<List<Error>>(errors){};

		return response.entity(errorList).build();
	}
}
