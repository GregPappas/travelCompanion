/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Date: 26 Oct 2012
 ***********************************************************************/
package com.lloydstsb.rest.v1.beneficiaries;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.BadSortcodeException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.valueobjects.Page;

/**
 * <p>
 * REST API service to search for a whitelisted beneficiary.
 * </p>
 *
 * @author Jesper Madsen (CT026780)
 */
@Path("/beneficiaries")
public interface SearchBeneficiaries {

	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Finds whitelisted beneficiaries for the given account / sortcode or name"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Page searchBeneficiaries(
			@Description(value = "The beneficiary sort code (if set then account number should also be set)", target = DocTarget.PARAM)
			@QueryParam("sortCode")
			@Size(min=6, max=6, message="The sort code must contain 6 digits only.")
			@Digits(integer=6, fraction=0, message="The sort code must contain digits only.")
			String sortCode,

			@Description(value = "The beneficiary account number (if set then sort code should also be set)", target = DocTarget.PARAM)
			@QueryParam("accountNumber")
			@Size(min=8, max=8, message="The account number must contain 8 digits only.")
			@Digits(integer=8, fraction=0, message="The account number must contain digits only.")
			String accountNumber,

			@Description(value = "The beneficiary name (if set the search will be by name and sort code / account no are ignored)", target = DocTarget.PARAM)
			@QueryParam("name")
			@Pattern(regexp="^[a-zA-Z0-9-& ]+$", message="Invalid character(s) in beneficiary name.")
			@Size(min=0, max=18, message="The beneficiary name cannot be longer than 18 characters.")
			String name,

			@Description(value = "The page of beneficiaries to return", target = DocTarget.PARAM)
			@QueryParam("page")
			@DefaultValue("0")
			@Min(0)
			int page,

			@Description(value = "How many beneficiaries in a page", target = DocTarget.PARAM)
			@QueryParam("size")
			@DefaultValue("50")
			@Max(50)
			@Min(1)
			int size) throws NotFoundException, BadSortcodeException, UnauthorisedException;
}