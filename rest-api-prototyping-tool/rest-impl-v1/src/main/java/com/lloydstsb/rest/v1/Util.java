package com.lloydstsb.rest.v1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
	
	public static String getAuthenticatedCustomerId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session.getAttribute(SessionValues.AUTHENTICATED_CUSTOMER.toString()).toString();
	}
}
