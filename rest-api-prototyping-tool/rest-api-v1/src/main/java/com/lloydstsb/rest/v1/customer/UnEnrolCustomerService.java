package com.lloydstsb.rest.v1.customer;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.lloydstsb.rest.v1.exceptions.AliasNameNotFoundOnSessionException;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.constants.HeaderConstants;
import com.lloydstsb.rest.v1.exceptions.CustomerUnenrolmentFailedException;

@Path("/customer")
public interface UnEnrolCustomerService {
	
	@DELETE
	@Path("enrolment")
	@Produces({ "application/xml", "application/json" })
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "This service will be invoked by the app when the customer wishes to de-enrol his device."),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public void unEnrolCustomer(
			@Description(value = "This header will hold the encrypted Ktemp", target = DocTarget.PARAM)
			@NotNull(message = HeaderConstants.AUTH_MESSAGE + " cannot be null")
			@HeaderParam(HeaderConstants.AUTH_MESSAGE)
			String authMessage) throws CustomerUnenrolmentFailedException, AliasNameNotFoundOnSessionException;

	
}
