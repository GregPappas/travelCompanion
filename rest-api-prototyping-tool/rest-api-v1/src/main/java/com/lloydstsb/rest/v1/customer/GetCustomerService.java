package com.lloydstsb.rest.v1.customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.valueobjects.Customer;

@Path("/customer")
public interface GetCustomerService {
	
	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Get details of the current authenticated user"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Customer getCustomer() throws UnauthorisedException;

	
}
