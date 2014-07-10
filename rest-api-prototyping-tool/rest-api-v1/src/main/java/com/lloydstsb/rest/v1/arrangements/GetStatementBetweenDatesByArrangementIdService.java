/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.arrangements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.joda.time.LocalDate;

import com.lloydstsb.rest.common.types.LocalDateAdapter;
import com.lloydstsb.rest.v1.exceptions.DormantArrangementException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.StatementExpiredException;
import com.lloydstsb.rest.v1.valueobjects.Statement;
import com.lloydstsb.rest.v1.valueobjects.Transaction;

@Path("/arrangements")
public interface GetStatementBetweenDatesByArrangementIdService {

	@GET
	@Path("{arrangementId}/statements")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
		@Description(target = DocTarget.METHOD, value = "Retrieve statement by year, month and arrangement id"),
		@Description(target = DocTarget.METHOD, value = "<strong>Note. Should be sent as POST to the template address with _method=GET and arrangementId in body</strong>"),
		@Description(target = DocTarget.REQUEST, value = "Standard http request"),
		@Description(target = DocTarget.RESPONSE, value = "Returns a page of transactions for an arrangement in a given month")
	})
	public Statement<? extends Transaction> getStatementBetweenDatesByArrangementId(
			@Description(value = "An arrangement id", target = DocTarget.PARAM)
			@PathParam("arrangementId")
			@NotNull
			String arrangementId,

			@Description(value = "A start date (in the format 'YYYY-MM-DD')", target = DocTarget.PARAM)
			@QueryParam("from")
			@NotNull
			@XmlJavaTypeAdapter(LocalDateAdapter.class)
			LocalDate from,

			@Description(value = "An end date (in the format 'YYYY-MM-DD', must be within three months of the start date)", target = DocTarget.PARAM)
			@QueryParam("to")
			@NotNull
			@XmlJavaTypeAdapter(LocalDateAdapter.class)
			LocalDate to,

			@Description(value = "A search parameter", target = DocTarget.PARAM)
			@QueryParam("query")
			String query,

			@Description(value = "The page of transactions to return", target = DocTarget.PARAM)
			@QueryParam("page")
			@DefaultValue("0")
			@Min(0)
			int page,

			@Description(value = "How many transactions in a page", target = DocTarget.PARAM)
			@QueryParam("size")
			@DefaultValue("50")
			@Max(50)
			@Min(1)
			int size) throws NotFoundException, StatementExpiredException, DormantArrangementException;
}
