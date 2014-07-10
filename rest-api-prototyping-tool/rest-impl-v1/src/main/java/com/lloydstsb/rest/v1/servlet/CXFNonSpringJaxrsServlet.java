package com.lloydstsb.rest.v1.servlet;

import javax.servlet.ServletConfig;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import com.lloydstsb.rest.validation.ValidatingJaxRsInvoker;

/**
 * This class exists to set our custom invoker on JAXRSServerFactoryBean. If we
 * were using Spring we could set it through configuration using the
 * jaxrs:invoker extension, but there you go.
 * 
 * See
 * http://cxf.apache.org/docs/jax-rs-filters.html#JAX-RSFilters-Custominvokers
 * for more information.
 * 
 * The custom invoker performs JSR303 validation on incoming method arguments
 * and outgoing return values.
 * 
 * JAX-RS 2.0 will include JSR303 validation by default so when that is released
 * this class should cease to be necessary.
 * 
 * @author alex
 */
public class CXFNonSpringJaxrsServlet extends org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet {
	private static final long serialVersionUID = 1L;

	protected void setExtensions(JAXRSServerFactoryBean bean, ServletConfig servletConfig) {
		super.setExtensions(bean, servletConfig);

		bean.setInvoker(new ValidatingJaxRsInvoker());
	}
}
