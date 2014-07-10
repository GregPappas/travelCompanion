/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.handler;

import java.lang.reflect.Method;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.Oneway;
import org.apache.cxf.jaxrs.ext.ResponseHandler;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 25/02/2013 17:18
 */
@Component
@Provider
public class HttpStatusResponseHandler implements ResponseHandler {

	private static final int LAST_HTTP_OK_CODE = 299;

	public Response handleResponse(final Message m, final OperationResourceInfo ori, final Response response) {
		if (ori == null || 
				m.containsKey(Message.RESPONSE_CODE) || 
				response.getStatus() > LAST_HTTP_OK_CODE || 
				response.getStatus() == Response.Status.ACCEPTED.getStatusCode()) {
			return null;
		}
		return Response.fromResponse(response)
				.status(defaultHttpStatusCode(ori))
				.build();
	}

	public Response.Status defaultHttpStatusCode(final OperationResourceInfo ori)
	{
		Class<?> returnType = getMethod(ori).getReturnType();
		Method method = getMethod(ori);
		boolean isVoid = void.class == returnType;
		boolean oneway = method.getAnnotation(Oneway.class) != null;
		boolean post = method.getAnnotation(POST.class) != null;
		if (post) {
			return Response.Status.CREATED;
		} else if (oneway) {
			return Response.Status.ACCEPTED;
		} else if (isVoid) {
			return Response.Status.NO_CONTENT;
		} else {
			return Response.Status.OK;
		}
	}

	private Method getMethod(OperationResourceInfo ori) {
		Method annMethod = ori.getAnnotatedMethod();
		return annMethod != null ? annMethod : ori.getMethodToInvoke();
	}
}
