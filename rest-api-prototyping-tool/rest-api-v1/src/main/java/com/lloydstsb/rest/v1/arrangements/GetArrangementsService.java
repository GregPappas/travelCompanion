package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.valueobjects.Arrangements;

@Path("/arrangements")
public interface GetArrangementsService {
	
	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Get list of arrangements for the current authenticated user"),
			@Description(target = DocTarget.REQUEST, value = "Standard http request"),
			@Description(target = DocTarget.RESPONSE, value = "Returns a page of the list of all the users arrangements.")
	})
	public Arrangements getArrangements(
			@Description(value = "The page of arrangements to return", target = DocTarget.PARAM)
			@QueryParam("page")
			@DefaultValue("0")
			@Min(0)
			int page,

			@Description(value = "How many arrangements in a page", target = DocTarget.PARAM)
			@QueryParam("size")
			@DefaultValue("50")
			@Max(50)
			@Min(1)
			int size);
}
