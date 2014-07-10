package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.AccountDisabledException;
import com.lloydstsb.rest.v1.exceptions.BadRequestException;
import com.lloydstsb.rest.v1.exceptions.CreateBeneficiaryDeclined;
import com.lloydstsb.rest.v1.exceptions.InvalidCredentialsException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

@Path("/arrangements")
public interface CompleteCreateBeneficiaryService {
	
	@PUT
	@Path("{arrangementId}/beneficiaries/{transactionId}")
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Completes the process of creating a new beneficiary for the current authenticated user"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Beneficiary completeCreateBeneficiary(
			@Description(value = "The arrangment id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String arrangementId,

			@Description(value = "The new beneficiary transaction id", target = DocTarget.PARAM)
			@PathParam("transactionId")
			@NotNull
			String transactionId,

			@Description(value = "The password (required only for password authentication type)", target = DocTarget.PARAM)
			@FormParam("password")
			@Size(min=6, max=20, message="Invalid number of password characters entered.")
			String password) throws NotFoundException, CreateBeneficiaryDeclined, BadRequestException, AccountDisabledException, InvalidCredentialsException, UnauthorisedException;
}