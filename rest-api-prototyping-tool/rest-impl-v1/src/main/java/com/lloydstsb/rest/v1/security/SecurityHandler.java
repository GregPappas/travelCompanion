package com.lloydstsb.rest.v1.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.interceptor.security.AbstractAuthorizingInInterceptor;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.SessionValues;
import com.lloydstsb.rest.v1.exceptions.AuthorisationException;
import com.lloydstsb.rest.v1.exceptions.AuthorisationExceptionMapper;
import com.lloydstsb.rest.v1.exceptions.NotLoggedInException;

import static com.lloydstsb.rest.v1.exceptions.ImplIbErrorCode.errorDescCode;

@Component
@Provider
public class SecurityHandler implements RequestHandler {

	private static final Pattern REGEX_PATTERN = Pattern
			.compile(".+session.*");

	private AbstractAuthorizingInInterceptor interceptor;
	private AuthorisationExceptionMapper authorisationExceptionMapper = new AuthorisationExceptionMapper();

	public Response handleRequest(Message message,
			ClassResourceInfo resourceClass) {
		try {
			HttpServletRequest request = (HttpServletRequest) message
					.get("HTTP.REQUEST");
			String uri = (String) message.get(Message.REQUEST_URL);
			if (isAuthenticationUri(uri)) {
				return null;
			}
			if (request.getSession(false) == null
					|| request.getSession(false).getAttribute(
							SessionValues.AUTHENTICATED_CUSTOMER.toString()) == null) {
				throw new NotLoggedInException(errorDescCode("Please login", "000"));
			}
			if (interceptor == null) {
				return null;
			}
			interceptor.handleMessage(message);
			return null;
		} catch (AuthorisationException exception) {
			return authorisationExceptionMapper.toResponse(exception);
		}
	}

	public boolean isAuthenticationUri(String input) {
		Matcher matcher = REGEX_PATTERN.matcher(input);
		return matcher.matches();
	}

	public void setInterceptor(AbstractAuthorizingInInterceptor in) {
		interceptor = in;
	}
}
