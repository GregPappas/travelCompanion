package com.lloydstsb.rest.v1.exceptions;

import org.springframework.http.HttpStatus;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * Exception class for 400 responses.
 * 
 * @author Jesper Madsen (CT026780)
 */
public class BadRequestException extends ApiException {
	private static final long serialVersionUID = 266155828618942521L;

	public BadRequestException(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return HttpStatus.BAD_REQUEST.value();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.BAD_REQUEST;
	}
}
