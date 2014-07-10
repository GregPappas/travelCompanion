package com.lloydstsb.rest.common.marshaller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ObjectMarshallerTest {
	
	private static final String ACCEPT_HEADER = "Accept";
	
	@InjectMocks
	private ObjectMarshaller objectMarshaller = new ObjectMarshaller();

	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private ServletOutputStream servletOutputStream;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testJsonResponseWhenHandleMarshallObjectToResponseForRequestWithJsonExtension() throws IOException{
		String errorCode = UUID.randomUUID().toString();
		String errorMessage = UUID.randomUUID().toString();
		Error error = new Error(errorCode, errorMessage);
		
		when(request.getRequestURI()).thenReturn("/retail/internetbaninking/api/v1/service/memorablecharacters.json");
		MyServletOutputStream servletOutputStream = new MyServletOutputStream(new ByteArrayOutputStream());
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		
		objectMarshaller.marshallObjectToResponse(request, response, error);
		Error errorResponse = mapper.readValue(servletOutputStream.getOutputStream().toString(), Error.class);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}
	
	@Test
	public void testJsonResponseWhenMarshallObjectToResponseForRequestWithJsonAcceptHeader() throws IOException{
		String errorCode = UUID.randomUUID().toString();
		String errorMessage = UUID.randomUUID().toString();
		Error error = new Error(errorCode, errorMessage);
		
		when(request.getHeader(ACCEPT_HEADER)).thenReturn(MediaType.APPLICATION_JSON);
		when(request.getRequestURI()).thenReturn("/retail/internetbaninking/api/v1/service/memorablecharacters");
		MyServletOutputStream servletOutputStream = new MyServletOutputStream(new ByteArrayOutputStream());
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		
		objectMarshaller.marshallObjectToResponse(request, response, error);
		Error errorResponse = mapper.readValue(servletOutputStream.getOutputStream().toString(), Error.class);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}
	
	@Test
	public void testXmlResponseWhenMarshallObjectToResponseForRequestWithXmlAcceptHeader() throws IOException{
		String errorCode = UUID.randomUUID().toString();
		String errorMessage = UUID.randomUUID().toString();
		Error error = new Error(errorCode, errorMessage);
		
		when(request.getHeader(ACCEPT_HEADER)).thenReturn(MediaType.APPLICATION_XML);
		when(request.getRequestURI()).thenReturn("/retail/internetbaninking/api/v1/service/memorablecharacters");
		MyServletOutputStream servletOutputStream = new MyServletOutputStream(new ByteArrayOutputStream());
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		
		objectMarshaller.marshallObjectToResponse(request, response, error);
		Error errorResponse = JAXB.unmarshal(new ByteArrayInputStream(((ByteArrayOutputStream) servletOutputStream.getOutputStream()).toByteArray()), Error.class);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}
	
	@Test
	public void testXmlResponseWhenMarshallObjectToResponseForRequestWithXmlExtension() throws IOException{
		String errorCode = UUID.randomUUID().toString();
		String errorMessage = UUID.randomUUID().toString();
		Error error = new Error(errorCode, errorMessage);
		
		when(request.getRequestURI()).thenReturn("/retail/internetbaninking/api/v1/service/memorablecharacters.xml");
		MyServletOutputStream servletOutputStream = new MyServletOutputStream(new ByteArrayOutputStream());
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		
		objectMarshaller.marshallObjectToResponse(request, response, error);
		Error errorResponse = JAXB.unmarshal(new ByteArrayInputStream(((ByteArrayOutputStream) servletOutputStream.getOutputStream()).toByteArray()), Error.class);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}
	
	@Test
	public void testJsonResponseWhenMarshallObjectToResponseByDefault() throws IOException{
		String errorCode = UUID.randomUUID().toString();
		String errorMessage = UUID.randomUUID().toString();
		Error error = new Error(errorCode, errorMessage);
		
		when(request.getRequestURI()).thenReturn("/retail/internetbaninking/api/v1/service/memorablecharacters");
		MyServletOutputStream servletOutputStream = new MyServletOutputStream(new ByteArrayOutputStream());
		when(response.getOutputStream()).thenReturn(servletOutputStream);
		
		objectMarshaller.marshallObjectToResponse(request, response, error);
		Error errorResponse = mapper.readValue(servletOutputStream.getOutputStream().toString(), Error.class);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}
	
	private class MyServletOutputStream extends ServletOutputStream {
		
		private OutputStream outputStream;
		
		public MyServletOutputStream(OutputStream outputStream){
			this.outputStream = outputStream;
		}
		
		public void write(int b) throws IOException {
			outputStream.write(b);
		}
		
		public void write(byte[] b,int off,int len) throws IOException {
			outputStream.write(b, off, len);
		}

		public OutputStream getOutputStream() {
			return outputStream;
		}
	}
	
	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Error {
		private String code;
		private String message;

		public Error() {
			super();
		}

		public Error(String code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

}
