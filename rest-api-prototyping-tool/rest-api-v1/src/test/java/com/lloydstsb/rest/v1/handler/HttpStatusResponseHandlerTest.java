/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.handler;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.Oneway;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.MessageImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 26/02/2013 12:49
 */
public class HttpStatusResponseHandlerTest {
	private HttpStatusResponseHandler handler = new HttpStatusResponseHandler();

	@Mock
	OperationResourceInfo ori;

	private Response response = Response.ok().build();
	private MessageImpl message = new MessageImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void voidDeleteMethod() throws Exception {
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("voidDeleteMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertEquals(204, amendedResponse.getStatus());
	}

	@Test
	public void voidPostMethod() throws Exception {
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("voidPostMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertEquals(201, amendedResponse.getStatus());
	}

	@Test
	public void intMethod() throws Exception {
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("intMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertEquals(200, amendedResponse.getStatus());
	}

	@Test
	public void oneWayMethod() throws Exception {
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("oneWayMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertEquals(202, amendedResponse.getStatus());
	}
	
	@Test
	public void voidDeleteMethodWithAcceptedResponse() throws Exception {
		response = Response.status(202).build();
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("voidDeleteMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertNull(amendedResponse);
	}
	
	@Test
	public void voidPostMethodWithAcceptedResponse() throws Exception {
		response = Response.status(202).build();
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("voidPostMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertNull(amendedResponse);
	}
	
	@Test
	public void intMethodWithAcceptedResponse() throws Exception {
		response = Response.status(202).build();
		when(ori.getAnnotatedMethod()).thenReturn(TestInterface.class.getMethod("intMethod"));
		final Response amendedResponse = handler.handleResponse(message, ori, response);
		assertNull(amendedResponse);
	}

	@Test
	public void testErrorCode() throws Exception {
		final Response amendedResponse = handler.handleResponse(message, ori, Response.serverError().build());
		assertNull(amendedResponse);
	}

	private interface TestInterface
	{
		@DELETE
		public void voidDeleteMethod();
		@POST
		public void voidPostMethod();

		@GET
		public int intMethod();

		@Oneway
		@DELETE
		public void oneWayMethod();
	}
}
