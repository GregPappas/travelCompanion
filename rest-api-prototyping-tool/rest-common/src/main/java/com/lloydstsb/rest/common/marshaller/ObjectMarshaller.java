package com.lloydstsb.rest.common.marshaller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

public class ObjectMarshaller {
	
    /**
     * Private constant for ACCEPT_HEADER_KEY.
     */
	private static final String ACCEPT_HEADER_KEY = "Accept";
	
    /**
     * Private constant for RESPONSE_TYPE_XML.
     */
	private static final String XML_EXTENSION = ".xml";

	public void marshallObjectToResponse(HttpServletRequest request,
			HttpServletResponse response, Object object) throws IOException,
			JsonGenerationException, JsonMappingException {
		if (request.getRequestURI().endsWith(XML_EXTENSION)
				|| MediaType.APPLICATION_XML.equals(request.getHeader(ACCEPT_HEADER_KEY))) {
			response.setContentType(MediaType.APPLICATION_XML);
			writeXml(response.getOutputStream(), object);
		} else {
			response.setContentType(MediaType.APPLICATION_JSON);
			writeJson(response.getOutputStream(), object);
		}
	}

	private void writeXml(OutputStream outputStream, Object object)
			throws IOException {
		JAXB.marshal(object, outputStream);
		outputStream.flush();
		outputStream.close();
	}

	@SuppressWarnings("deprecation")
	private void writeJson(OutputStream outputStream, Object object)
			throws IOException, JsonGenerationException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		// make deserializer use JAXB annotations (only)
		mapper.getDeserializationConfig().setAnnotationIntrospector(introspector);
		// make serializer use JAXB annotations (only)
		mapper.getSerializationConfig().setAnnotationIntrospector(introspector);
		MappingJsonFactory jsonFactory = new MappingJsonFactory();
		JsonGenerator jsonGenerator = jsonFactory
				.createJsonGenerator(outputStream);
		mapper.writeValue(jsonGenerator, object);
		outputStream.flush();
		outputStream.close();
	}
}
