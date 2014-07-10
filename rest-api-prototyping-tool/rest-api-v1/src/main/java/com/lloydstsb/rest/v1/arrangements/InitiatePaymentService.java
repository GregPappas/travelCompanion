package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.PaymentDeclinedException;
import com.lloydstsb.rest.v1.exceptions.PaymentsDisabledException;
import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.valueobjects.Payment;

@Path("/arrangements")
public interface InitiatePaymentService {
	@POST
	@Path("{arrangementId}/beneficiaries/{beneficiaryId}/payments")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Initiates a payment for the current authenticated user"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Payment initiatePayment(
			@Description(value = "An arrangment id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String arrangementId,
			
			@Description(value = "An beneficiary id", target = DocTarget.PARAM)
			@PathParam("beneficiaryId")
			@NotNull
			String beneficiaryId,
			
			@Description(value = "The payment date (optional, format yyyy-MM-dd'T'HH:mm:ss.SSSZ). For asap payment the date should not be provided.", target = DocTarget.PARAM)
			@FormParam("date")
			String date,

			@Description(value = "The amount (format ###.##)", target = DocTarget.PARAM)
			@FormParam("amount")
			@NotNull
			@DecimalMin("0.01")
			@DecimalMax("999999999999999.99")
			String amount,

			@Description(value = "The currency code of the amount (format GBP)", target = DocTarget.PARAM)
			@FormParam("currencyCode")
			@NotNull
			String currencyCode,

			@Description(value = "The payment reference (optional)", target = DocTarget.PARAM)
			@FormParam("reference")
			@Pattern(regexp="^[\\w\\s@&+\\-,./']*$", message="Invalid character(s) in reference.")
			@Size(min=0, max=18, message="The reference cannot be longer than 18 characters.")
			String reference) throws NotFoundException, PaymentsDisabledException, PaymentDeclinedException, UnauthorisedException;
}