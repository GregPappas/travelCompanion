package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;

@Path("/arrangements")
public interface GetArrangementByIdService {
	
	@GET
	@Path("{arrangementId}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
		@Description(target = DocTarget.METHOD, value = "Get the arrangement details by arrangement id"),
		@Description(target = DocTarget.METHOD, value = "<strong>Note. Should be sent as POST to the template address with _method=GET and arrangementId in body</strong>"),
		@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Arrangement getArrangementById(
			@Description(value = "An arrangement id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String arrangementId) throws NotFoundException, UnauthorisedException;

}
