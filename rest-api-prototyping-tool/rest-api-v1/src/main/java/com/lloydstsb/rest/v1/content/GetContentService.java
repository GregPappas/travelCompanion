package com.lloydstsb.rest.v1.content;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.NotFoundException;


@Path("/content")
public interface GetContentService {
	@GET
	@Path("{contentPath}/{contentKey}")	
	@Produces({"text/html"})
	@Descriptions({
		@Description(target = DocTarget.METHOD, value = "Get content for the given content key"),
		@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public String getContent(			
			@Description("The Content Path value e.g. p19_03")
			@PathParam("contentPath")
			@NotNull
			String contentPath ,

			@Description("The Content Key value e.g. legal001")
			@PathParam("contentKey")
			@NotNull
			String contentKey) throws NotFoundException;
}
