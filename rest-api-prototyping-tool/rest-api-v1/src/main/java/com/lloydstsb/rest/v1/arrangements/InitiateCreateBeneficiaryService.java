/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Date: 26 Oct 2012
 ***********************************************************************/
package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.Digits;
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

import com.lloydstsb.rest.v1.exceptions.BadRequestException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.PaymentsDisabledException;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;

/**
 * <p>
 * REST API service to initiate the creation of a beneficiary for the current authenticated user.
 * </p>
 * The details are validated e.g. the reference and the authentication type is returned.
 *
 * @author Jesper Madsen (CT026780)
 */
@Path("/arrangements")
public interface InitiateCreateBeneficiaryService {

	@POST
	@Path("{arrangementId}/beneficiaries")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Starts the process of creating a beneficiary for the current authenticated user"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public NewBeneficiary initiateCreateBeneficiary(
			@Description(value = "An arrangment id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String arrangementId,

			@Description(value = "The beneficiary sort code", target = DocTarget.PARAM)
			@FormParam("sortCode")
			@NotNull
			@Size(min=6, max=6, message="The sort code must contain 6 digits only.")
			@Digits(integer=6, fraction=0, message="The sort code must contain digits only.")
			String sortCode,
			
			@Description(value = "The beneficiary account number", target = DocTarget.PARAM)
			@FormParam("accountNumber")
			@NotNull
			@Size(min=8, max=8, message="The account number must contain 8 digits only.")
			@Digits(integer=8, fraction=0, message="The account number must contain digits only.")
			String accountNumber,

			@Description(value = "The beneficiary name", target = DocTarget.PARAM)
			@FormParam("name")
			@NotNull
			@Pattern(regexp="^[a-zA-Z0-9 &@+,.'/_()-]+$", message="Invalid character(s) in beneficiary name.")
			@Size(min=1, max=81, message="The beneficiary name is too long.")
			String name,

			@Description(value = "The beneficiary reference (required only for whitelist beneficiaries)", target = DocTarget.PARAM)
			@FormParam("reference")
			@Pattern(regexp="^[\\w\\s@&+\\-,./']*$", message="Invalid character(s) in reference.")
			@Size(min=0, max=18, message="The reference cannot be longer than 18 characters.")
			String reference) throws NotFoundException, PaymentsDisabledException, BadRequestException;
}