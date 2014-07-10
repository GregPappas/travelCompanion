/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.session;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.DataNotFoundException;
import com.lloydstsb.rest.v1.exceptions.InvalidCredentialsException;

@Path("/session")
public interface LoginService {

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "This service will perform the initial username/password validation."),
			@Description(target = DocTarget.REQUEST, value = "HEALTH_MESSAGE HEADER- This is the HEX code used as the access control of the app. The value of this header would be compared with the channel specific Galaxy reference database. Custom actions could be defined based on the HEX code. It would comprise of the following information Jail broken/rooted status, malware/device state, tampering/reverse engineering alarm."),
			@Description(target = DocTarget.REQUEST, value = "Standard http request"),
			@Description(target = DocTarget.RESPONSE, value = "Should contain the redirect cookie to identify the user to the auth stack."),
			@Description(target = DocTarget.RESPONSE, value = "May also include an IBSession01(Throttle cookie) if the server is overloaded. This cookie has a validity of 10 minutes and would mean any request made with this cookie in the next 10 minutes will not be allowed.") })
	public void login(
			@Description("A username (required)")
			@FormParam("username")
			@NotNull(message = "The username cannot be null")
			String username,

			@Description("A password (required)")
			@FormParam("password")
			@NotNull(message = "The password cannot be null") String password) throws InvalidCredentialsException;
}
