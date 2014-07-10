package com.lloydstsb.rest.v1.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.ErrorHelper;
import com.lloydstsb.rest.v1.helpers.CheckHelper;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Customer;
import com.lloydstsb.rest.v1.valueobjects.Error;

@Path("/customer")
public class CustomerDetailsServiceImpl {

	private SessionHelper sessionHelper = new SessionHelper();
	private CheckHelper checkHelper = new CheckHelper();
	private ErrorHelper errorHelper = new ErrorHelper();
	
//	public static final String USER_ID = "userId";

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getCustomer(@Context HttpServletRequest request,@Context HttpServletResponse response)
	{
		String userId = sessionHelper.getUserId(request);
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getForcedLogoutError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();	
		}

		getDetails(userId);
		return Response.status(200).entity(getDetails(userId)).build();
	}

	public Customer getDetails(String userId)
	{
		CustomersDataIpsum customers = new CustomersDataIpsum();
		return customers.getCustomers().get(userId);

	}

	public void setSessionHelper(SessionHelper sessionHelper) {
		this.sessionHelper = sessionHelper;
	}

}
