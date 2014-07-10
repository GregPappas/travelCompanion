package com.lloydstsb.rest.validation;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;

import javax.validation.ValidationException;

import org.apache.cxf.message.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ValidatingJaxRsInvokerTest {
	private ValidatingJaxRsInvoker invoker;

	@Mock
	private InputValidator inputValidator;

	@Mock
	private OutputValidator outputValidator;

	@Mock
	private Exchange exchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		invoker = new ValidatingJaxRsInvoker();
		invoker.setInputValidator(inputValidator);
		invoker.setOutputValidator(outputValidator);
	}

	@Test
	public void testPerformInvocation() throws Exception {
		Object service = new Object();
		Method method = service.getClass().getDeclaredMethod("toString");
		Object[] parameters = new Object[] {};

		// the method under test
		invoker.performInvocation(exchange, service, method, parameters);

		// make sure we've validated the input and output
		verify(inputValidator, times(1)).validateInput(eq(service), eq(method), eq(parameters));
		verify(outputValidator, times(1)).validateOutput(anyString());
	}
	
	@Test
	public void testPerformInvocation_throwInputValidationException() throws Exception {
		Object service = new Object();
		Method method = service.getClass().getDeclaredMethod("toString");
		Object[] parameters = new Object[] {};
		
		// make like our input is invalid
		doThrow(new ValidationException()).when(inputValidator).validateInput(anyObject(), any(Method.class), any(Object[].class));

		try {
			// the method under test
			invoker.performInvocation(exchange, service, method, parameters);
			
			fail("ValidationException was not thrown...");
		} catch(ValidationException e) {
			// expected
		}

		// make sure we've validated the input...
		verify(inputValidator, times(1)).validateInput(eq(service), eq(method), eq(parameters));
		
		// ... but not the output
		verify(outputValidator, never()).validateOutput(anyString());
	}
}
