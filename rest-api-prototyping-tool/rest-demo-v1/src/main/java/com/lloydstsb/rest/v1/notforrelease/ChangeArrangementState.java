package com.lloydstsb.rest.v1.notforrelease;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementStateType;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;

@Path("/changeState")
public class ChangeArrangementState {
	
	@Path("/{arrangementId}/{state}")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response changeState(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId, @PathParam("state") String state)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		wrapper.setState(ArrangementStateType.valueOf(state));
		return Response.status(200).build();
	}
	
	@Path("/{arrangementId}/togglePaymentsAvailable")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response togglePaymentsAvailable(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		boolean toggledValue = !wrapper.isMakePaymentAvailable();
		wrapper.setMakePaymentAvailable(toggledValue);
		return Response.status(200).build();
	}
	

	@Path("/{arrangementId}/toggleTransfersAvailable")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response toggleTransfersAvailable(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		boolean toggledValue = !wrapper.isMakeTransferAvailable();
		wrapper.setMakeTransferAvailable(toggledValue);
		return Response.status(200).build();
	}
	
	@Path("/{arrangementId}/toggleBeneficiaryDisabled")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response toggle(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		boolean togglevalue=!wrapper.isCreateBeneficiaryAvailable();
		wrapper.setCreateBeneficiaryAvailable(togglevalue);
		return Response.status(200).build();
	}

	@Path("/{arrangementId}/toggleProhibitiveIndicator")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response toggleProhibitiveIndicator(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		boolean toggledValue = !wrapper.isProhibitiveIndicator();
		wrapper.setProhibitiveIndicator(toggledValue);
		return Response.status(200).build();
	}
	
	@Path("/{arrangementId}/ChangeAvailableBalance")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response toggleAmountExceedsBalance(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId,
			@QueryParam("amount") String amount)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		Double oldAmount = wrapper.getAvailableBalance().getAmount().doubleValue();
		ObjectGenerator objectGenerator = new ObjectGenerator();
		CurrencyAmount newBalance = objectGenerator.createCurrencyAmount(amount, "GBP"); 
		wrapper.setAvailableBalance(newBalance);
		return Response.status(200).entity("old_amount{"+oldAmount+"}").build();
	}
	
}
