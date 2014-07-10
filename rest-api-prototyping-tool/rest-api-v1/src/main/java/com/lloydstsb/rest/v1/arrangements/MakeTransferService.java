/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.exceptions.AmountExceedsBalanceException;
import com.lloydstsb.rest.v1.exceptions.ExceedsReceivingLimitException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.ProhibitiveIndicatorException;
import com.lloydstsb.rest.v1.exceptions.SameAccountsException;
import com.lloydstsb.rest.v1.exceptions.TransferDeclinedOtherReasonException;
import com.lloydstsb.rest.v1.exceptions.TransfersDisabledException;
import com.lloydstsb.rest.v1.exceptions.UnauthorisedException;

@Path("/arrangements")
public interface MakeTransferService {
	@POST
	@Path("{arrangementId}/transfers")
	@Descriptions({
			@Description(target = DocTarget.METHOD, value = "Transfers an amount from one arrangement to another."),
			@Description(target = DocTarget.REQUEST, value = "Standard http request") })
	public void makeTransfer(
			@Description(value = "The source arrangement id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String sourceArrangementId,

			@Description(value = "The target arrangement id", target = DocTarget.PARAM)
			@FormParam("targetArrangementId")
			@NotNull
			String targetArrangementId,

			@Description(value = "The amount (format ###.##)", target = DocTarget.PARAM)
			@FormParam("amount")
			@NotNull
			@DecimalMin("0.01")
			@DecimalMax("999999999999999.99")
			String amount,

			@Description(value = "The currency code of the amount (format GBP)", target = DocTarget.PARAM)
			@FormParam("currencyCode")
			@NotNull
			String currencyCode)
			throws NotFoundException, TransfersDisabledException, TransferDeclinedOtherReasonException, AmountExceedsBalanceException, SameAccountsException, ExceedsReceivingLimitException, ProhibitiveIndicatorException, UnauthorisedException;
}
