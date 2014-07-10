package com.lloydstsb.rest.v1.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.BadRequestException;
import com.lloydstsb.rest.v1.exceptions.UserRoleUpgradeFailedException;

@Path("/customer")
public interface CreateMemorableInformation {
	

	@POST
	@Path("memorablecharacters")
	@Produces({ "application/xml", "application/json" })
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Create memorable information string for identification purposes"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public void createMemorableInformation(
			@Description(value = "The user's new memorable information.", target = DocTarget.PARAM) 
			@NotNull(message = "The memorable information cannot be null")
			@Size(min = 6, max = 15)
			@FormParam("memorableInformation")
			String memorableInformation) throws UserRoleUpgradeFailedException, BadRequestException;
}
