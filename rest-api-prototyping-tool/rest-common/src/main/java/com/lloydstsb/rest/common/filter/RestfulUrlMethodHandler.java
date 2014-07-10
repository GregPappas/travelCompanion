/***********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: GetArrangementByIdServiceImpl
 *   
 * Author(s): REST API Team
 *  
 * Date: 31 Oct 2012
 * 
 ***********************************************************************/
package com.lloydstsb.rest.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Component;

@Component
@Provider
public class RestfulUrlMethodHandler implements RequestHandler {
	
	private static final String HTTP_REQUEST = "HTTP.REQUEST";
	
	private RestfulUrlMethodTransformer restfulUrlMethodTransformer = new RestfulUrlMethodTransformer();

	public Response handleRequest(Message message, ClassResourceInfo resourceClass) {
		HttpServletRequest request = (HttpServletRequest) message.get(HTTP_REQUEST);
		ServletRequest wrappedRequest = restfulUrlMethodTransformer.checkForMethodParameterAndWrapRequest(request);
		message.put(HTTP_REQUEST, wrappedRequest);
		return null;
	}
}
