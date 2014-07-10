package com.lloydstsb.rest.v1.helpers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount.CURRENCY_CODE;

public class ArrangementHelper 
{
	private BeneficiaryDataIpsum beneficiaryData = new BeneficiaryDataIpsum();
	private ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
	private ObjectGenerator objectGenerator = new ObjectGenerator();
	
	public void updateBalance(String arrangementId, CurrencyAmount runningBalance)
	{
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		double runningBalanceDouble = Double.parseDouble(runningBalance.getAmount().toString());  
		double overdraftDouble = 0;
		
		if(!(wrapper.getLimit()== null && wrapper.getOverdraftLimit()== null))
		{
			if(wrapper.getOverdraftLimit()== null)
			{
				overdraftDouble = Double.parseDouble(wrapper.getLimit().getAmount().toString());
			}	
			else if(wrapper.getLimit()== null)
			{
				overdraftDouble = Double.parseDouble(wrapper.getOverdraftLimit().getAmount().toString());
			}
			
			CurrencyAmount balance = wrapper.getBalance();
			BigDecimal decimal1 = new BigDecimal(runningBalanceDouble - overdraftDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
			balance.setAmount(decimal1);
			
			BigDecimal decimal2 = new BigDecimal(runningBalanceDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
			CurrencyAmount newAmount = new CurrencyAmount();
			newAmount.setAmount(decimal2);
			newAmount.setCurrency(CURRENCY_CODE.GBP);
			wrapper.setAvailableBalance(newAmount);

		}

	}



	public CurrencyAmount sumOfBalances(CurrencyAmount originalBalance, CurrencyAmount amountToAdd)
	{
		CurrencyAmount currentAmount = originalBalance;
		CurrencyAmount newAmount = new CurrencyAmount();
		double currentAmmountDouble = currentAmount.getAmount().doubleValue();
		double amountToAddDouble = amountToAdd.getAmount().doubleValue();
		double newAmountDouble = currentAmmountDouble + amountToAddDouble;
		
		BigDecimal newAmountDoubleRounded =new BigDecimal(newAmountDouble).setScale(2, BigDecimal.ROUND_HALF_UP);
		newAmount.setAmount(newAmountDoubleRounded);
		newAmount.setCurrency(currentAmount.getCurrency());
		
		return newAmount;
		
	}	
		
	public void makePaymentToLocalCustomer(String arrangementId, String beneficiaryId, CurrencyAmount amount) 
	{
		ArrayList<Beneficiary> beneficiariesList = beneficiaryData.getBeneficiaryList(arrangementId);
		Beneficiary beneficiary = new Beneficiary();
		
		for(Beneficiary beneficiaryobj : beneficiariesList)
		{
			if(beneficiaryobj.getId().equals(beneficiaryId))
			{
				beneficiary = beneficiaryobj;
				break;
			}
		}
		
		String idToFind = beneficiary.getAccountNumber()+ beneficiary.getSortCode();
		
		for(ArrangementWrapper arrangementWrapper : arrangementData.getAllArrangementWrappers())
		{	
			if(arrangementWrapper.getId().equals(idToFind))
			{
				CurrencyAmount newAmount = sumOfBalances(arrangementWrapper.getAvailableBalance(), amount);
				updateBalance(arrangementWrapper.getId(), newAmount);
				TransactionsDataIpsum transactionData = new TransactionsDataIpsum();
				String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
				Transaction deposit = objectGenerator.createNewTransaction(amount.getAmount().toString(), transactionData.getNewTransactionId(), date, amount.getCurrency().toString(), arrangementId, "DEP", "money debited in", arrangementWrapper);
				transactionData.getTransactionList(arrangementWrapper.getId()).add(deposit);
				break;
			}
		}

	
	}
		
	public Beneficiary getBeneficiary(SessionHelper sessionHelper, HttpServletRequest request,String transactionId)
	{
		sessionHelper.setRequest(request);
		Beneficiary beneficiary = sessionHelper.returnBeneficiarySessionVariable(transactionId);
		return beneficiary;
	}
	
}
