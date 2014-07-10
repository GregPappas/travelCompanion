package com.lloydstsb.rest.validation;

import java.lang.reflect.Method;

import org.apache.cxf.jaxrs.JAXRSInvoker;
import org.apache.cxf.message.Exchange;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.validation.jsr303.Jsr303InputValidator;
import com.lloydstsb.rest.validation.jsr303.Jsr303OutputValidator;

/**
 * Delegates to InputValidator and OutputValidators to perform validation of
 * passed and returned data.
 * 
 * JAX-RS 2.0 will include JSR303 validation by default so when that is released
 * this class should cease to be necessary.
 * 
 * @author alex
 */
@Component
public class ValidatingJaxRsInvoker extends JAXRSInvoker {
	private InputValidator inputValidator = new Jsr303InputValidator();
	private OutputValidator outputValidator = new Jsr303OutputValidator();

	protected Object performInvocation(Exchange exchange, Object service, Method method, Object[] parameters) throws Exception {
		inputValidator.validateInput(service, method, parameters);

		Object output = super.performInvocation(exchange, service, method, parameters);

		outputValidator.validateOutput(output);

		return output;
	}

	public void setInputValidator(InputValidator inputValidator) {
		this.inputValidator = inputValidator;
	}

	public void setOutputValidator(OutputValidator outputValidator) {
		this.outputValidator = outputValidator;
	}
}
