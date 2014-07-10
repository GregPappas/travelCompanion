package com.lloydstsb.rest.common.exceptions;

import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
	//private static final Logger LOG = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);
	
	@Context
	private ServletContext servletContext;

	public Response toResponse(WebApplicationException exception) {
		//servletContext.log(msg)
		
		servletContext.log("WebApplicationException encountered!", exception);

		String message = "Unknown error.";

		try {
			HttpStatus status = HttpStatus.valueOf(exception.getResponse().getStatus());
			message = status.getReasonPhrase();
		} catch (IllegalArgumentException e) {

		}

		return Response.status(exception.getResponse().getStatus()).entity(exception.getResponse().getStatus() + " - " + message + " " + (exception.getMessage() == null ? "" : exception.getMessage())).type("text/plain").build();
	}
}
