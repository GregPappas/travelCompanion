package com.lloydstsb.rest.common.addressing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.ext.ResourceComparator;
import org.apache.cxf.jaxrs.impl.HttpHeadersImpl;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.common.annotation.PATCH;

@Component
public class CustomResourceComparator implements ResourceComparator {
	private final String HTTP_REQUEST = "HTTP.REQUEST";
	private final String PATH_SEPERATOR = "/";

	/**
	 * Should return -1 if arg0 has a method that can service the invocation
	 * contained within message, 1 if arg1 has a method that can service the
	 * invocation, otherwise return 0 if neither can service the invocation.
	 */
	public int compare(ClassResourceInfo arg0, ClassResourceInfo arg1, Message message) {
		UriInfo uriInfo = new UriInfoImpl(message);
		HttpHeaders headerInfo = new HttpHeadersImpl(message);
		String requestedPath = uriInfo.getPath();
		Object requestMethod = message.get(Message.HTTP_REQUEST_METHOD);
		Class<? extends Annotation> requestedMethodType = getHttpMethodAnnotationClass(requestMethod.toString());
		HttpServletRequest request = (HttpServletRequest) message.get(HTTP_REQUEST);

		Map<Class<?>, Map<String, List<String>>> parameters = findParameters(uriInfo, headerInfo, request);

		List<MethodMatch> matches = new LinkedList<MethodMatch>();

		// service methods will only match if they service the requested URL
		addMatches(matches, arg0.getServiceClass(), requestedPath, requestedMethodType, parameters);
		addMatches(matches, arg1.getServiceClass(), requestedPath, requestedMethodType, parameters);

		// if we have two competing service classes (e.g. same URL, different
		// arguments), make them fight!
		MethodMatch bestMatch = null;
		int totalParametersSent = countParameters(parameters);
		int totalParametersMatched = -1;
		int regexParametersMatched = -1;

		for (MethodMatch match : matches) {
			if (bestMatch == null) {
				// initially take the first item in the list
				bestMatch = match;
			}

			if (match.getTotalArguments() == totalParametersSent && match.getMatchedArguments() == match.getTotalArguments()) {

				if (match.getRegexMatches() > regexParametersMatched || match.getMatchedArguments() > totalParametersMatched) {
					// total coverage of arguments
					bestMatch = match;
					totalParametersMatched = match.getMatchedArguments();
					regexParametersMatched = match.getRegexMatches();
				}
			} else if (match.getMatchedMandatoryArguments() == match.getMandatoryArguments()) {

				if (match.getRegexMatches() > regexParametersMatched || match.getMatchedMandatoryArguments() > totalParametersMatched) {
					// matched all mandatory arguments
					bestMatch = match;
					totalParametersMatched = match.getMatchedMandatoryArguments();
					regexParametersMatched = match.getRegexMatches();
				}
			}
		}

		if (bestMatch != null) {
			if (bestMatch.getContainingClass().equals(arg0.getServiceClass())) {
				return -1;
			} else if (bestMatch.getContainingClass().equals(arg1.getServiceClass())) {
				return 1;
			}
		}

		return 0;
	}

	private Map<Class<?>, Map<String, List<String>>> findParameters(UriInfo uriInfo, HttpHeaders headerInfo, HttpServletRequest request) {
		MultivaluedMap<String, String> pathParams = uriInfo.getPathParameters(true);
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters(true);
		MultivaluedMap<String, String> headerParams = headerInfo.getRequestHeaders();
		Map<String, List<String>> formParams = new HashMap<String, List<String>>();

		for (Object key : request.getParameterMap().keySet()) {
			if (queryParams.containsKey(key)) {
				// HttpServletRequest.getParameterMap also contains query
				// parameters so ignore them..
				continue;
			}

			List<String> value = new ArrayList<String>();
			value.add(request.getParameter(key.toString()));

			formParams.put(key.toString(), value);
		}

		Map<Class<?>, Map<String, List<String>>> output = new HashMap<Class<?>, Map<String, List<String>>>();

		output.put(FormParam.class, formParams);
		output.put(PathParam.class, pathParams);
		output.put(QueryParam.class, queryParams);
		output.put(HeaderParam.class, headerParams);

		return output;
	}

	private int countParameters(Map<Class<?>, Map<String, List<String>>> parameters) {
		int total = 0;

		for (Map<String, List<String>> values : parameters.values()) {
			total += values.size();
		}

		return total;
	}

	protected void addMatches(List<MethodMatch> matches, Class<?> clazz, String requestedPath, Class<? extends Annotation> requestedHttpMethod, Map<Class<?>, Map<String, List<String>>> parameters) {
		for (Class<?> interfaze : clazz.getInterfaces()) {
			findAndAddMatches(matches, clazz, interfaze, requestedPath, requestedHttpMethod, parameters);
		}

		findAndAddMatches(matches, clazz, clazz, requestedPath, requestedHttpMethod, parameters);
	}

	protected void findAndAddMatches(List<MethodMatch> matches, Class<?> serviceClass, Class<?> clazz, String requestedPath, Class<? extends Annotation> requestedHttpMethod, Map<Class<?>, Map<String, List<String>>> parameters) {
		if (!requestedPath.startsWith(PATH_SEPERATOR)) {
			requestedPath = PATH_SEPERATOR + requestedPath;
		}

		if (requestedPath.contains("?")) {
			String[] parts = requestedPath.split("\\?");
			requestedPath = parts[0];
		}

		String path = new String();
		Path pathAnnotation = clazz.getAnnotation(Path.class);

		// class has @Path annotation
		if (pathAnnotation != null) {
			path += pathAnnotation.value();

			if (!path.startsWith(PATH_SEPERATOR)) {
				path = PATH_SEPERATOR + requestedPath;
			}

			// /foo/bar was requested but class is mapped to /bar
			if (!requestedPath.startsWith(path)) {
				return;
			}
		}

		for (Method method : clazz.getMethods()) {
			// skip over this method if it doesn't have the right http method
			// type, unless it's OPTIONS
			if (requestedHttpMethod != OPTIONS.class && method.getAnnotation(requestedHttpMethod) == null) {
				continue;
			}

			pathAnnotation = method.getAnnotation(Path.class);

			if (pathAnnotation == null) {
				// method does not have @Path annotation
				continue;
			}

			String methodPath = pathAnnotation.value();

			// class is annotated with with requested path and this method is
			// annotated with @Path("")
			if (methodPath.trim().equals("") && !path.equals(requestedPath)) {
				continue;
			}

			if (!methodPath.startsWith(PATH_SEPERATOR) && !path.endsWith(PATH_SEPERATOR) && !methodPath.trim().equals("")) {
				path += PATH_SEPERATOR;
			}

			path += methodPath;

			boolean matchesPath = path.equals(requestedPath);

			if ((methodPath.indexOf("{") > -1 && matchWildcard(requestedPath, path))) {
				matchesPath = true;

				updatePathParameters(path, requestedPath, parameters);
			}

			// We've found a potential match
			if (matchesPath) {
				MethodMatch match = new MethodMatch();
				match.setMethod(method);
				match.setContainingClass(serviceClass);

				coversHowManyArguments(method, match, parameters);

				matches.add(match);
			}
		}
	}

	private void updatePathParameters(String path, String requestedPath, Map<Class<?>, Map<String, List<String>>> parameters) {

		Map<String, List<String>> regexParameters = new HashMap<String, List<String>>();
		parameters.put(Pattern.class, regexParameters);

		Map<String, List<String>> pathParameters = parameters.get(PathParam.class);

		// matches any : not preceded by a \ e.g. will match "foo : bar" but
		// not "foo \: bar"
		Pattern regexMatcherPattern = Pattern.compile("(?<!\\\\)\\:");

		// matches between { and } ignoring \} e.g. will match
		// "foo : .\{3\}" in "{foo : .\{3\}}" and
		// "wibble" in "{wibble}"
		Pattern pattern = Pattern.compile("\\{(.*?)(?<!\\\\)\\}");
		Matcher matcher = pattern.matcher(path);

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();

			String parameterName = path.substring(start + 1, end - 1);
			String parameterValue = requestedPath.substring(start);

			// truncate only if the path parameter is not at the end of the path
			if (parameterValue.indexOf(PATH_SEPERATOR) > -1) {
				parameterValue = parameterValue.substring(0, parameterValue.indexOf(PATH_SEPERATOR));
			}

			Matcher containsRegex = regexMatcherPattern.matcher(parameterName);

			// The parameter name contains a regex - attempt to extract the
			// value from the path
			if (containsRegex.find()) {
				String pathRegexPattern = parameterName.substring(containsRegex.end()).trim();

				if (!Pattern.matches(pathRegexPattern, parameterValue)) {
					// accept only complete matches on value
					continue;
				}

				parameterName = parameterName.substring(0, containsRegex.start()).trim();

				List<String> parameter = new ArrayList<String>();
				parameter.add(parameterValue);

				regexParameters.put(parameterName, parameter);
			}

			List<String> parameter = new ArrayList<String>();
			parameter.add(parameterValue);

			pathParameters.put(parameterName, parameter);
		}
	}

	private void coversHowManyArguments(Method method, MethodMatch match, Map<Class<?>, Map<String, List<String>>> parameters) {
		int mandatoryArguments = 0;
		int matchedMandatoryArguments = 0;
		int matchedArguments = 0;
		int totalArguments = 0;

		// look at every annotation
		for (Annotation[] parameterAnnotations : method.getParameterAnnotations()) {
			boolean isMandatory = false;
			boolean matchedArgument = false;
			boolean isArgument = false;

			for (Annotation annotation : parameterAnnotations) {
				if (annotation instanceof PathParam) {
					isArgument = true;

					if (matchesParameterName(parameters, PathParam.class, ((PathParam) annotation).value())) {
						matchedArgument = true;
					}
				}

				else if (annotation instanceof QueryParam) {
					isArgument = true;

					if (matchesParameterName(parameters, QueryParam.class, ((QueryParam) annotation).value())) {
						matchedArgument = true;
					}
				}

				else if (annotation instanceof FormParam) {
					isArgument = true;

					if (matchesParameterName(parameters, FormParam.class, ((FormParam) annotation).value())) {
						matchedArgument = true;
					}
				}

				else if (annotation instanceof HeaderParam) {
					isArgument = true;

					if (matchesParameterName(parameters, HeaderParam.class, ((HeaderParam) annotation).value())) {
						matchedArgument = true;
					}
				}

				else if (annotation instanceof NotNull) {
					isArgument = true;
					isMandatory = true;
				}
			}

			if (isArgument) {
				totalArguments++;
			}

			if (matchedArgument) {
				matchedArguments++;
			}

			if (isMandatory) {
				mandatoryArguments++;

				if (matchedArgument) {
					matchedMandatoryArguments++;
				}
			}
		}

		match.setMandatoryArguments(mandatoryArguments);
		match.setMatchedArguments(matchedArguments);
		match.setMatchedMandatoryArguments(matchedMandatoryArguments);
		match.setTotalArguments(totalArguments);
		match.setRegexMatches(parameters.containsKey(Pattern.class) ? parameters.get(Pattern.class).size() : 0);
	}

	private boolean matchesParameterName(Map<Class<?>, Map<String, List<String>>> parameters, Class<?> annotationClass, String name) {
		Map<String, List<String>> parameterList = parameters.get(annotationClass);

		if (parameterList == null) {
			return false;
		}

		return parameterList.containsKey(name);
	}

	public int compare(OperationResourceInfo arg0, OperationResourceInfo arg1, Message arg2) {
		return 0;
	}

	private Class<? extends Annotation> getHttpMethodAnnotationClass(String method) {
		method = method.toUpperCase();

		if (method.equals(HttpMethod.GET)) {
			return GET.class;
		}

		if (method.equals(HttpMethod.POST)) {
			return POST.class;
		}

		if (method.equals(HttpMethod.PUT)) {
			return PUT.class;
		}

		if (method.equals(HttpMethod.DELETE)) {
			return DELETE.class;
		}

		if (method.equals(HttpMethod.OPTIONS)) {
			return OPTIONS.class;
		}

		if (method.equals(HttpMethod.HEAD)) {
			return HEAD.class;
		}

		if (method.equals(PATCH.PATCH)) {
			return PATCH.class;
		}

		throw new RuntimeException("Unsupported HTTP method type " + method);
	}

	private boolean matchWildcard(String requestedPath, String wildcardPath) {
		// handle multiple arguments
		if (wildcardPath.endsWith(":.+}") || wildcardPath.endsWith(":.*}")) {
			// the idea here is that /foo/bar/baz should match
			// /foo/{someArguments:.*}
			// so we turn /foo/{someArguments:.*} into /foo/, then ask if
			// /foo/bar/baz starts with /foo/ as bar/baz will get turned into
			// a List<PathSegment> of arguments by JAX-RS
			wildcardPath = wildcardPath.replaceAll("\\{.*:\\.[\\+\\*]\\}", "");

			return requestedPath.startsWith(wildcardPath);
		}

		// replace all the wildcards in the method path with tokens from the
		// requested path
		while (wildcardPath.indexOf('{') != -1) {
			int startWildcardIndex = wildcardPath.indexOf('{');
			int endWildcardIndex = wildcardPath.indexOf('}');
			int lastSlashIndex = requestedPath.indexOf('/', startWildcardIndex);

			if (lastSlashIndex == -1) {
				lastSlashIndex = requestedPath.length();
			}

			String wildcardToken = wildcardPath.substring(startWildcardIndex, endWildcardIndex + 1);

			if (startWildcardIndex > requestedPath.length()) {
				// first wildcard occurs after end of requested path, cannot be
				// this method so return...
				return false;
			}

			String replacementText = requestedPath.substring(startWildcardIndex, lastSlashIndex);
			// Replacement text is not allowed to contain '{' otherwise we'll loop forever
			if (replacementText.indexOf('{') != -1) {
				return false;
			}
			wildcardPath = wildcardPath.replace(wildcardToken, replacementText);
		}

		return requestedPath.equals(wildcardPath);
	}
}
