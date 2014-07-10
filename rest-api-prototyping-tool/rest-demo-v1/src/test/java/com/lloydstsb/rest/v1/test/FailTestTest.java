package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lloydstsb.rest.v1.demo.FailTest;

public class FailTestTest {

	@Test
	public void exceptionShouldThrowARuntimeException() 
	{
		boolean thrown = false;
		FailTest test = new FailTest();
		
		try{
		test.exception();
		thrown = false;
		}catch(RuntimeException e){
			
			thrown = true;
		}

		assertTrue(thrown);
	}
	
	@Test
	public void errorShouldThrowANoClassDefFoundError() 
	{
		boolean thrown = false;
		FailTest test = new FailTest();
		
		try{
		test.error();
		thrown = false;
		}catch(NoClassDefFoundError e){
			
			thrown = true;
		}

		assertTrue(thrown);
	}

}
