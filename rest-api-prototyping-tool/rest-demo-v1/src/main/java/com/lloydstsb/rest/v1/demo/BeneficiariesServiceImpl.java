package com.lloydstsb.rest.v1.demo;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.BeneficiaryObject;
import com.lloydstsb.rest.v1.data.ErrorHelper;
import com.lloydstsb.rest.v1.helpers.BeneficiaryHelper;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.Page;

@Path("/beneficiaries")
public class BeneficiariesServiceImpl {

	public BeneficiaryDataIpsum beneficiaryData = new BeneficiaryDataIpsum();
	private SessionHelper sessionHelper = new SessionHelper();
	private BeneficiaryHelper beneficiaryHelper = new BeneficiaryHelper();
	private ErrorHelper errorHelper = new ErrorHelper();

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getBeneficiaries(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam("sortCode") String sortCode,
			@QueryParam("accountNumber") String accountNumber,
			@QueryParam("name") String name,
			@DefaultValue("0") @QueryParam("page") int pageIndex,
			@DefaultValue("50") @QueryParam("size") int size) 
	{

		ArrayList<Beneficiary> beneficiariesList= new ArrayList<Beneficiary>();
		String userId = sessionHelper.getUserId(request);

		if(name != null)
		{
			beneficiariesList=beneficiaryData.addBeneficiariesToListFromNameSearch(userId,name);
		}
		else if(!beneficiaryHelper.validateAccountNumberAndSortCodeNull(accountNumber, sortCode))
		{
			
			
			if(!beneficiaryHelper.checkAccountNumberValid(accountNumber))
			{
				Error e = errorHelper.getNotFound();
				return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
			}
			if(!beneficiaryHelper.checkSortCodeValid(sortCode))
			{
				Error e = errorHelper.getBadSortcode();
				return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
			}
			
				beneficiariesList=beneficiaryData.addBeneficiariesToListFromAccountAndSortCodeSearch(userId, sortCode, accountNumber);
		}
		
		else
		{
			beneficiariesList = beneficiaryData.getAllBeneficiariesBelongingToUser(userId);
		}
		ArrayList<BeneficiaryObject> newBeneficiariesList = beneficiaryData.convertFromBeneficiaryListToSearchBeneficiaryList(beneficiariesList);
		Page<BeneficiaryObject> page = new Page<BeneficiaryObject>(newBeneficiariesList, pageIndex, size);
		return Response.status(200).entity(page).build();
		
	}

	public void setSessionHelper(SessionHelper sessionHelper) {
		this.sessionHelper = sessionHelper;
	}
}
