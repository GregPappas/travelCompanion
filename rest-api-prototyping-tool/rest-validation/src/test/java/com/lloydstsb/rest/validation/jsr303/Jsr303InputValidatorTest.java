package com.lloydstsb.rest.validation.jsr303;

import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.method.MethodConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class Jsr303InputValidatorTest {
	private Jsr303InputValidator validator;
	private DummyService service;

	@Before
	public void setUp() throws Exception {
		validator = new Jsr303InputValidator();
		service = new DummyService();
	}

	@Test
	public void testValidateInput_validInput() throws Exception {
		Method method = DummyService.class.getDeclaredMethod("aMethod", String.class);
		Object[] args = new Object[] { "foo" };

		try {
			validator.validateInput(service, method, args);
		} catch (MethodConstraintViolationException e) {
			fail("Threw MethodConstraintViolationException when non-null object was passed as an argument annotated with @NotNull");
		}
	}

	@Test
	public void testValidateInput_invalidInput() throws Exception {
		Method method = DummyService.class.getDeclaredMethod("aMethod", String.class);
		Object[] args = new Object[] { null };

		try {
			validator.validateInput(service, method, args);

			fail("Did not throw MethodConstraintViolationException when null was passed as an argument annotated with @NotNull");
		} catch (MethodConstraintViolationException e) {

		}
	}

	protected class DummyService {
		public void aMethod(@NotNull String anArgument) {

		}
	}
}
