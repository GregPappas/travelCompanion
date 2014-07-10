/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.documentation.v1;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.springframework.stereotype.Service;

import com.lloydstsb.rest.util.populator.ObjectPopulator;
import com.lloydstsb.rest.v1.arrangements.CompleteCreateBeneficiaryService;
import com.lloydstsb.rest.v1.arrangements.CompletePaymentPasswordService;
import com.lloydstsb.rest.v1.arrangements.CompletePaymentTransactionSigningService;
import com.lloydstsb.rest.v1.arrangements.GetArrangementByIdService;
import com.lloydstsb.rest.v1.arrangements.GetArrangementsService;
import com.lloydstsb.rest.v1.arrangements.GetBeneficiariesByArrangementIdService;
import com.lloydstsb.rest.v1.arrangements.GetCurrentStatementByArrangementIdService;
import com.lloydstsb.rest.v1.arrangements.GetStatementBetweenDatesByArrangementIdService;
import com.lloydstsb.rest.v1.arrangements.InitiateCreateBeneficiaryService;
import com.lloydstsb.rest.v1.arrangements.InitiatePaymentService;
import com.lloydstsb.rest.v1.arrangements.MakeTransferService;
import com.lloydstsb.rest.v1.exceptions.AmountExceedsBalanceException;
import com.lloydstsb.rest.v1.exceptions.CreateBeneficiaryDeclined;
import com.lloydstsb.rest.v1.exceptions.ExceedsReceivingLimitException;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.exceptions.PaymentsDisabledException;
import com.lloydstsb.rest.v1.exceptions.ProhibitiveIndicatorException;
import com.lloydstsb.rest.v1.exceptions.SameAccountsException;
import com.lloydstsb.rest.v1.exceptions.StatementExpiredException;
import com.lloydstsb.rest.v1.exceptions.TransferDeclinedOtherReasonException;
import com.lloydstsb.rest.v1.exceptions.TransfersDisabledException;
import com.lloydstsb.rest.v1.valueobjects.Arrangements;
import com.lloydstsb.rest.v1.valueobjects.Beneficiaries;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;
import com.lloydstsb.rest.v1.valueobjects.Page;
import com.lloydstsb.rest.v1.valueobjects.Payment;
import com.lloydstsb.rest.v1.valueobjects.PaymentConfirmation;
import com.lloydstsb.rest.v1.valueobjects.Statement;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CurrentAccount;

@Service
public class ArrangementDocumentation implements GetArrangementsService, GetArrangementByIdService, GetCurrentStatementByArrangementIdService, /*GetStatementByArrangmentIdAndMonthService,*/ GetStatementBetweenDatesByArrangementIdService, GetBeneficiariesByArrangementIdService, InitiatePaymentService, CompletePaymentPasswordService, CompletePaymentTransactionSigningService, MakeTransferService, InitiateCreateBeneficiaryService, CompleteCreateBeneficiaryService {
	private static final int DEFAULT_LIST_TOTAL = 100;
	private ObjectPopulator objectPopulator = new ObjectPopulator();

	public Arrangements getArrangements(int pageIndex, int pageSize) {
		List<Arrangement> arrangementsList = new ArrayList<Arrangement>();
		arrangementsList.add(objectPopulator.populate(CurrentAccount.class));
//		arrangementsList.add(objectPopulator.populate(SavingsAccount.class));
//		arrangementsList.add(objectPopulator.populate(CreditCardAccount.class));
//		arrangementsList.add(objectPopulator.populate(IsaAccount.class));
//		arrangementsList.add(objectPopulator.populate(MortgageAccount.class));
//		arrangementsList.add(objectPopulator.populate(NonCbsPersonalLoanAccount.class));
//		arrangementsList.add(objectPopulator.populate(TermDepositAccount.class));
//		arrangementsList.add(objectPopulator.populate(CbsPersonalLoanAccount.class));
//		arrangementsList.add(objectPopulator.populate(SavedLoanAccount.class));
//		arrangementsList.add(objectPopulator.populate(InvestmentAccount.class));
//		arrangementsList.add(objectPopulator.populate(IsaInvestmentAccount.class));
//		arrangementsList.add(objectPopulator.populate(GiaAccount.class));
//		arrangementsList.add(objectPopulator.populate(CapAccount.class));
//		arrangementsList.add(objectPopulator.populate(MortgageUfssAccount.class));
//		arrangementsList.add(objectPopulator.populate(ShareDealingAccount.class));

		Page<Arrangement> arrangementPage = new Page<Arrangement>();
		arrangementPage.setItems(arrangementsList);
		arrangementPage.setPage(pageIndex);
		arrangementPage.setSize(pageSize);
		arrangementPage.setTotal(DEFAULT_LIST_TOTAL);
		
		Arrangements arrangements = new Arrangements();
		arrangements.setArrangements(arrangementPage);
		
		arrangements.setMessages(getMessages());

		return arrangements;
	}

	private List<String> getMessages() {
		List<String> messages = new ArrayList<String>();
		messages.add(objectPopulator.populate(String.class));
		return messages;
	}

	public Arrangement getArrangementById(String arrangementId) {
		return objectPopulator.populate(CurrentAccount.class);
	}

	public Statement getCurrentStatementByArrangementId(String arrangementId, String query, int page, int size) throws NotFoundException, StatementExpiredException {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(objectPopulator.populate(Transaction.class));
		transactions.add(objectPopulator.populate(Transaction.class));

		Page<Transaction> transactionList = new Page<Transaction>();
		transactionList.setItems(transactions);
		transactionList.setPage(page);
		transactionList.setSize(size);
		transactionList.setTotal(DEFAULT_LIST_TOTAL);

		Statement statement = objectPopulator.populate(Statement.class);
		statement.setTransactions(transactionList);
		
		statement.setMessages(getMessages());

		return statement;
	}

	public Statement getStatementByArrangementIdAndMonth(String arrangementId, YearMonth period, String query, int page, int size) throws NotFoundException, StatementExpiredException {
		return getCurrentStatementByArrangementId(arrangementId, query, page, size);
	}

	public Statement getStatementBetweenDatesByArrangementId(String arrangementId, LocalDate start, LocalDate end, String query, int page, int size) throws NotFoundException, StatementExpiredException {
		return getCurrentStatementByArrangementId(arrangementId, query, page, size);
	}

	public Beneficiaries getBeneficiaries(String arrangementId, int pageIndex, int pageSize) {
		List<Beneficiary> beneficiariesList = new ArrayList<Beneficiary>();
		beneficiariesList.add((Beneficiary) objectPopulator.populate(Beneficiary.class));
		beneficiariesList.add((Beneficiary) objectPopulator.populate(Beneficiary.class));

		Page<Beneficiary> beneficiaryPage = new Page<Beneficiary>();
		beneficiaryPage.setPage(pageIndex);
		beneficiaryPage.setSize(pageSize);
		beneficiaryPage.setItems(beneficiariesList);
		beneficiaryPage.setTotal(DEFAULT_LIST_TOTAL);
		
		Beneficiaries beneficiaries = new Beneficiaries();
		beneficiaries.setBeneficiaries(beneficiaryPage);
		beneficiaries.setMessages(getMessages());

		return beneficiaries;
	}

	public PaymentConfirmation completePaymentWithPassword(String arrangementId, String beneficiaryId, String paymentId, String password) {
		PaymentConfirmation confirmation = objectPopulator.populate(PaymentConfirmation.class);
		return confirmation;
	}

	public PaymentConfirmation completeSignedPayment(String arrangementId, String beneficiaryId, String paymentId, String password) {
		PaymentConfirmation confirmation = objectPopulator.populate(PaymentConfirmation.class);
		return confirmation;
	}

	public Payment initiatePayment(String arrangementId, String beneficiaryId, String date, String amount, String currencyCode, String reference) {
		Payment payment = objectPopulator.populate(Payment.class);
		return payment;
	}

	public void makeTransfer(String sourceArrangementId, String targetArrangementId, String amount, String currencyCode)
			throws NotFoundException, TransfersDisabledException,
			TransferDeclinedOtherReasonException, AmountExceedsBalanceException,
			SameAccountsException,
			ExceedsReceivingLimitException,
			ProhibitiveIndicatorException {
		// nothing returned
	}

	public Beneficiary completeCreateBeneficiary(String arrangementId, String beneficiaryId, String password) throws NotFoundException, CreateBeneficiaryDeclined {
		Beneficiary ben = objectPopulator.populate(Beneficiary.class);
		return ben;
	}

	public NewBeneficiary initiateCreateBeneficiary(String arrangementId, String beneficiarySortCode, String beneficiaryAccountNumber, String beneficiaryName, String beneficiaryReference) throws NotFoundException, PaymentsDisabledException {
		NewBeneficiary ben = objectPopulator.populate(NewBeneficiary.class);
		return ben;
	}

//	public CreditCardStatement getCreditCardStatementBetweenDatesByArrangementId(String arrangementId, LocalDate from, LocalDate to, String query, int page, int size) throws NotFoundException, StatementExpiredException {
//		return getCurrentCreditCardStatementByArrangementId(null, null, 0, 0);
//	}
//
//	public CreditCardStatement getCreditCardStatementByArrangementIdAndMonth(String arrangementId, YearMonth period, String query, int page, int size) throws NotFoundException, StatementExpiredException {
//		return getCurrentCreditCardStatementByArrangementId(null, null, 0, 0);
//	}

//	public CreditCardStatement getCurrentCreditCardStatementByArrangementId(String arrangementId, String query, int page, int size) throws NotFoundException, StatementExpiredException {
//		List<CreditCardTransaction> transactions = new ArrayList<CreditCardTransaction>();
//		transactions.add(objectPopulator.populate(CreditCardTransaction.class));
//		transactions.add(objectPopulator.populate(CreditCardTransaction.class));
//
//		Page<CreditCardTransaction> transactionList = new Page<CreditCardTransaction>();
//		transactionList.setItems(transactions);
//		transactionList.setPage(page);
//		transactionList.setSize(size);
//		transactionList.setTotal(DEFAULT_LIST_TOTAL);
//
//		CreditCardStatement statement = objectPopulator.populate(CreditCardStatement.class);
//		statement.setTransactions(transactionList);
//
//		return statement;
//	}
}
