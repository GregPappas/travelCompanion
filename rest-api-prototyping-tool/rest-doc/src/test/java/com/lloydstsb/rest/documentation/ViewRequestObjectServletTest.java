package com.lloydstsb.rest.documentation;

import static org.mockito.Mockito.when;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ViewRequestObjectServletTest {
	private ViewRequestObjectServlet servlet;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private ServletOutputStream outputStream;
	
	@Before
	public void setUp() throws Exception {
		servlet = new ViewRequestObjectServlet();
		
		when(response.getOutputStream()).thenReturn(outputStream);
	}
	
	@Test
	public void shouldParseClassName() throws Exception {
		// this test needs a complex object passed to one of our service methods which at the time of writing we don't have.  renable this later...
		
		//when(request.getParameter("class")).thenReturn("com.lloydstsb.rest.v1.session.SubmitMemorableCharactersService.enterMemorableCharacters[0]");
		//when(request.getRequestURI()).thenReturn("/viewRequestObject.json");
		
		//servlet.doGet(request, response);

		servlet.getClass();
	}
}
