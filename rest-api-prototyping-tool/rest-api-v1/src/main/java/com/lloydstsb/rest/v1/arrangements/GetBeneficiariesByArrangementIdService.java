package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.valueobjects.Beneficiaries;

@Path("/arrangements")
public interface GetBeneficiariesByArrangementIdService {
	
	@GET
	@Path("{arrangementId}/beneficiaries")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Get list of beneficiaries for the current authenticated user"),
//			@Description(target = DocTarget.METHOD, value = "<strong>Note. Should be sent as POST to the template address with _method=GET and arrangementId in body</strong>"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Beneficiaries getBeneficiaries(
		@Description(value = "An arrangment id", target = DocTarget.PARAM)
		@PathParam("arrangementId")
		@NotNull
		String arrangementId,

		@Description(value = "The page of beneficiaries to return", target = DocTarget.PARAM)
		@QueryParam("page")
		@DefaultValue("0")
		@Min(0)
		int pageIndex,

		@Description(value = "How many beneficiaries in a page", target = DocTarget.PARAM)
		@QueryParam("size")
		@DefaultValue("50")
		@Max(50)
		@Min(0)
		int pageSize) throws NotFoundException, UnauthorisedException;
}
