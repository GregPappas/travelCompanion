package com.lloydstsb.rest.documentation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Wadl2HtmlServlet extends HttpServlet {

	private static final String WADL_PATH = "wadl.path";
	private static final String WADL_XSL = "wadl.xsl";

	private static final long serialVersionUID = 1L;
	
	private String wadlPath;
	private String wadlXsl;
	
	public void init() throws ServletException {
	    wadlPath = getServletConfig().getInitParameter(WADL_PATH);
	    wadlXsl = getServletConfig().getInitParameter(WADL_XSL);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String scheme = request.getScheme();
		    String serverName = request.getServerName();
		    int serverPort = request.getServerPort();
		    String contextPath = request.getContextPath(); 
		    String wadleUrl = scheme + "://" + serverName + ":" + serverPort + contextPath + wadlPath;
		    
			InputStream inputStream = Wadl2HtmlServlet.class.getResourceAsStream(wadlXsl);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(inputStream));
			
			transformer.transform(new StreamSource(wadleUrl), new StreamResult(response.getOutputStream()));
			
			Calendar inOneMonth = Calendar.getInstance();
			inOneMonth.add(Calendar.MONTH, 1);
			response.setDateHeader("Expires", inOneMonth.getTimeInMillis());
			response.getOutputStream().close();
		} catch (Throwable t) {
			getServletContext().log("Exception occurred while generating wadl documentation", t);
		}
	}
}