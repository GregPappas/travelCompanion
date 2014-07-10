/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.session;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@Path("/session")
public interface LogoutService {

	@DELETE
	@Path("")
	@Descriptions({
		@Description(target = DocTarget.METHOD, value = "Ends current user session"),
		@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public void logout();
}
