package com.lloydstsb.rest.validation.jsr303;

import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.lloydstsb.rest.validation.OutputValidator;

/**
 * Performs JSR303 validation of outgoing return value.
 * 
 * JAX-RS 2.0 will include JSR303 validation by default so when that is released
 * this class should cease to be necessary.
 * 
 * @author alex
 */
public class Jsr303OutputValidator implements OutputValidator {
	private Validator validator;

	public Jsr303OutputValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void validateOutput(Object output) {
		if (output == null) {
			return;
		}

		// handle collections
		if (output instanceof Collection) {
			validateCollection((Collection<Object>) output);

			return;
		}

		// handle arrays
		if (output instanceof Object[]) {
			validateArray((Object[]) output);

			return;
		}

		Set violations = validator.validate(output);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException("Could not validate outgoing object " + output.getClass(), violations);
		}
	}

	protected void validateCollection(Collection<Object> output) {
		for (Object o : output) {
			validateOutput(o);
		}
	}

	protected void validateArray(Object[] output) {
		for (Object o : output) {
			validateOutput(o);
		}
	}
}
