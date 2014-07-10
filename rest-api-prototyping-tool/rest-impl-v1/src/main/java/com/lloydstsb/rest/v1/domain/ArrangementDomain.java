package com.lloydstsb.rest.v1.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bank_bankaccount")
public class ArrangementDomain extends BaseDomain {

	@Id
	private String arrangementId;

	private String accountType;

	private String accountName;

	private String sortCode;

	private String accountNumber;
	
	@Column(name="balance")
	private String balanceAmount;
	
	private String balanceCurrency;
	
	private boolean hasWarnings;
	
	private String warnings;
    
	private boolean hasReminders; 
	
    private String reminders;
    
    private String isGhostAccount;

    private String loanNumber;
    
    private String availableBalance;
    
    private String overdraftLimit;
    
    private String cardNumber;
    
    private String creditLimit;
    
    private Integer totalEarned;
    
    private String remainingAllowance;
    
    private String monthlyPayment;
    
    private Date balanceAsAtDate;
    
    private String originalAmount;
    
    private Date maturityDate;
    
    private Date expiryDate;
    
    private String infoString;
    
    private Date startDate;
    
    private Date creditCardDueDate;
    
    private String creditCardMinimumPayment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private CustomerDomain customer;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "arrangement")
	private Set<TransactionDomain> transactions = new HashSet<TransactionDomain>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "arrangement")
	private Set<BeneficiaryDomain> beneficiaries = new HashSet<BeneficiaryDomain>();

	public String getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(String arrangementId) {
		this.arrangementId = arrangementId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getBalanceCurrency() {
		return balanceCurrency;
	}

	public void setBalanceCurrency(String balanceCurrency) {
		this.balanceCurrency = balanceCurrency;
	}

	public boolean isHasWarnings() {
		return hasWarnings;
	}

	public void setHasWarnings(boolean hasWarnings) {
		this.hasWarnings = hasWarnings;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public boolean isHasReminders() {
		return hasReminders;
	}

	public void setHasReminders(boolean hasReminders) {
		this.hasReminders = hasReminders;
	}

	public String getReminders() {
		return reminders;
	}

	public void setReminders(String reminders) {
		this.reminders = reminders;
	}

	public String getIsGhostAccount() {
		return isGhostAccount;
	}

	public void setIsGhostAccount(String isGhostAccount) {
		this.isGhostAccount = isGhostAccount;
	}

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(String overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Integer getTotalEarned() {
		return totalEarned;
	}

	public void setTotalEarned(Integer totalEarned) {
		this.totalEarned = totalEarned;
	}

	public String getRemainingAllowance() {
		return remainingAllowance;
	}

	public void setRemainingAllowance(String remainingAllowance) {
		this.remainingAllowance = remainingAllowance;
	}

	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Date getBalanceAsAtDate() {
		return balanceAsAtDate;
	}

	public void setBalanceAsAtDate(Date balanceAsAtDate) {
		this.balanceAsAtDate = balanceAsAtDate;
	}

	public String getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getInfoString() {
		return infoString;
	}

	public void setInfoString(String infoString) {
		this.infoString = infoString;
	}

	public CustomerDomain getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDomain customer) {
		this.customer = customer;
	}

	public Set<TransactionDomain> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<TransactionDomain> transactions) {
		this.transactions = transactions;
	}

	public Set<BeneficiaryDomain> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(Set<BeneficiaryDomain> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCreditCardDueDate() {
		return creditCardDueDate;
	}

	public void setCreditCardDueDate(Date creditCardDueDate) {
		this.creditCardDueDate = creditCardDueDate;
	}

	public String getCreditCardMinimumPayment() {
		return creditCardMinimumPayment;
	}

	public void setCreditCardMinimumPayment(String creditCardMinimumPayment) {
		this.creditCardMinimumPayment = creditCardMinimumPayment;
	}
}