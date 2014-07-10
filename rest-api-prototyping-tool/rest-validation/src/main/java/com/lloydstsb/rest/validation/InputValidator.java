package com.lloydstsb.rest.validation;

import java.lang.reflect.Method;

import javax.validation.ValidationException;

/**
 * Simple interface designed to be used with JSR303 validation implementation.
 * 
 * JAX-RS 2.0 will include JSR303 validation by default so when that is released
 * this class should cease to be necessary.
 * 
 * @author alex
 */
public interface InputValidator {
	
	public void validateInput(Object target, Method method, Object[] parameterValues) throws ValidationException;
}
