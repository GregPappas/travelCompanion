package com.lloydstsb.rest.validation.jsr303;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.Validation;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.hibernate.validator.method.MethodValidator;

import com.lloydstsb.rest.validation.InputValidator;

/**
 * Performs JSR303 validation of incoming method parameters.
 * 
 * Uses deprecated MethodValidator class which is due to be replaced in
 * Hibernate Validator 5.0. When this is released this should be refactored to
 * use whatever replaces it.
 * 
 * JAX-RS 2.0 will include JSR303 validation by default so when that is released
 * this class should cease to be necessary.
 * 
 * @author alex
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Jsr303InputValidator implements InputValidator {
	private MethodValidator validator;
	
	public Jsr303InputValidator() {
		validator = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory().getValidator().unwrap(MethodValidator.class);
	}

	public void validateInput(Object target, Method method, Object[] parameterValues) {
		Set violations = validator.validateAllParameters(target, method, parameterValues);

		if (!violations.isEmpty()) {
			throw new MethodConstraintViolationException("Could not validate incoming parameters", violations);
		}
	}
}
