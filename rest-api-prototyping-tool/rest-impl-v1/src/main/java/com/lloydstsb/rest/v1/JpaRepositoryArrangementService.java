/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lloydstsb.rest.v1.arrangements.CompleteCreateBeneficiaryService;
import com.lloydstsb.rest.v1.arrangements.CompletePaymentPasswordService;
import com.lloydstsb.rest.v1.arrangements.CompletePaymentTransactionSigningService;
import com.lloydstsb.rest.v1.arrangements.GetArrangementByIdService;
import com.lloydstsb.rest.v1.arrangements.GetArrangementsService;
import com.lloydstsb.rest.v1.arrangements.GetBeneficiariesByArrangementIdService;
import com.lloydstsb.rest.v1.arrangements.GetCurrentStatementByArrangementIdService;
import com.lloydstsb.rest.v1.arrangements.GetStatementBetweenDatesByArrangementIdService;
import com.lloydstsb.rest.v1.arrangements.GetStatementByArrangmentIdAndMonthService;
import com.lloydstsb.rest.v1.arrangements.InitiateCreateBeneficiaryService;
import com.lloydstsb.rest.v1.arrangements.InitiatePaymentService;
import com.lloydstsb.rest.v1.arrangements.MakeTransferService;
import com.lloydstsb.rest.v1.delegate.ArrangementDelegate;
import com.lloydstsb.rest.v1.delegate.BeneficiaryDelegate;
import com.lloydstsb.rest.v1.delegate.CustomerDelegate;
import com.lloydstsb.rest.v1.delegate.TransactionDelegate;
import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.BeneficiaryDomain;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.domain.DomainList;
import com.lloydstsb.rest.v1.domain.TransactionDomain;
import com.lloydstsb.rest.v1.exceptions.AmountExceedsBalanceException;
import com.lloydstsb.rest.v1.exceptions.ExceedsReceivingLimitException;
import com.lloydstsb.rest.v1.exceptions.ISAFallowAccountException;
import com.lloydstsb.rest.v1.exceptions.ISAMinimumTransferException;
import com.lloydstsb.rest.v1.exceptions.ISAOverseasCustomerException;
import com.lloydstsb.rest.v1.exceptions.ISARemaningDepositExceededException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.ProhibitiveIndicatorException;
import com.lloydstsb.rest.v1.exceptions.SameAccountsException;
import com.lloydstsb.rest.v1.exceptions.StatementExpiredException;
import com.lloydstsb.rest.v1.exceptions.TransferDeclinedOtherReasonException;
import com.lloydstsb.rest.v1.exceptions.TransfersDisabledException;
import com.lloydstsb.rest.v1.mapping.ArrangementMapper;
import com.lloydstsb.rest.v1.mapping.BeneficiaryMapper;
import com.lloydstsb.rest.v1.mapping.TransactionMapper;
import com.lloydstsb.rest.v1.valueobjects.Arrangements;
import com.lloydstsb.rest.v1.valueobjects.AuthenticationType;
import com.lloydstsb.rest.v1.valueobjects.Beneficiaries;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;
import com.lloydstsb.rest.v1.valueobjects.Page;
import com.lloydstsb.rest.v1.valueobjects.Payment;
import com.lloydstsb.rest.v1.valueobjects.PaymentConfirmation;
import com.lloydstsb.rest.v1.valueobjects.Statement;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;

import static com.lloydstsb.rest.v1.exceptions.ImplIbErrorCode.errorDescCode;

@Service
public class JpaRepositoryArrangementService implements GetArrangementsService, GetArrangementByIdService, GetCurrentStatementByArrangementIdService, GetStatementByArrangmentIdAndMonthService, GetStatementBetweenDatesByArrangementIdService, GetBeneficiariesByArrangementIdService, InitiatePaymentService, CompletePaymentPasswordService, CompletePaymentTransactionSigningService, MakeTransferService, InitiateCreateBeneficiaryService, CompleteCreateBeneficiaryService {

	@Autowired
	private ArrangementDelegate arrangementDelegate;

	@Autowired
	private TransactionDelegate transactionDelegate;

	@Autowired
	private BeneficiaryDelegate beneficiaryDelegate;

	@Autowired
	private CustomerDelegate customerDelegate;

	@Autowired
	private ArrangementMapper arrangementMapper;

	@Autowired
	private BeneficiaryMapper beneficiaryMapper;

	@Autowired
	private TransactionMapper transactionMapper;

	@Context
	private HttpServletRequest request;

	public Arrangements getArrangements(int pageIndex, int pageSize) {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		DomainList<ArrangementDomain> arrangementDomains = arrangementDelegate.getArrangements(customer, pageIndex, pageSize);
		List<Arrangement> arrangementsList = arrangementMapper.map(arrangementDomains.getEntities());

		Page<Arrangement> arrangementList = new Page<Arrangement>();
		arrangementList.setItems(arrangementsList);
		arrangementList.setPage(pageIndex);
		arrangementList.setSize(pageSize);
		arrangementList.setTotal(arrangementDomains.getTotalAvailable());

		return new Arrangements(arrangementList);
	}

	public Arrangement getArrangementById(String arrangementId) throws NotFoundException {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		ArrangementDomain customerArrangement = arrangementDelegate.getArrangement(customer, arrangementId);

		if (customerArrangement != null) {
			return arrangementMapper.map(customerArrangement);
		}

		throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
	}

	public Statement<? extends Transaction> getStatementBetweenDatesByArrangementId(String arrangementId, LocalDate from, LocalDate to, String query, int page, int size) throws NotFoundException, StatementExpiredException {
		String customerId = Util.getAuthenticatedCustomerId(request);

		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}

		Calendar start = Calendar.getInstance();
		start.setTime(from.toDate());

		Calendar end = Calendar.getInstance();
		end.setTime(to.toDate());

		DateTime startDate = new DateTime().withYear(start.get(Calendar.YEAR)).withMonthOfYear(start.get(Calendar.MONTH));
		startDate = startDate.dayOfMonth().withMinimumValue();

		DateTime endDate = new DateTime().withYear(end.get(Calendar.YEAR)).withMonthOfYear(end.get(Calendar.MONTH));
		endDate = endDate.dayOfMonth().withMaximumValue();

		return getStatement(customerId, arrangementId, startDate, endDate, page, size);
	}

	public Statement<? extends Transaction> getStatementByArrangementIdAndMonth(String arrangementId, YearMonth period, String query, int page, int size) throws NotFoundException, StatementExpiredException {
		String customerId = Util.getAuthenticatedCustomerId(request);

		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}

		return getStatement(customerId, arrangementId, period, page, size);
	}

	public Statement<? extends Transaction> getCurrentStatementByArrangementId(String arrangementId, String query, int page, int size) throws NotFoundException, StatementExpiredException {
		String customerId = Util.getAuthenticatedCustomerId(request);

		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}

		final YearMonth now = YearMonth.now();
		return getStatement(customerId, arrangementId, now, page, size);
	}

	private Statement getStatement(String customerId, String arrangementId, YearMonth ym, int pageIndex, int pageSize) throws NotFoundException {
		final DateTimeField monthField = ym.getChronology().dayOfMonth();
		final LocalDate firstDayOfMonth = ym.toLocalDate(monthField.getMinimumValue(ym));
		final LocalDate lastDayOfMonth = ym.toLocalDate(monthField.getMaximumValue(ym));

		final DateTime firstDateTime = firstDayOfMonth.toDateTimeAtStartOfDay();
		// Start of the next day but one tick less.
		final DateTime lastDateTime = lastDayOfMonth.plusDays(1).toDateTimeAtStartOfDay().minus(1);

		return getStatement(customerId, arrangementId, firstDateTime, lastDateTime, pageIndex, pageSize);
	}

	private Statement getStatement(String customerId, String arrangementId, DateTime start, DateTime end, int pageIndex, int pageSize) throws NotFoundException {
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		ArrangementDomain arrangement = arrangementDelegate.getArrangement(customer, arrangementId);
		DomainList<TransactionDomain> transactionDomains = transactionDelegate.getTransactionsBetweenDates(customer, arrangement, start.toDate(), end.toDate(), pageIndex, pageSize);
		List<Transaction> transactions = transactionMapper.map(transactionDomains.getEntities());

		Page<Transaction> transactionList = new Page<Transaction>();
		transactionList.setPage(pageIndex);
		transactionList.setSize(pageSize);
		transactionList.setTotal(transactionDomains.getTotalAvailable());
		transactionList.setItems(transactions);

		Statement statement = new Statement();
		statement.setTransactions(transactionList);

		return statement;
	}

	public Beneficiaries getBeneficiaries(String arrangementId, int pageIndex, int pageSize) throws NotFoundException {
		String customerId = Util.getAuthenticatedCustomerId(request);

		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}

		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		ArrangementDomain arrangement = arrangementDelegate.getArrangement(customer, arrangementId);
		DomainList<BeneficiaryDomain> beneficiaryDomains = beneficiaryDelegate.getBeneficiariesByArrangement(arrangement, pageIndex, pageSize);
		List<Beneficiary> beneficiaries = beneficiaryMapper.map(beneficiaryDomains.getEntities());

		Page<Beneficiary> beneficiaryList = new Page<Beneficiary>();
		beneficiaryList.setPage(pageIndex);
		beneficiaryList.setSize(pageSize);
		beneficiaryList.setTotal(beneficiaryDomains.getTotalAvailable());
		beneficiaryList.setItems(beneficiaries);

		return new Beneficiaries(beneficiaryList);
	}

	public PaymentConfirmation completePaymentWithPassword(String arrangementId, String beneficiaryId, String paymentId, String password) throws NotFoundException {
		TransactionDomain domain = transactionDelegate.getTransaction(paymentId);

		PaymentConfirmation confirmation = new PaymentConfirmation();
		if (domain == null) {
			throw new NotFoundException(errorDescCode("Transaction not found", ""));
		}
		confirmation.setActualPaymentDate(domain.getTransactionDate());
		return confirmation;
	}

	public PaymentConfirmation completeSignedPayment(String arrangementId, String beneficiaryId, String paymentId, String password) throws NotFoundException {
		TransactionDomain domain = transactionDelegate.getTransaction(paymentId);

		PaymentConfirmation confirmation = new PaymentConfirmation();
		if (domain == null) {
			throw new NotFoundException(errorDescCode("Transaction not found", ""));
		}
		confirmation.setActualPaymentDate(domain.getTransactionDate());
		return confirmation;
	}

	public Payment initiatePayment(String arrangementId, String beneficiaryId, String date, String amount, String currencyCode, String reference) throws NotFoundException {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}
		ArrangementDomain arrangement = arrangementDelegate.getArrangement(customer, arrangementId);

		TransactionDomain transaction = new TransactionDomain();
		transaction.setArrangement(arrangement);
		transaction.setAmount(amount);
		transaction.setAmountCurrency(currencyCode);
		transaction.setDescription("New payment to " + beneficiaryId);
		transaction.setTransactionDate(DatatypeConverter.parseDateTime(date).getTime());
		transaction.setTransactionId(String.valueOf(UUID.randomUUID()));
		transactionDelegate.saveTransaction(transaction);

		Payment payment = new Payment();
		payment.setAuthenticationType(AuthenticationType.PASSWORD);
		payment.setFasterPaymentOffered(true);
		payment.setProcessAsFasterPayment(true);
		payment.setPaymentId(transaction.getTransactionId());
		return payment;
	}

	public void makeTransfer(String sourceArrangementId, String targetArrangementId, String amount, String currencyCode)
			throws NotFoundException, TransfersDisabledException,
			TransferDeclinedOtherReasonException, AmountExceedsBalanceException,
			ISARemaningDepositExceededException, SameAccountsException,
			ISAFallowAccountException, ISAOverseasCustomerException,
			ISAMinimumTransferException, ExceedsReceivingLimitException,
			ProhibitiveIndicatorException {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		ArrangementDomain arrangement = arrangementDelegate.getArrangement(customer, sourceArrangementId);
		TransactionDomain transaction = new TransactionDomain();
		transaction.setArrangement(arrangement);
		transaction.setAmount(amount);
		transaction.setAmountCurrency(currencyCode);
		transaction.setDescription("New transfer to account " + targetArrangementId);
		transaction.setTransactionDate(new Date());
		transaction.setTransactionId(String.valueOf(UUID.randomUUID()));
		transactionDelegate.saveTransaction(transaction);
	}

	public Beneficiary completeCreateBeneficiary(String arrangementId, String beneficiaryId, String password) throws NotFoundException {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);

		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}
		ArrangementDomain arrangement = arrangementDelegate.getArrangement(customer, arrangementId);

		BeneficiaryDomain beneficiary = new BeneficiaryDomain();
		beneficiary.setArrangement(arrangement);
		beneficiary.setBeneficiaryId(beneficiaryId);
		beneficiaryDelegate.saveBeneficiary(beneficiary);
		// BeneficiaryDomain beneficiary =
		// beneficiaryDelegate.getBeneficiaryByBeneficiaryId(customerId,
		// beneficiaryId);
		Beneficiary ben = beneficiaryMapper.map(beneficiary);
		return ben;
	}

	public NewBeneficiary initiateCreateBeneficiary(String arrangementId, String beneficiarySortCode, String beneficiaryAccountNumber, String beneficiaryName, String beneficiaryReference) throws NotFoundException {

		String customerId = Util.getAuthenticatedCustomerId(request);
		// CustomerDomain customer = customerDelegate.getCustomer(customerId);
		if (!arrangementExistsForCustomer(customerId, arrangementId)) {
			throw new NotFoundException(errorDescCode("Cannot find arrangement", ""));
		}
		// ArrangementDomain arrangement =
		// arrangementDelegate.getArrangement(customer, arrangementId);

		NewBeneficiary ben = new NewBeneficiary();
		ben.setAuthenticationType(AuthenticationType.PASSWORD);
		ben.setTransactionId(String.valueOf(UUID.randomUUID()));
		return ben;
	}

	private boolean arrangementExistsForCustomer(String customerId, String arrangementId) {
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		ArrangementDomain customerArrangement = arrangementDelegate.getArrangement(customer, arrangementId);
		if (customerArrangement != null) {
			return true;
		}

		return false;
	}
}
