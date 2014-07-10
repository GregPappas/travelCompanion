package com.lloydstsb.rest.v1.helpers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.valueobjects.AuthenticationType;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;
import com.lloydstsb.rest.v1.valueobjects.Payment;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.TransactionType;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CurrentAccount;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ProductArrangement;


public class ObjectGenerator 
{
	private MiscHelper miscHelper = new MiscHelper();
	
	public Beneficiary createBeneficiary(String accNo, String sortCode, String name, String newBeneficiaryID)
	{
		String beneficiaryId = newBeneficiaryID;
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setAccountNumber(accNo);
		beneficiary.setId(beneficiaryId);
		beneficiary.setSortCode(sortCode);
		beneficiary.setName(name);
		return beneficiary;
	}	
	
	public NewBeneficiary createNewBeneficiary(String beneficiaryId)
	{
		NewBeneficiary tempBeneficiary = new NewBeneficiary();
		tempBeneficiary.setAuthenticationType(AuthenticationType.PASSWORD);
		tempBeneficiary.setTransactionId(beneficiaryId);
		return tempBeneficiary;
	}	

	
	
	public Payment createNewPayment(String reference, String newPaymentId)
	{
		Payment payment=new Payment();
		String paymentId = newPaymentId;
		payment.setPaymentId(paymentId);
		payment.setAuthenticationType(AuthenticationType.PASSWORD);
		payment.setFasterPaymentOffered(true);
		payment.setInternalPayment(true);
		payment.setInternalPaymentAllowed(true);
			ArrayList<String> messages=new ArrayList<String>();
		payment.setMessages(messages);
		payment.setProcessAsFasterPayment(true);
		payment.setReference(reference);
		
		return payment;
	}
	
	public Transaction createNewTransaction(String amount, String paymentId,String date,String currencyCode, String arrangementId, String type, String description, ArrangementWrapper wrapper)
	{
		Transaction transaction =new Transaction();
		CurrencyAmount currencyAmount = new CurrencyAmount();
		double amountToSet = 0;
		if (Double.parseDouble(amount)<0)
		{
			amountToSet = Double.parseDouble(amount) * -1;
		}
		else
		{
			amountToSet = Double.parseDouble(amount);
		}
		BigDecimal decimal = new BigDecimal(Double.valueOf(amountToSet));
		currencyAmount.setAmount(decimal);
		currencyAmount.setCurrency(CurrencyAmount.CURRENCY_CODE.valueOf(currencyCode));		
		transaction.setAmount(currencyAmount);
		Date newDate;
		try {
			newDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			transaction.setDate(newDate);
		} catch (ParseException e) {
			// Auto-generated catch block
		}
		transaction.setDescription(description);
		transaction.setId(paymentId);
		ArrangementWrapper arrangement = wrapper;
		
		
		
		CurrencyAmount runningBalance = new CurrencyAmount();//= lastTransactionClone.getRunningBalance();
		double runningBalanceDouble = Double.valueOf(arrangement.getAvailableBalance().getAmount().toString()).doubleValue();
		double currencyAmountDouble = Double.valueOf(amount);

		double result = runningBalanceDouble + currencyAmountDouble;
		runningBalance.setAmount(new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP));
		runningBalance.setCurrency(arrangement.getAvailableBalance().getCurrency());
		transaction.setRunningBalance(runningBalance);	
		transaction.setType(TransactionType.from(type));

		return transaction;
	}
	
	
	
	public ProductArrangement createCurrentAccount(ArrangementWrapper wrapper)
	{
		ArrayList <String> messages = new ArrayList<String>();
		messages.add("message1");
		messages.add("message2");
		messages.add("message3");
		
	/*<------------------CURRENT ACCOUNT TEMPLATE ---------------------->*/	
		CurrentAccount currentAcc = new CurrentAccount();
		currentAcc.setAcceptsTransfers(wrapper.isAcceptsTransfers());
		currentAcc.setAccountName(wrapper.getAccountName());
		currentAcc.setAccountNumber(wrapper.getAccountNumber());
		currentAcc.setSortCode(wrapper.getSortCode());
		currentAcc.setAvailableBalance(wrapper.getAvailableBalance());
		currentAcc.setBalance(wrapper.getBalance());
		currentAcc.setCreateBeneficiaryAvailable(wrapper.isCreateBeneficiaryAvailable());
		currentAcc.setDormant(miscHelper.isDormant(wrapper.getState()));
		currentAcc.setId(wrapper.getId());
		currentAcc.setMakePaymentAvailable(wrapper.isMakePaymentAvailable());
		currentAcc.setMakeTransferAvailable(wrapper.isMakeTransferAvailable());
		currentAcc.setMessages(wrapper.getMessages());
		currentAcc.setNonMigratedAccount(wrapper.isNonMigratedAccount());
		currentAcc.setOtherBrand(wrapper.isOtherBrand());
		currentAcc.setOtherBrandBankName(wrapper.getOtherBrandBankName());
		currentAcc.setOverdraftLimit(wrapper.getOverdraftLimit());
		currentAcc.setStartDate(wrapper.getStartDate());
		return currentAcc;		
	}	
		
	public CurrencyAmount createCurrencyAmount(String amount, String currency)
	{
		if(amount.equals("") || currency.equals(""))
		{
			return null;
		}
		CurrencyAmount currencyAmount = new CurrencyAmount();
		BigDecimal decimal = new BigDecimal(amount).setScale(2,BigDecimal.ROUND_HALF_UP);
		currencyAmount.setAmount(decimal);
		currencyAmount.setCurrency(CurrencyAmount.CURRENCY_CODE.valueOf(currency));
		return currencyAmount;
	}
	
}
