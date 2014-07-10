package com.lloydstsb.rest.v1.customer;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.constants.HeaderConstants;
import com.lloydstsb.rest.v1.exceptions.BadRequestException;
import com.lloydstsb.rest.v1.exceptions.CustomerEnrolmentFailedException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;

@Path("/customer")
public interface EnrolCustomerService {
	
	@PUT
	@Path("enrolment")
	@Produces({ "application/xml", "application/json" })
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "This service will be invoked to retrieve the trust token that needs to be implanted in the user’s device."),
			@Description(target = DocTarget.REQUEST, value = "Standard http request"),
			@Description(target = DocTarget.RESPONSE, value = "AUTH_MESSAGE header containing userID, DeviceID and Kuser encrypted with Ktemp")
	})
	public void enrolCustomer(
			@Description(value = "This header will hold the encrypted Ktemp", target = DocTarget.PARAM) 
			@NotNull(message = HeaderConstants.AUTH_MESSAGE + " cannot be null")
			@HeaderParam(HeaderConstants.AUTH_MESSAGE)
			String authMessage,

			@Description(value = "This header will contain information regarding device id profiling id, malware blob, logging information and tampering information", target = DocTarget.PARAM) 
			@NotNull(message = HeaderConstants.WAS_MESSAGE + " cannot be null")
			@HeaderParam(HeaderConstants.WAS_MESSAGE)
			String wasMessage,

			@Description(value = "The transaction id", target = DocTarget.PARAM)
			@FormParam("transactionId")
			@NotNull(message = "transactionId cannot be null")
			String transactionId,

			@Description(value = "This will contain device model extracted by client using platform specific api.", target = DocTarget.PARAM) 
			@NotNull(message = "deviceType cannot be null")
			@FormParam("deviceType")
			String deviceType) throws CustomerEnrolmentFailedException, NotFoundException, BadRequestException;
}
