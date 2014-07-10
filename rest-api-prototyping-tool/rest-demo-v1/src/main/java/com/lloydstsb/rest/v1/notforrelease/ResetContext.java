package com.lloydstsb.rest.v1.notforrelease;

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

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.PersistenceContainer;
import com.lloydstsb.rest.v1.data.PhoneNumbersDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;

import java.io.IOException;

@Path("/reset")
public class ResetContext {

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response reset(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
		PersistenceContainer.getInstance().clear();
		   PhoneNumbersDataIpsum phoneNumbers = new PhoneNumbersDataIpsum();
		   CustomersDataIpsum customers = new CustomersDataIpsum();
		   phoneNumbers.instantiatePhoneNumbers();
		   customers.instantiateCustomers();
		   ArrangementServiceDataIpsum arrangements = new ArrangementServiceDataIpsum();
		   arrangements.instantiateExchangeLockCustomersArrangements();
		   TransactionsDataIpsum transactions = new TransactionsDataIpsum();
		   transactions.readAndParseCSVFile();
		   BeneficiaryDataIpsum beneficiary = new BeneficiaryDataIpsum();
		   beneficiary.generateHashMapOfBeneficiaries();

		return Response.status(200).entity("Data Successfully rebooted").build();
	}

	
}
