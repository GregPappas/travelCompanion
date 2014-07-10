package com.lloydstsb.rest.common.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.lloydstsb.rest.common.addressing.CustomResourceComparator;

@RunWith(MockitoJUnitRunner.class)
public class PATCHTtest {
	private CustomResourceComparator comparator = new CustomResourceComparator();

	@Mock
	private Message message;

	@Mock
	private ClassResourceInfo info1;

	@Mock
	private ClassResourceInfo info2;

	@Mock
	private Exchange exchange;

	@Mock
	private HttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		when(message.getExchange()).thenReturn(exchange);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldSelectPatchAnnotatedMethod() throws Exception {
		when(info1.getServiceClass()).thenReturn((Class) Service1Impl.class);
		when(info2.getServiceClass()).thenReturn((Class) Service2Impl.class);
		when(message.get("path_to_match_slash")).thenReturn("/foo/bar");
		when(message.get(Message.HTTP_REQUEST_METHOD)).thenReturn("PATCH");
		when(message.get("HTTP.REQUEST")).thenReturn(request);

		int result = comparator.compare(info1, info2, message);

		assertEquals(1, result);
	}

	@Path("/foo")
	private interface Service1 {

		@Path("/bar")
		@GET
		public void doSomething();
	}

	private class Service1Impl implements Service1 {
		public void doSomething() {

		}
	}

	@Path("/foo")
	private interface Service2 {

		@Path("/bar")
		@PATCH
		public void doSomething();
	}

	private class Service2Impl implements Service2 {
		public void doSomething() {

		}
	}
}
