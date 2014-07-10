package com.lloydstsb.rest.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObjectNameParserTest {
	private ObjectNameParser objectNameParser;
	
	@Before
	public void setUp() throws Exception {
		objectNameParser = new ObjectNameParser();
	}
	
	@Test
	public void shouldParseClassName() throws Exception {
		String argumentName = "com.example.Foo.bar[0]";
		
		String result = objectNameParser.findClassName(argumentName);
		
		assertEquals("com.example.Foo", result);
	}
	
	@Test
	public void shouldParseMethodName() throws Exception {
		String argumentName = "com.example.Foo.bar[0]";
		
		String result = objectNameParser.findMethodName(argumentName);
		
		assertEquals("bar", result);
	}
	
	@Test
	public void shouldParseArgumentIndex() throws Exception {
		String argumentName = "com.example.Foo.bar[0]";
		
		int result = objectNameParser.findArgumentIndex(argumentName);
		
		assertEquals(0, result);
	}
}
