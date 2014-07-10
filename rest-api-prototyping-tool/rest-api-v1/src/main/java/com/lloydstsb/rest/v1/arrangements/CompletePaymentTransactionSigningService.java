package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.NotNull;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.constants.HeaderConstants;
import com.lloydstsb.rest.v1.exceptions.AccountDisabledException;
import com.lloydstsb.rest.v1.exceptions.ForcedLogoutException;
import com.lloydstsb.rest.v1.exceptions.InvalidCredentialsException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.PaymentDeclinedException;
import com.lloydstsb.rest.v1.exceptions.PaymentHeldForReviewException;
import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.valueobjects.PaymentConfirmation;

@Path("/arrangements")
public interface CompletePaymentTransactionSigningService {
	
	@PUT
	@Path("{arrangementId}/beneficiaries/{beneficiaryId}/payments/{paymentId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Fulfils a payment for the current user using transaction signing authentication"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public PaymentConfirmation completeSignedPayment(
			@Description(value = "An arrangment id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String arrangementId,

			@Description(value = "An beneficiary id", target = DocTarget.PARAM)
			@PathParam("beneficiaryId")
			@NotNull
			String beneficiaryId,

			@Description(value = "The payment id", target = DocTarget.PARAM)
			@PathParam("paymentId")
			@NotNull
			String paymentId,

			@Description(value = "The " + HeaderConstants.AUTH_MESSAGE + " header (required)", target = DocTarget.PARAM)
			@HeaderParam(HeaderConstants.AUTH_MESSAGE)
			@NotNull
			String authMessage) throws PaymentHeldForReviewException, NotFoundException, PaymentDeclinedException,
				UnauthorisedException, AccountDisabledException, InvalidCredentialsException, ForcedLogoutException;
}
