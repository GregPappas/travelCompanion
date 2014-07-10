package com.lloydstsb.rest.v1.helpers;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.data.ErrorHelper;


@Provider
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {
	
	private ErrorHelper errorHelper = new ErrorHelper();
	
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response toResponse(Throwable ex) {

		Error e =errorHelper.getUnexpectedError(); 
	return Response.status(Integer.parseInt(e.getCode())).entity(e).build();
  }
}