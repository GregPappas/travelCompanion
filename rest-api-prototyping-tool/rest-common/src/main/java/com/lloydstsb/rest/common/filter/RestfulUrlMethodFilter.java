package com.lloydstsb.rest.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RestfulUrlMethodFilter implements Filter {

	private RestfulUrlMethodTransformer restfulUrlMethodTransformer = new RestfulUrlMethodTransformer();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		ServletRequest wrappedRequest = restfulUrlMethodTransformer.checkForMethodParameterAndWrapRequest(request);
		filterChain.doFilter(wrappedRequest, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
