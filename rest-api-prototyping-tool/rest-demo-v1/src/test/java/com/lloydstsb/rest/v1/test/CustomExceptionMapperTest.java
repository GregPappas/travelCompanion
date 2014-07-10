package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.lloydstsb.rest.v1.helpers.CustomExceptionMapper;

public class CustomExceptionMapperTest {

	@Test
	public void customExceptionMapperShouldReturnResponse500WhenTriggered() 
	{
		//setup
		CustomExceptionMapper customExceptionMapper = new CustomExceptionMapper();
		Exception ex = new Exception();
		//act
		Response returnedResponse = customExceptionMapper.toResponse(ex);
		//verify
		assertEquals(500, returnedResponse.getStatus());
	}

}
