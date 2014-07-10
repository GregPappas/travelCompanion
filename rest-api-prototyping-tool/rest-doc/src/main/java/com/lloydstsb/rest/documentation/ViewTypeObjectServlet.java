package com.lloydstsb.rest.documentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

public class ViewTypeObjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CLASSNAME_PARAMETER = "class";
	private static final String RESPONSE_TYPE_XML = ".xml";
	private static final String RESPONSE_TYPE_JSON = ".json";
	private ObjectPopulator objectPopulator = new ObjectPopulator();
	private List<String> viewObjectPackageNames;

	public void init(ServletConfig config) throws ServletException {
		viewObjectPackageNames = new ArrayList<String>();
		viewObjectPackageNames.add("com.lloydstsb.rest.v1.valueobjects");
		viewObjectPackageNames.add("com.lloydstsb.rest.v1.valueobjects.arrangement");
		
		this.init();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String lookup = request.getParameter(CLASSNAME_PARAMETER);
			
			if(lookup == null) {
				throw new IllegalArgumentException("Please provide a parameter " + CLASSNAME_PARAMETER);
			}
			
			Class<?> clazz = findClass(lookup);
 			
			checkXmlRootElementAnnotationIsPresent(clazz);
			
			Object object = objectPopulator.populate(clazz);

			marshallObjectToResponse(request, response, object);
		} catch (Throwable t) {
			response.setContentType("text/html");
			response.getOutputStream().write(t.getMessage().getBytes());
		}
	}

	private void marshallObjectToResponse(HttpServletRequest request,
			HttpServletResponse response, Object object) throws IOException,
			JsonGenerationException, JsonMappingException {
		if (request.getRequestURI().endsWith(RESPONSE_TYPE_XML)) {
			// send XML
			response.setContentType("application/xml");
			JAXB.marshal(object, response.getOutputStream());
		} else if (request.getRequestURI().endsWith(RESPONSE_TYPE_JSON)) {
			// send JSON
			response.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
		    AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		    // make deserializer use JAXB annotations (only)
		    mapper.getDeserializationConfig().withAnnotationIntrospector(introspector);
		    // make serializer use JAXB annotations (only)
		    mapper.getSerializationConfig().withAnnotationIntrospector(introspector);
			MappingJsonFactory jsonFactory = new MappingJsonFactory();
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(response.getOutputStream());
			mapper.writeValue(jsonGenerator, object);
		} else {
			throw new RuntimeException("Please specify either XML or JSON for output format.  Do this in the url - e.g. /viewRequestObject.<span style=\"color: red\">json</span>?class=com.foo.Bar or /viewRequestObject.<span style=\"color: red\">xml</span>?class=com.foo.Bar");
		}
	}
	
	private Class<?> findClass(String lookup) throws IOException {
		lookup = lookup.substring(0, 1).toUpperCase() + lookup.substring(1);
			
		for(String packageName : viewObjectPackageNames) {
			try {
				return Class.forName(packageName + "." + lookup);
			} catch(ClassNotFoundException e) {
				
			}
		}
		
		throw new IllegalArgumentException("Could not load class for " + lookup);
	}

	private void checkXmlRootElementAnnotationIsPresent(Class<?> clazz) throws IOException {
		if(clazz.isAnnotationPresent(XmlRootElement.class)) {
			return;
		}
		
		throw new RuntimeException("No javax.xml.bind.annotation.XmlRootElement annotation on " + clazz.getCanonicalName());
	}
}
