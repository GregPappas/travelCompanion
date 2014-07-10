package com.lloydstsb.rest.v1.demo;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.ErrorHelper;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.helpers.ArrangementHelper;
import com.lloydstsb.rest.v1.helpers.CheckHelper;
import com.lloydstsb.rest.v1.helpers.MiscHelper;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Arrangements;
import com.lloydstsb.rest.v1.valueobjects.Beneficiaries;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount.CURRENCY_CODE;
import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;
import com.lloydstsb.rest.v1.valueobjects.Page;
import com.lloydstsb.rest.v1.valueobjects.Payment;
import com.lloydstsb.rest.v1.valueobjects.PaymentConfirmation;
import com.lloydstsb.rest.v1.valueobjects.Statement;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;

@Path("/arrangements")
public class ArrangementsServiceImpl 
{
	private ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
	private ArrangementHelper arrangementHelper = new ArrangementHelper();
	private BeneficiaryDataIpsum beneficiaryData = new BeneficiaryDataIpsum();
	public TransactionsDataIpsum transactionData = new TransactionsDataIpsum();
	
	private SessionHelper sessionHelper = new SessionHelper();
	private ObjectGenerator objectGenerator = new ObjectGenerator();
	private MiscHelper miscHelper = new MiscHelper();
	private CheckHelper checkHelper = new CheckHelper();
	private ErrorHelper errorHelper = new ErrorHelper();
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getArrangements(@Context HttpServletRequest request, @Context HttpServletResponse response,
			 @DefaultValue("0") @QueryParam("page") int pageIndex, 
			 @DefaultValue("50") @QueryParam("size") int size ) 
	{
		size=checkHelper.checkSize(size);
		pageIndex=checkHelper.checkPage(pageIndex);
		String userId = sessionHelper.getUserId(request);

		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}

		ArrayList<Arrangement> arrangementList = arrangementData.getArrangementsFromArrangementWrapperList(userId);
		Page<Arrangement> page = new Page<Arrangement>(arrangementList,pageIndex, size);
		Arrangements arrangements = new Arrangements(page);
		return Response.status(200).entity(arrangements).build();
	}

	
	@Path("/{arrangementId}")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getArrangement(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId )
	{
		String userId = sessionHelper.getUserId(request);
		
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}	
		
		ArrangementWrapper arrangementWrapper = arrangementData.getArrangementWrapper(arrangementId);
		Arrangement arrangement = arrangementData.getArrangementFromArrangementWrapper(arrangementWrapper); 		
		return Response.status(200).entity(arrangement).build();
	}
	
	
	@Path("/{arrangementId}/statements")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getStatement (@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId,
			@DefaultValue("0") @QueryParam("page")   int pageIndex,
			@DefaultValue("50") @QueryParam("size") int size ,
			@QueryParam("from")   String dateFrom,
			@QueryParam("to") String dateTo,
			@QueryParam("query") String query)
	{
		
		size=checkHelper.checkSize(size);
		pageIndex=checkHelper.checkPage(pageIndex);
		String userId = sessionHelper.getUserId(request);
		ArrayList<Transaction> transactionList = null;
		
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getDormantArrangement();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if (!checkHelper.checkTransactionListExists(arrangementId))
		{
			HashMap<String, ArrayList<Transaction>> transactionMap = transactionData.getTransactionMap();
			transactionMap.put(arrangementId, new ArrayList<Transaction>());
		}
		
		
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		
		
		if (checkHelper.checkExpiryDate(dateFrom,dateTo,wrapper))
		{
			Error e = errorHelper.getStatementExpiredError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
	
		if (!checkHelper.checkDatesFromAndTo(dateFrom,dateTo,wrapper))
		{
			Error e = errorHelper.getBadRequest();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
	

		if(dateFrom != null && !"".equals(dateFrom))
		{
			transactionList = transactionData.getTransactionListFilteredByDate(arrangementId, dateFrom, dateTo);
		
		}
		else
		{
			transactionList = transactionData.getTransactionList(arrangementId);
		}
		
		if(query != null)
		{
			transactionList = transactionData.filterTransactionsByName(transactionList, query);
		}
		Collections.reverse(transactionList);
		Page<Transaction> page = new Page<Transaction>(transactionList, pageIndex, size);
		Statement<Transaction> statement=new Statement<Transaction>();
		statement.setTransactions(page);
		return Response.status(200).entity(statement).build();
	}
	
	@Path("/{arrangementId}/beneficiaries")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getBeneficiaries(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId,
			 @DefaultValue("0") @QueryParam("page") int pageIndex, 
			 @DefaultValue("50") @QueryParam("size") int size) 
	{
		size=checkHelper.checkSize(size);
		pageIndex=checkHelper.checkPage(pageIndex);
		String userId = sessionHelper.getUserId(request);
		
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}

		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}	

		ArrayList<Beneficiary> beneficiariesList = beneficiaryData.getBeneficiaryList(arrangementId);
		Page<Beneficiary> page = new Page<Beneficiary>(beneficiariesList, pageIndex, size);
		Beneficiaries beneficiaries = new Beneficiaries(page);
		return Response.status(200).entity(beneficiaries).build();
	}
	

	
	@Path("/{arrangementId}/beneficiaries/{transactionId}")
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response confirmBeneficiary (@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId") String arrangementId,
			@PathParam("transactionId") String transactionId,
			@DefaultValue("")@FormParam("password") String password) 
	{
		
		String userId = sessionHelper.getUserId(request);
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		String[] paramArray = {password};
		if(!checkHelper.checkParamsForEmpty(paramArray))
		{	
			Error e = errorHelper.getBadRequest(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if (!miscHelper.authenticate(userId, password))
		{
			Error e = errorHelper.getInvalidCredentialError(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}

		if(!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId))
		{
			Error e = errorHelper.getNotFound(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}	
		
		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkIfBeneficiaryIsNull(sessionHelper, request,transactionId))
		{	
			Error e = errorHelper.getNotFound(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!arrangementData.getArrangementWrapper(arrangementId).isCreateBeneficiaryAvailable())
		{
			Error e = errorHelper.getBeneficiaryDeclinedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
	
		
		Beneficiary beneficiary = arrangementHelper.getBeneficiary(sessionHelper, request, transactionId);
		beneficiary.setEnabled(true);
		
		if (beneficiaryData.getBeneficiaryMap().containsKey(arrangementId))
		{
			beneficiaryData.getBeneficiaryList(arrangementId).add(beneficiary);
		}
		else
		{
			ArrayList<Beneficiary> newBeneficiariesList =new ArrayList<Beneficiary>();
			newBeneficiariesList.add(beneficiary);
			beneficiaryData.addNewBeneficiaryList(newBeneficiariesList,arrangementId);
		}
		sessionHelper.getSession().removeAttribute(transactionId);
		return Response.status(200).entity(beneficiary).build();
	}

	@Path("/{arrangementId}/beneficiaries")
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response createBeneficiary(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathParam("arrangementId")  String arrangementId,
			@DefaultValue("")@FormParam("sortCode")  String sortCode, 
			@DefaultValue("")@FormParam("accountNumber") String accountNumber,
			@DefaultValue("")@FormParam("name")  String name,
			@DefaultValue("")@FormParam("reference")String reference  ) {
			
		String userId = sessionHelper.getUserId(request);
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		String[] paramArray = {sortCode, accountNumber, name};
		if(checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkParamsForEmpty(paramArray))
		{	
			Error e = errorHelper.getBadRequest(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
	
		if(!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId))
		{
			Error e = errorHelper.getNotFound(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}	
		
		if(!arrangementData.getArrangementWrapper(arrangementId).isCreateBeneficiaryAvailable())
		{
			Error e = errorHelper.getBeneficiaryDeclinedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
	
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}	
		
		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(checkHelper.checkAccountIsWhiteListed(accountNumber, sortCode))
		{
			String[] referenceCheck = {reference};
			if(!checkHelper.checkParamsForEmpty(referenceCheck))
			{
				Error e = errorHelper.getBadRequest();
				return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
			}
		}	
		
		Beneficiary beneficiary = objectGenerator.createBeneficiary(accountNumber, sortCode, name,beneficiaryData.getNewBeneficiaryID());
		NewBeneficiary newBeneficiaryObject = objectGenerator.createNewBeneficiary(beneficiary.getId());
		beneficiary.setReferenceAllowed(true);
		sessionHelper.setBeneficiary(beneficiary);
	 	
	 	return Response.status(201).entity(newBeneficiaryObject).build();	
	}
	
	@Path("/{arrangementId}/beneficiaries/{beneficiaryId}/payments")
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createPayment(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@PathParam("arrangementId")  String arrangementId,
			@PathParam("beneficiaryId")  String beneficiaryId,
			@FormParam("date") String date, @FormParam("amount") String amount,
			@FormParam("currencyCode") String currencyCode, @FormParam("reference") String reference) {
		
		String userId = sessionHelper.getUserId(request);
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if (!checkHelper.checkArrangementIdBelongsToUser(userId,arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if (!checkHelper.checkBeneficiaryExists(arrangementId, beneficiaryId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();	
		}
		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		if(!arrangementData.getArrangementWrapper(arrangementId).isMakePaymentAvailable())
		{
			Error e = errorHelper.getPaymentsDisabledError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		double availableBalance = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getAvailableBalance().getAmount().toString());
		if(!checkHelper.checkAmountLessThanAvailableBalance(Double.parseDouble(amount),availableBalance))
		{
			Error e = errorHelper.getPaymentDeclinedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		Payment payment = objectGenerator.createNewPayment(reference, transactionData.getNewTransactionId());
		Transaction transaction = objectGenerator.createNewTransaction(amount, payment.getPaymentId(),date,currencyCode, arrangementId, "PMNT", "Paying money to someone",wrapper);
		sessionHelper.setTransaction(transaction);
		return Response.status(201).entity(payment).build();
	}

	@Path("/{arrangementId}/beneficiaries/{beneficiaryId}/payments/{paymentId}")
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response confirmPayment(@Context HttpServletRequest request,
			@Context HttpServletResponse response, 
			@PathParam("arrangementId") String arrangementId,
			@PathParam("beneficiaryId")  String beneficiaryId,
			@PathParam("paymentId")  String paymentId,
			@FormParam("password") String password) {
		
		
		String userId = sessionHelper.getUserId(request);
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if (!miscHelper.authenticate(userId, password))
		{
			Error e = errorHelper.getInvalidCredentialError(); 
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		if (!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		if (!checkHelper.checkBeneficiaryExists(arrangementId, beneficiaryId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();	
		}
		if(checkHelper.checkArrangementDormant(arrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}		

		if(checkHelper.checkArrangementSuspicious(arrangementId))
		{
			Error e = errorHelper.getHeldForReview();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}

		if(sessionHelper.returnTransactionSessionVariable(paymentId)==null)
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		Transaction transaction = sessionHelper.returnTransactionSessionVariable(paymentId); 
		double availableBalance = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getAvailableBalance().getAmount().toString());
		if(!checkHelper.checkAmountLessThanAvailableBalance(Double.parseDouble(transaction.getAmount().getAmount().toString()),availableBalance))
		{
			Error e = errorHelper.getPaymentDeclinedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		transactionData.getTransactionList(arrangementId).add(transaction);
		
		PaymentConfirmation confirmation = new PaymentConfirmation();		
		confirmation.setActualPaymentDate(transaction.getDate());
		confirmation.setReference(paymentId);
		
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Nothing to report");
		confirmation.setMessages(messages);
		
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		arrangementHelper.updateBalance(wrapper.getId(), transaction.getRunningBalance());
		arrangementHelper.makePaymentToLocalCustomer(arrangementId, beneficiaryId, transaction.getAmount());
		
		return Response.status(200).entity(confirmation).build();
	}

	@Path("/{arrangementId}/transfers")
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response makeTransfer(@Context HttpServletRequest request, 
			@Context HttpServletResponse response, 
			@PathParam("arrangementId") String arrangementId,
			@FormParam("targetArrangementId") String targetArrangementId,
			@FormParam("amount") String amount, 
			@DefaultValue("GBP")@FormParam("currencyCode") String currencyCode) {
		
		String userId = sessionHelper.getUserId(request);
		String[] paramArray = {amount,currencyCode,targetArrangementId};
		
		if (checkHelper.checkUserIdIsNull(userId))
		{
			Error e = errorHelper.getUnauthorisedError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkParamsForEmpty(paramArray))
		{
			Error e = errorHelper.getTransferDeclinedOtherReasonError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkArrangementIdBelongsToUser(userId, arrangementId) || 
				!checkHelper.checkArrangementIdBelongsToUser(userId, targetArrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		double amountDouble = Double.parseDouble(amount);
		double availableBalance = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getAvailableBalance().getAmount().toString());
		
		if(!checkHelper.checkAmountLessThanAvailableBalance(amountDouble,availableBalance))
		{
			Error e = errorHelper.getAmountExceedsBalanceError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!checkHelper.checkAmountAboveMinimum(amountDouble))
		{
			Error e = errorHelper.getBadRequest();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(arrangementId.equals(targetArrangementId))
		{
			Error e = errorHelper.getSameAccountsError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(!arrangementData.getArrangementWrapper(arrangementId).isMakeTransferAvailable() || !arrangementData.getArrangementWrapper(targetArrangementId).isMakeTransferAvailable())
		{
			Error e = errorHelper.getPaymentsDisabledError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		if(arrangementData.getArrangementWrapper(arrangementId).isProhibitiveIndicator() || arrangementData.getArrangementWrapper(targetArrangementId).isProhibitiveIndicator())
		{
			Error e = errorHelper.getProhibitiveIndicatorError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		if(checkHelper.checkArrangementDormant(arrangementId) || checkHelper.checkArrangementDormant(targetArrangementId))
		{
			Error e = errorHelper.getNotFound();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}

		if(!checkHelper.checkArrangementIsNotDisabled(arrangementId)||!checkHelper.checkArrangementIsNotDisabled(targetArrangementId))
		{
			Error e = errorHelper.getForcedLogoutError();
			sessionHelper.getSession().invalidate();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}	
		
		if(checkHelper.checkExceedsReceivingLimit(new BigDecimal(amount)))
		{
			Error e = errorHelper.getExceedsReceivingLimitError();
			return Response.status(Integer.valueOf(e.getCode())).entity(e).build();
		}
		
		CurrencyAmount amountToBeTransferedOut = new CurrencyAmount();
		BigDecimal negativeAmountDoubleRounded =new BigDecimal(-amountDouble).setScale(2,BigDecimal.ROUND_HALF_UP);;
		amountToBeTransferedOut.setAmount(negativeAmountDoubleRounded);
		amountToBeTransferedOut.setCurrency(CURRENCY_CODE.valueOf(currencyCode));
		
		CurrencyAmount amountToBeTransferedIn= new CurrencyAmount();
		BigDecimal amountDoubleRounded =new BigDecimal(amountDouble).setScale(2,BigDecimal.ROUND_HALF_UP);;
		amountToBeTransferedIn.setAmount(amountDoubleRounded);
		amountToBeTransferedIn.setCurrency(CURRENCY_CODE.valueOf(currencyCode));
		
		String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		ArrangementWrapper targetWrapper = arrangementData.getArrangementWrapper(targetArrangementId);
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		Transaction transferIn = objectGenerator.createNewTransaction(amount, transactionData.getNewTransactionId(), date, currencyCode, targetArrangementId, "DEP", "Money transfered from " + arrangementId, targetWrapper);
		Transaction transferOut = objectGenerator.createNewTransaction(amountToBeTransferedOut.getAmount().toString(), transactionData.getNewTransactionId(), date, currencyCode, arrangementId, "TRANSFER","Transfering Money to account " + targetArrangementId,wrapper);
		transactionData.getTransactionList(targetArrangementId).add(transferIn);
		transactionData.getTransactionList(arrangementId).add(transferOut);
		arrangementHelper.updateBalance(arrangementId, transferOut.getRunningBalance());
		arrangementHelper.updateBalance(targetArrangementId, transferIn.getRunningBalance());
		
		return Response.status(201).build();
				
	}
	
		public void setSessionHelper(SessionHelper sessionHelper) {
		this.sessionHelper = sessionHelper;
	}

}
