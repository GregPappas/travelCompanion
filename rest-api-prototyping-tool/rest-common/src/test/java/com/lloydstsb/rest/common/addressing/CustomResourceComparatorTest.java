package com.lloydstsb.rest.common.addressing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.PathSegment;

import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomResourceComparatorTest {
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
		when(message.get(eq("HTTP.REQUEST"))).thenReturn(request);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void compareAndAssert(Class<?> clazzA, Class<?> clazzB, String url, String method, int expected) throws Exception {
		when(info1.getServiceClass()).thenReturn((Class) clazzA);
		when(info2.getServiceClass()).thenReturn((Class) clazzB);
		when(message.get("path_to_match_slash")).thenReturn(url);
		when(message.get(Message.HTTP_REQUEST_METHOD)).thenReturn(method);

		String[] parts = url.split("\\?");

		if (parts.length > 1) {
			when(message.get(Message.QUERY_STRING)).thenReturn(parts[1]);
		}

		int result = comparator.compare(info1, info2, message);

		assertEquals(expected, result);
	}

	@Test
	public void shouldChooseService1WhenGET() throws Exception {
		compareAndAssert(Service1Impl.class, Service2Impl.class, "/foo/bar", "GET", -1);
	}

	@Test
	public void shouldChooseService2WhenPOST() throws Exception {
		compareAndAssert(Service1Impl.class, Service2Impl.class, "/foo/bar", "POST", 1);
	}

	@Test
	public void shouldChooseNeitherService1NorService2WhenDELETE() throws Exception {
		compareAndAssert(Service1Impl.class, Service2Impl.class, "/foo/bar", "DELETE", 0);
	}

	@Test
	public void shouldChooseServiceAWhenGETorOPTIONS() throws Exception {
		compareAndAssert(ServiceAImpl.class, ServiceBImpl.class, "/bar", "GET", -1);
	}

	@Test
	public void shouldChooseServiceBWhenPOSTorOPTIONS() throws Exception {
		compareAndAssert(ServiceAImpl.class, ServiceBImpl.class, "/bar", "POST", 1);
	}

	@Test
	public void shouldChooseNeitherServiceANorServiceBWhenDELETE() throws Exception {
		compareAndAssert(ServiceAImpl.class, ServiceBImpl.class, "/bar", "DELETE", 0);
	}

	@Test
	public void shouldChooseServiceFooWhenPassingArgumentInPath() throws Exception {
		compareAndAssert(ServiceFooImpl.class, ServiceBarImpl.class, "/bar/thing", "GET", -1);
	}

	@Test
	public void shouldChooseServiceFooWhenPassingArgumentInPathAndMethodIsOPTIONS() throws Exception {
		compareAndAssert(ServiceFooImpl.class, ServiceBarImpl.class, "/bar/thing", "OPTIONS", -1);
	}

	@Test
	public void shouldChooseServiceBarWhenPassingArgumentAndResourceInPath() throws Exception {
		compareAndAssert(ServiceFooImpl.class, ServiceBarImpl.class, "/bar/thing/qux", "GET", 1);
	}

	@Test
	public void shouldChooseServiceBarWhenPassingArgumentAndResourceInPathAndMethodIsOPTIONS() throws Exception {
		compareAndAssert(ServiceFooImpl.class, ServiceBarImpl.class, "/bar/thing/qux", "OPTIONS", 1);
	}

	@Test
	public void shouldChooseServiceBazWhenPassingTwoArgumentsAndResourceInPath() throws Exception {
		compareAndAssert(ServiceBarImpl.class, ServiceBazImpl.class, "/bar/thing/qux/foo/bob", "GET", 1);
	}

	@Test
	public void shouldChooseServiceBazWhenPassingTwoArgumentsAndResourceInPathAndMethodIsOPTIONS() throws Exception {
		compareAndAssert(ServiceBarImpl.class, ServiceBazImpl.class, "/bar/thing/qux/foo/bob", "OPTIONS", 1);
	}

	@Test
	public void shouldChooseServiceWibbleWhenPassingTwoStringArguments() throws Exception {
		compareAndAssert(ServiceWibble.class, ServiceWobble.class, "/bar/bob?baz=foo&flux=garply", "GET", -1);
	}

	@Test
	public void shouldChooseServiceWobbleWhenPassingOneStringArguments() throws Exception {
		compareAndAssert(ServiceWibble.class, ServiceWobble.class, "/bar/bob?baz=foo", "GET", 1);
	}

	@Test
	public void shouldChooseServiceCWhenPassingNoArguments() throws Exception {
		compareAndAssert(ServiceC.class, ServiceD.class, "/wibble", "GET", -1);
	}

	@Test
	public void shouldChooseServiceDWhenPassingOneArgument() throws Exception {
		compareAndAssert(ServiceC.class, ServiceD.class, "/wibble/foo", "GET", 1);
	}

	@Test
	public void shouldChooseServiceDWhenPassingTwoArguments() throws Exception {
		compareAndAssert(ServiceC.class, ServiceD.class, "/wibble/foo/bar", "GET", 1);
	}

	@Test
	public void shouldChooseServiceWabbleWhenPassingTwoStringArguments() throws Exception {
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("baz", "foo");
		when(request.getParameterMap()).thenReturn(formParams);

		compareAndAssert(ServiceWabble.class, ServiceBobble.class, "/bar/bob?flux=garply", "POST", -1);
	}

	@Test
	public void shouldChooseServiceBobbleWhenPassingOneStringArguments() throws Exception {
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("baz", "foo");
		when(request.getParameterMap()).thenReturn(formParams);

		compareAndAssert(ServiceWabble.class, ServiceBobble.class, "/bar/bob", "POST", 1);
	}

	@Test
	public void shouldChooseServiceEWhenPassingOneMandatoryPathParamAndNoStringArguments() throws Exception {
		compareAndAssert(ServiceE.class, ServiceF.class, "/wibble/hoorah/bar", "GET", -1);
	}

	@Test
	public void shouldChooseServiceFWhenPassingOneMandatoryPathParamAndOneStringArgument() throws Exception {
		compareAndAssert(ServiceE.class, ServiceF.class, "/wibble/hoorah/bar?baz=hello", "GET", 1);
	}

	@Test
	public void shouldChooseServiceGWhenPassingPathParamWithDigitsOnly() throws Exception {
		compareAndAssert(ServiceG.class, ServiceH.class, "/wibble/123/bar", "GET", -1);
		compareAndAssert(ServiceH.class, ServiceG.class, "/wibble/123/bar", "GET", 1);
	}

	@Test
	public void shouldChooseServiceHWhenPassingPathParamWithNotJustDigitsDigits() throws Exception {
		compareAndAssert(ServiceG.class, ServiceH.class, "/wibble/1a23/bar", "GET", 1);
		compareAndAssert(ServiceH.class, ServiceG.class, "/wibble/1a23/bar", "GET", -1);
	}
	
	@Test
	public void shouldChooseServiceIWhenPassingOneHeaderParamOnly() throws Exception {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("me");
		headers.put("foo", values);
		when(message.get(Message.PROTOCOL_HEADERS)).thenReturn(headers);
		
		compareAndAssert(ServiceJ.class, ServiceI.class, "/wobble/bar", "GET", 1);
		compareAndAssert(ServiceI.class, ServiceJ.class, "/wobble/bar", "GET", -1);
	}
	
	@Test
	public void shouldChooseServiceJWhenPassingTwoHeaderParamOnly() throws Exception {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("me");
		headers.put("foo", values);
		headers.put("baz", values);
		when(message.get(Message.PROTOCOL_HEADERS)).thenReturn(headers);
		
		compareAndAssert(ServiceI.class, ServiceJ.class, "/wobble/bar", "GET", 1);
		compareAndAssert(ServiceJ.class, ServiceI.class, "/wobble/bar", "GET", -1);
	}	
	
	@Test
	public void shouldChooseServiceKWhenPassingHeaderAndPathParam() throws Exception {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("me");
		headers.put("foo", values);
		when(message.get(Message.PROTOCOL_HEADERS)).thenReturn(headers);

		compareAndAssert(ServiceJ.class, ServiceK.class, "/wobble/bar/baz", "GET", 1);
		compareAndAssert(ServiceK.class, ServiceJ.class, "/wobble/bar/baz", "GET", -1);
	}

	@Test
	public void shouldChooseServiceLWhenPassingHeaderAndFormParam() throws Exception {
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("me");
		headers.put("foo", values);
		when(message.get(Message.PROTOCOL_HEADERS)).thenReturn(headers);
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("baz", "foo");
		when(request.getParameterMap()).thenReturn(formParams);

		compareAndAssert(ServiceM.class, ServiceL.class, "/wobble/bar", "GET", 1);
		compareAndAssert(ServiceL.class, ServiceM.class, "/wobble/bar", "GET", -1);
	}

	@Test
	public void shouldChooseServiceMWhenPassingFormParamsOnly() throws Exception {
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("foo", "baz");
		formParams.put("baz", "foo");
		when(request.getParameterMap()).thenReturn(formParams);

		compareAndAssert(ServiceL.class, ServiceM.class, "/wobble/bar", "GET", 1);
		compareAndAssert(ServiceM.class, ServiceL.class, "/wobble/bar", "GET", -1);
	}

	@Test
	public void testInfiniteWildcardLoopFix() throws Exception {
		compareAndAssert(ServiceJ.class, ServiceK.class, "/wobble/bar/{baz}", "GET", 1);
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
		@POST
		public void doSomething();
	}

	private class Service2Impl implements Service2 {
		public void doSomething() {

		}
	}

	@Path("/bar")
	private interface ServiceA {

		@Path("")
		@GET
		public void doSomething();
	}

	private class ServiceAImpl implements ServiceA {
		public void doSomething() {

		}
	}

	@Path("/bar")
	private interface ServiceB {

		@Path("")
		@POST
		public void doSomething();
	}

	private class ServiceBImpl implements ServiceB {
		public void doSomething() {

		}
	}

	@Path("/bar")
	private interface ServiceFoo {

		@Path("{baz}")
		@GET
		public void doSomething(String baz);
	}

	private class ServiceFooImpl implements ServiceFoo {
		public void doSomething(String baz) {

		}
	}

	@Path("/bar")
	private interface ServiceBar {

		@Path("{baz}/qux")
		@GET
		public void doSomething(String baz);
	}

	private class ServiceBarImpl implements ServiceBar {
		public void doSomething(String baz) {

		}
	}

	@Path("/bar")
	private interface ServiceBaz {

		@Path("{baz}/qux/{flux}/bob")
		@GET
		public void doSomething(String baz, String flux);
	}

	private class ServiceBazImpl implements ServiceBaz {
		public void doSomething(String baz, String flux) {

		}
	}

	@Path("/bar")
	private class ServiceWibble {
		@Path("bob")
		@GET
		public void doSomething(@QueryParam("baz") String baz,

		@QueryParam("flux") String flux) {

		}
	}

	@Path("/bar")
	private class ServiceWobble {
		@Path("bob")
		@GET
		public void doSomething(@QueryParam("baz") String baz) {

		}
	}

	@Path("/bar")
	private class ServiceWabble {
		@Path("bob")
		@POST
		public void doSomething(@FormParam("baz") String baz,

		@QueryParam("flux") String flux) {

		}
	}

	@Path("/bar")
	private class ServiceBobble {
		@Path("bob")
		@POST
		public void doSomething(@FormParam("baz") String baz) {

		}
	}

	@Path("/wibble")
	private class ServiceC {
		@Path("")
		@GET
		public void doSomething() {

		}
	}

	@Path("/wibble")
	private class ServiceD {
		@Path("{things:.+}")
		@GET
		public void doSomething(@PathParam("things") List<PathSegment> things) {

		}
	}

	@Path("/wibble")
	private class ServiceE {
		@Path("{foo}/bar")
		@GET
		public void doSomething(@PathParam("foo") @NotNull String foo) {

		}
	}

	@Path("/wibble")
	private class ServiceF {
		@Path("{foo}/bar")
		@GET
		public void doSomething(@PathParam("foo") @NotNull String foo, @QueryParam("baz") String baz) {

		}
	}

	@Path("/wibble")
	private class ServiceG {
		@Path("{foo : \\d+}/bar")
		@GET
		public void doSomething(@PathParam("foo") @NotNull String foo) {

		}
	}

	@Path("/wibble")
	private class ServiceH {
		@Path("{foo}/bar")
		@GET
		public void doSomething(@PathParam("foo") @NotNull String foo) {

		}
	}
	
	@Path("/wobble")
	private class ServiceI {
		@Path("/bar")
		@GET
		public void doSomething(@HeaderParam("foo") @NotNull String foo) {

		}
	}

	@Path("/wobble")
	private class ServiceJ {
		@Path("/bar")
		@GET
		public void doSomething(@HeaderParam("foo") @NotNull String foo, @HeaderParam("baz") @NotNull String baz) {

		}
	}	
	
	@Path("/wobble")
	private class ServiceK {
		@Path("/bar/{baz}")
		@GET
		public void doSomething(@HeaderParam("foo") @NotNull String foo, @PathParam("baz") @NotNull String baz) {

		}
	}

	@Path("/wobble")
	private class ServiceL {
		@Path("/bar")
		@GET
		public void doSomething(@HeaderParam("foo") @NotNull String foo, @FormParam("baz") @NotNull String baz) {

		}
	}

	@Path("/wobble")
	private class ServiceM {
		@Path("/bar")
		@GET
		public void doSomething(@FormParam("foo") @NotNull String foo, @FormParam("baz") @NotNull String baz) {

		}
	}

}
