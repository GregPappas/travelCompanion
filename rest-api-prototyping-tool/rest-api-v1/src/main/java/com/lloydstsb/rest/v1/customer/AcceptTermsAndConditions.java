package com.lloydstsb.rest.v1.customer;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@Path("/customer")
public interface AcceptTermsAndConditions {
	
	@PUT
	@Path("termsandconditions")
	@Produces({ "application/xml", "application/json" })
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Accept the terms and conditions"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public void acceptTermsAndConditions();

}
