package com.lloydstsb.rest.v1.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.Payment;
import com.lloydstsb.rest.v1.valueobjects.PhoneNumber;
import com.lloydstsb.rest.v1.valueobjects.Transaction;

public class PersistenceContainer {
	public static final PersistenceContainer persistenceContainerInstance = new PersistenceContainer();
	private HashMap<String,ArrayList<Beneficiary>> beneficiary;
	private ServletContext context;
	private HashMap<String, PhoneNumber> phoneNumbers;
	private HashMap <String, ExchangeLockCustomer> ExchangeLockCustomers;
	private HashMap<String, ArrayList<Transaction>> transactionMap;
	private HashMap<String, ArrayList<ArrangementWrapper>> arrangementWrapperMap;
	private BigDecimal receivingLimit = new BigDecimal(1000000);
	public PersistenceContainer(){}
	private long beneficiaryID=500000000;
	private long transactionID=2000;
	
	public HashMap<String, ArrayList<ArrangementWrapper>> getArrangementWrapper()
	{
		return arrangementWrapperMap;
	}
    public HashMap<String, Payment> paymentMap = new HashMap<String, Payment>();

    public void setPayment(String userId, Payment payment)
    {
        paymentMap.put(userId, payment);
    }

    public Payment getPayment(String userId)
    {
       return paymentMap.get(userId);
    }

	public void setArrangementWrapper(HashMap<String,ArrayList<ArrangementWrapper>> arrangementWrapperMap)
	{
		this.arrangementWrapperMap = arrangementWrapperMap;
	}
	
	public synchronized String getNewBeneficiaryID()
	{
		beneficiaryID++;
		return String.valueOf(beneficiaryID);
	}
	public synchronized String getNewTransactionID()
	{
		transactionID++;
		return String.valueOf(transactionID);
	}
	
	public HashMap<String, ArrayList<Beneficiary>> getBeneficiaries() {
		return beneficiary;
	}

	public void setBeneficiary(HashMap<String, ArrayList<Beneficiary>> beneficiary) {
		this.beneficiary = beneficiary;
	}

	public HashMap<String, ArrayList<Transaction>> getTransactionMap() {
		return transactionMap;
	}

	public void setTransactionMap(HashMap<String, ArrayList<Transaction>> transactionMap) {
		this.transactionMap = transactionMap;
	}

	public static PersistenceContainer getInstance(){
		return persistenceContainerInstance;
	}
	
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
	public void setPhoneNumbers(HashMap <String, PhoneNumber> phoneNumbers)
	{
		this.phoneNumbers=phoneNumbers;
	}
	public HashMap <String, PhoneNumber> getPhoneNumbers ()
	{
		return phoneNumbers;
	}
	
	public void setExchangeLockCustomers(HashMap <String, ExchangeLockCustomer> ExchangeLockCustomers)
	{
		this.ExchangeLockCustomers=ExchangeLockCustomers;
	}
	public HashMap <String, ExchangeLockCustomer> getExchangeLockCustomers()
	{
		return ExchangeLockCustomers;
	}

	public synchronized void clear() 
	{
		beneficiary.clear();
		phoneNumbers.clear();
		ExchangeLockCustomers.clear();;
		transactionMap.clear();
		arrangementWrapperMap.clear();
		beneficiaryID=500000000;
		transactionID=25;	
	}

	public void setTransactionLimitAmount(BigDecimal newReceivingLimit) {
		receivingLimit = newReceivingLimit;
		
	}
	public BigDecimal getTransactionLimitAmount()
	{
		return receivingLimit;
	}

    public void resetPayment() {
        this.paymentMap = new HashMap<String,Payment>();
    }
}
