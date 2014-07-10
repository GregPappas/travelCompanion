package com.lloydstsb.rest.v1.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.data.ErrorHelper;
import com.lloydstsb.rest.v1.helpers.MiscHelper;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Error;


@Path("/session")
public class LoginServiceImpl{

	public static final String USER_ID = "userId";
	private SessionHelper sessionHelper = new SessionHelper();
	private MiscHelper miscHelper = new MiscHelper();
	private ErrorHelper errorHelper = new ErrorHelper();

	@POST
	@Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public Response login(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest request,@Context HttpServletResponse response)
	{
		String SessionVariableKeyOfUserId  = USER_ID;
		sessionHelper.setRequest(request);
		if(miscHelper.authenticate(username, password))
		{
			getSessionHelper().addVariableToSession(username,SessionVariableKeyOfUserId);
			return Response.status(201).build();
		}	
		
		Error e = errorHelper.getInvalidCredentialError();
		    return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
	}
	
	
    @DELETE
    public Response logout(@Context HttpServletRequest request,@Context HttpServletResponse response)
    {
    	//if session does not exist try and catch
    	sessionHelper.setRequest(request);
    	sessionHelper.getSession().invalidate();
        return Response.status(204).build();
   }



	public SessionHelper getSessionHelper() {
		return sessionHelper;
	}

	public void setSessionHelper(SessionHelper s) {
		this.sessionHelper = s;
	}

	
}
