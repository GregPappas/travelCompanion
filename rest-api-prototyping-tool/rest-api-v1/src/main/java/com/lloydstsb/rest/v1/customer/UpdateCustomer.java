package com.lloydstsb.rest.v1.customer;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.common.annotation.PATCH;
import com.lloydstsb.rest.v1.exceptions.AtLeastOneContactNumberRequiredException;
import com.lloydstsb.rest.v1.exceptions.CredentialsExpiredException;
import com.lloydstsb.rest.v1.exceptions.CustomerUpdateFailedException;
import com.lloydstsb.rest.v1.exceptions.EmailRequiredForPaperlessStatementException;
import com.lloydstsb.rest.v1.exceptions.InputConstraintViolationException;
import com.lloydstsb.rest.v1.exceptions.InvalidCredentialsException;
import com.lloydstsb.rest.v1.exceptions.InvalidUserAccountException;
import com.lloydstsb.rest.v1.exceptions.PasswordExpiredException;

@Path("/customer")
public interface UpdateCustomer {

	@PATCH
	@Path("")
	@Produces({ "application/xml", "application/json" })
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Update customer details"),
			@Description(target = DocTarget.REQUEST, value = "The following fields can be included in the request"),
			@Description(target = DocTarget.REQUEST, value = "password : User password value (Mandatory for email and telephone number updates)"),
			@Description(target = DocTarget.REQUEST, value = "email : New customer email address value"),
			@Description(target = DocTarget.REQUEST, value = "MOBILE : New customer mobile telephone number"),
			@Description(target = DocTarget.REQUEST, value = "HOME : New customer home telephone number"),
			@Description(target = DocTarget.REQUEST, value = "WORK : New customer work telephone number"),
			@Description(target = DocTarget.REQUEST, value = "WORK.extension : New customer work telephone number extension"),
			@Description(target = DocTarget.REQUEST, value = "marketingPreferenceEnabled : For enabling / disabling marketing preference (true | false)"),
			@Description(target = DocTarget.REQUEST, value = "emailPreferenceEnabled : For enabling / disabling email marketing preference  (true | false)"),
			@Description(target = DocTarget.REQUEST, value = "smsPreferenceEnabled : For enabling / disabling sms marketing preference  (true | false)") })
	public void updateCustomer(
			@Description(value = "A map of all key/value pairs submitted", target = DocTarget.PARAM)
			@NotNull(message = "The form data cannot be null")
			MultivaluedMap<String, String> formData) throws InvalidCredentialsException, InvalidUserAccountException, CredentialsExpiredException, PasswordExpiredException, InputConstraintViolationException, CustomerUpdateFailedException, AtLeastOneContactNumberRequiredException, EmailRequiredForPaperlessStatementException;
}
