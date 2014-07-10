package com.lloydstsb.rest.documentation;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.lloydstsb.rest.util.ObjectNameParser;
import com.lloydstsb.rest.util.populator.ObjectPopulator;

public class ViewRequestObjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CLASSNAME_PARAMETER = "class";
	private static final String RESPONSE_TYPE_XML = ".xml";
	private static final String RESPONSE_TYPE_JSON = ".json";
	private ObjectNameParser objectNameParser = new ObjectNameParser();
	private ObjectPopulator objectPopulator = new ObjectPopulator();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String lookup = request.getParameter(CLASSNAME_PARAMETER);

			Integer argumentIndex = findArgumentIndex(lookup);
			Class<?> clazz = findClass(lookup);
			Method method = findMethod(clazz, lookup, argumentIndex);

			checkDocumentationParameterIsPresent(method, argumentIndex, lookup, response);

			Class<?> parameter = method.getParameterTypes()[argumentIndex];
			Annotation[] annotations = method.getParameterAnnotations()[argumentIndex];
			Type type = method.getGenericParameterTypes()[argumentIndex];

			Object object = objectPopulator.populate(parameter, annotations, type);

			if (request.getRequestURI().endsWith(RESPONSE_TYPE_XML)) {
				// send XML
				response.setContentType("application/xml");
				JAXB.marshal(object, response.getOutputStream());
			} else if (request.getRequestURI().endsWith(RESPONSE_TYPE_JSON)) {
				// send JSON
				response.setContentType("application/json");
				ObjectMapper mapper = new ObjectMapper();
				MappingJsonFactory jsonFactory = new MappingJsonFactory();
				JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(response.getOutputStream());
				mapper.writeValue(jsonGenerator, object);
			} else {
				throw new RuntimeException("Please specify either XML or JSON for output format.  Do this in the url - e.g. /viewRequestObject.<span style=\"color: red\">json</span>?class=com.foo.Bar or /viewRequestObject.<span style=\"color: red\">xml</span>?class=com.foo.Bar");
			}
		} catch (Throwable t) {
			if(getServletConfig() != null && getServletContext() != null) {
				getServletContext().log("Exception occurred while generating wadl documentation", t);
			}
			
			response.setContentType("text/html");
			response.getOutputStream().write(t.getMessage().getBytes());
			response.getOutputStream().write("<br/><br/>".getBytes());
			response.getOutputStream().write("Requested class should be in the format com.example.MyClass.myMethod[<span style=\"color: blue\">n</span>] where <span style=\"color: blue\">n</span> is the argument index".getBytes());
		}
	}

	private void checkDocumentationParameterIsPresent(Method method, Integer argumentIndex, String lookup, HttpServletResponse response) throws IOException {
		Annotation[][] annotations = method.getParameterAnnotations();

		for (Annotation annotation : annotations[argumentIndex]) {
			if (annotation instanceof Description) {
				Description description = (Description) annotation;

				if (description.value().equals(lookup)) {
					return;
				}
			}
		}

		response.getOutputStream().write(("No org.apache.cxf.jaxrs.model.wadl.Description annotation on " + lookup).getBytes());
		throw new RuntimeException("No org.apache.cxf.jaxrs.model.wadl.Description annotation on " + lookup);
	}

	private Method findMethod(Class<?> clazz, String lookup, Integer argumentIndex) {
		String methodName = objectNameParser.findMethodName(lookup);

		if (methodName == null) {
			throw new RuntimeException("Could not find method name in " + lookup);
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.getName().equals(methodName)) {
				continue;
			}

			if (method.getParameterTypes().length < (argumentIndex + 1)) {
				continue;
			}

			return method;
		}

		throw new RuntimeException("No method named " + methodName + " present on class " + clazz.getCanonicalName());
	}

	private Class<?> findClass(String lookup) throws ClassNotFoundException {
		String className = objectNameParser.findClassName(lookup);

		if (className == null) {
			throw new RuntimeException("Could not find classname in " + lookup);
		}

		return Class.forName(className);
	}

	private Integer findArgumentIndex(String lookup) {
		Integer argumentIndex = objectNameParser.findArgumentIndex(lookup);

		if (argumentIndex == null) {
			throw new RuntimeException("Could not find argument index in " + lookup);
		}

		return argumentIndex;
	}
}
