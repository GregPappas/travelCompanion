package com.lloydstsb.rest.v1.data;

import java.util.ArrayList;
import java.util.Date;

import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ArrangementType;


public class ArrangementWrapper
{
	
	
	private CurrencyAmount monthlyPayment;
	private CurrencyAmount availableBalance;
	private CurrencyAmount overdraftLimit;
	private CurrencyAmount limit;
	private CurrencyAmount balance;
	private CurrencyAmount overdueAmount;
	
	private boolean isAcceptsTransfers;
	private boolean isCreateBeneficiaryAvailable;
	
	
	private boolean isMakePaymentAvailable;
	private boolean isMakeTransferAvailable;
	private boolean isNonMigratedAccount;
	private boolean isOtherBrand;
	private boolean isProhibitiveIndicator;

	private String id;
	private String accountName;
	private String accountNumber;
	private String sortCode;
	private String otherBrandBankName;
	private String cardNumber;
	private String referenceNumber;
	
	
	private ArrangementType type;
	private ArrayList<String> messages;
	private Date startDate;
	private Date lastStatementDate;
	private Date mortgageAsAt;
	
	private ArrangementStateType state;
	
	private int statementValiditySpan;
		
	
	public ArrangementStateType getState() {
		return state;
	}
	public void setState(ArrangementStateType state) {
		this.state = state;
	}
	public CurrencyAmount getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(CurrencyAmount monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	public CurrencyAmount getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(CurrencyAmount availableBalance) {
		this.availableBalance = availableBalance;
	}
	public CurrencyAmount getOverdraftLimit() {
		return overdraftLimit;
	}
	public void setOverdraftLimit(CurrencyAmount overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	public CurrencyAmount getLimit() {
		return limit;
	}
	public void setLimit(CurrencyAmount limit) {
		this.limit = limit;
	}
	public CurrencyAmount getBalance() {
		return balance;
	}
	public void setBalance(CurrencyAmount balance) {
		this.balance = balance;
	}
	public CurrencyAmount getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(CurrencyAmount overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public boolean isAcceptsTransfers() {
		return isAcceptsTransfers;
	}
	public void setAcceptsTransfers(boolean isAcceptsTransfers) {
		this.isAcceptsTransfers = isAcceptsTransfers;
	}
	public boolean isCreateBeneficiaryAvailable() {
		return isCreateBeneficiaryAvailable;
	}
	public void setCreateBeneficiaryAvailable(boolean isCreateBeneficiaryAvailable) {
		this.isCreateBeneficiaryAvailable = isCreateBeneficiaryAvailable;
	}
	public boolean isMakePaymentAvailable() {
		return isMakePaymentAvailable;
	}
	public void setMakePaymentAvailable(boolean isMakePaymentAvailable) {
		this.isMakePaymentAvailable = isMakePaymentAvailable;
	}
	public boolean isMakeTransferAvailable() {
		return isMakeTransferAvailable;
	}
	public void setMakeTransferAvailable(boolean isMakeTransferAvailable) {
		this.isMakeTransferAvailable = isMakeTransferAvailable;
	}
	public boolean isNonMigratedAccount() {
		return isNonMigratedAccount;
	}
	public void setNonMigratedAccount(boolean isNonMigratedAccount) {
		this.isNonMigratedAccount = isNonMigratedAccount;
	}
	public boolean isOtherBrand() {
		return isOtherBrand;
	}
	public void setOtherBrand(boolean isOtherBrand) {
		this.isOtherBrand = isOtherBrand;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	public String getOtherBrandBankName() {
		return otherBrandBankName;
	}
	public void setOtherBrandBankName(String otherBrandBankName) {
		this.otherBrandBankName = otherBrandBankName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public ArrangementType getType() {
		return type;
	}
	public void setType(ArrangementType type) {
		this.type = type;
	}
	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
	public Date getStartDate() {
		if(startDate == null)
		{
			return null;
		}
		return (Date)startDate.clone();
	}
	public void setStartDate(Date startDate) {
		if(startDate == null)
		{
		this.startDate = null;
		}
		else
		{
			this.startDate = new Date(startDate.getTime());
		}
	}
	public Date getLastStatementDate() {
		if(lastStatementDate == null)
		{
		return null;
		}
		return (Date)lastStatementDate.clone();
	}
	public void setLastStatementDate(Date lastStatementDate) {
		if(lastStatementDate == null)
		{
			this.lastStatementDate = null;
		}
		else
		{
		this.lastStatementDate  = new Date(lastStatementDate.getTime());
		}
		
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public Date getMortgageAsAt() {
		if(mortgageAsAt == null)
		{
			return null;
		}
		else
		{
		return (Date) mortgageAsAt.clone();
		}
	}
	public void setMortgageAsAt(Date mortgageAsAt) {
		if(mortgageAsAt == null)
		{
			this.mortgageAsAt = null;
		}
		else
		{
			this.mortgageAsAt = new Date(mortgageAsAt.getTime());
		}
	}
	public int getStatementValiditySpan() {
		return statementValiditySpan;
	}
	public void setStatementValiditySpan(String statementValiditySpan) {
		this.statementValiditySpan = Integer.parseInt(statementValiditySpan);
	}
	
	public boolean isProhibitiveIndicator() {
		return isProhibitiveIndicator;
	}
	public void setProhibitiveIndicator(boolean isProhibitiveIndicator) {
		this.isProhibitiveIndicator = isProhibitiveIndicator;
	}
	
	
}
