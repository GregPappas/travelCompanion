package com.lloydstsb.rest.common.filter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.HttpMethod;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.model.URITemplate;

import com.lloydstsb.rest.common.annotation.PATCH;

public class RestfulUrlMethodTransformer {
	
	public RestfulUrlMethodTransformer(){
		permittedVerbs.add(HttpMethod.GET);
		permittedVerbs.add(HttpMethod.POST);
		permittedVerbs.add(HttpMethod.PUT);
		permittedVerbs.add(HttpMethod.DELETE);
		permittedVerbs.add(HttpMethod.OPTIONS);
		permittedVerbs.add(PATCH.PATCH);
	}
	
	public static final String DEFAULT_METHOD_PARAM = "_method";
	private static final List<String> permittedVerbs = new ArrayList<String>();
	
	public ServletRequest checkForMethodParameterAndWrapRequest(ServletRequest request) {
		String method = request.getParameter(DEFAULT_METHOD_PARAM);

		if (!StringUtils.isEmpty(method)) {
			method = method.toUpperCase();

			if (permittedVerbs.contains(method)) {
				request = new HttpMethodRequestWrapper(method, (HttpServletRequest) request);
			}
		}
		return request;	
	}
	
	/**
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied
	 * method for {@link HttpServletRequest#getMethod()}.
	 */
	private class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;
		private final String URI;
		private final StringBuffer URL;

		public HttpMethodRequestWrapper(String method, HttpServletRequest httpServletRequest) {
			super(httpServletRequest);
			if (containsTemplate(httpServletRequest)) {
				Map<String, String> mapOfParamtersWithRealActualValues = getMapOfParamtersWithStringValues(httpServletRequest);

				String sanitisedURI = urlEncodeTrickyCharacters(unEncodeCurlyBracesIfEncodedByFirewall(httpServletRequest.getRequestURI()));
				URITemplate uriTemplate = new URITemplate(sanitisedURI);
				this.URI = urlEncodeTrickyCharacters(uriTemplate.substitute(mapOfParamtersWithRealActualValues));

				String sanitisedUrl = urlEncodeTrickyCharacters(unEncodeCurlyBracesIfEncodedByFirewall(httpServletRequest.getRequestURL().toString()));
				URITemplate urlTemplate = new URITemplate(sanitisedUrl);
				this.URL = new StringBuffer(urlEncodeTrickyCharacters(urlTemplate.substitute(mapOfParamtersWithRealActualValues)));
			} else {
				this.URI = httpServletRequest.getRequestURI();
				this.URL = httpServletRequest.getRequestURL();
			}
			this.method = method;
		}

		private boolean containsTemplate(HttpServletRequest httpServletRequest) {
			return httpServletRequest.getRequestURI().indexOf("{") != -1 || httpServletRequest.getRequestURI().indexOf("%7B") != -1 ;
		}

		@SuppressWarnings("rawtypes")
		private Map<String, String> getMapOfParamtersWithStringValues(HttpServletRequest httpServletRequest) {
			Map<String, String> paramters = new HashMap<String, String>();
			Enumeration parameterNames = httpServletRequest.getParameterNames();
			while(parameterNames.hasMoreElements()){
				 String parameter = parameterNames.nextElement().toString();
				 paramters.put(parameter, httpServletRequest.getParameter(parameter));
			}
			return paramters;
		}
		
		private String unEncodeCurlyBracesIfEncodedByFirewall(String url){
			String result = url;
			result = result.replaceAll("%7B", "{");
			result = result.replaceAll("%7D", "}");
			return result;
		}

		private String urlEncodeTrickyCharacters(String url) {
			return url.replaceAll("\\|", "%7C");
		}

		public String getRequestURI() {
			return this.URI;
		}

		public StringBuffer getRequestURL() {
			return this.URL;
		}

		public String getMethod() {
			return this.method;
		}
	}
}
