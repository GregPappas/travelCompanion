package com.lloydstsb.rest.v1.demo;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/throw")
public class FailTest 
{
	@Path("/Exception")
	public Response exception()
	{
		throw new RuntimeException();	
	}
	
	@Path("/Error")
	public Response error()
	{
		throw new NoClassDefFoundError();
	}
}
