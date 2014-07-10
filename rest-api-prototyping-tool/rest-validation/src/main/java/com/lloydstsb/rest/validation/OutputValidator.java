package com.lloydstsb.rest.validation;

import javax.validation.ValidationException;

/**
 * Simple interface designed to be used with JSR303 validation implementation.
 * 
 * JAX-RS 2.0 will include JSR303 validation by default so when that is released
 * this class should cease to be necessary.
 * 
 * @author alex
 */
public interface OutputValidator {
	
	public void validateOutput(Object output) throws ValidationException;
}
