package com.lloydstsb.rest.v1.helpers;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementStateType;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

public class CheckHelper 
{
	private BeneficiaryDataIpsum beneficiaryData = new BeneficiaryDataIpsum();
	private ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
	
	public int checkSize(int numberToCheck)
	{
		if(numberToCheck>0 && numberToCheck <= 50)
		{
			return numberToCheck;
		}
		return 1;
	}
	
	public int checkPage(int numberToCheck)
	{
		if (numberToCheck >= 0)
		{
			return numberToCheck;
		}
		return 0;
	}
	
	
	public boolean checkUserIdIsNull(String userId)
	{
		if(userId==null)
		{
			return true;	
		}	
		return false;
	}
	
	public boolean checkArrangementIdBelongsToUser(String userId, String arrangementId) 
	{
		if(userId == null || arrangementId == null)
		{
			return false;
		}
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrayList<ArrangementWrapper> arrangementWrappers = arrangementData.getArrangementWrappers(userId);
		for(ArrangementWrapper wrapper : arrangementWrappers)
		{
			if(wrapper.getId().equals(arrangementId))
			{
				return true;
			}	
		}
		return false;
	}
	
	public boolean checkTransactionListExists(String arrangementId)
	{
		TransactionsDataIpsum reader = new TransactionsDataIpsum();
		if(!reader.getTransactionMap().containsKey(arrangementId))
		{
			return false;
		}
		return true;
	}

	public boolean checkIfBeneficiaryIsNull(SessionHelper session, HttpServletRequest request, String transactionId)
	{
		session.setRequest(request);
		try
		{
			if(session.returnBeneficiarySessionVariable(transactionId)!= null)
			{
				return true;
			}

			
		}catch(NullPointerException npe)
		{
			return false;	
		}
		
				
		return false;
	}

	
	public boolean checkBeneficiaryExists(String arrangementId,String beneficiaryId)
	{
		ArrayList<Beneficiary> beneficiaryArrangementList = beneficiaryData.getBeneficiaryList(arrangementId);
		
		for(Beneficiary benObj : beneficiaryArrangementList)
		{
			if(benObj.getId().equals(beneficiaryId))
			{
				return true;
			} 	
		}
		return false;
	}

	
	
	public boolean checkParamsForEmpty(String[] paramArray)
	{		
		for(String param: paramArray)
		{
			if(param == null || param.equals(""))
			{
				return false;
			}
		}
		return true;
	}

	public boolean checkDatesFromAndTo(String dateFrom,String dateTo,ArrangementWrapper wrapper)
	{	
		if (dateFrom == null && dateTo == null)
		{
			return true;
		}
		if(!"".equals(dateFrom) && !"".equals(dateTo))
		{
			return validateDatesFromAndTo(dateFrom,dateTo,wrapper);
		}
		if(!"".equals(dateFrom) || !"".equals(dateTo))
		{
			return false;
		}
	
		return true;
	}
	
	public boolean validateDatesFromAndTo(String dateFrom,String dateTo,ArrangementWrapper wrapper)
	{
		Date dFrom;
		Date dTo;
		Date dStartDate;
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String StartDate=sdf.format(wrapper.getStartDate());

		try {
			dFrom = sdf.parse(dateFrom);
			dTo = sdf.parse(dateTo);
			dStartDate=sdf.parse(StartDate);
		} catch (Exception e) {
			return false;
		}

		int statementValiditySpan = wrapper.getStatementValiditySpan();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(Calendar.getInstance().getTime());
		cal.add(Calendar.MONTH, -statementValiditySpan);
		String currentDate=sdf.format(Calendar.getInstance().getTime());
		
		if (		(dFrom.before(dTo) || dateTo.equals(dateFrom))
				&& (dTo.before(Calendar.getInstance().getTime()) || currentDate.equals(dateTo)) 
				&& (dFrom.after(dStartDate)|| StartDate.equals(dateFrom))
			)
			{
				return true;
			}				 
				
		return false;
	}
	
	public boolean checkAmountAboveMinimum(double amountDouble) 
	{
		return (amountDouble>0);
	}

		public boolean checkAmountLessThanAvailableBalance(double amountDouble, double availableBalance) {
		return (amountDouble < availableBalance);
	}
		
	public boolean checkArrangementIsNotDisabled(String arrangementId)
	{
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
		if(wrapper.getState() == ArrangementStateType.DISABLED)
		{
			return false;
		}
		return true;
	}

	public boolean checkArrangementDormant(String arrangementId) {
		if(arrangementData.getArrangementWrapper(arrangementId).getState().toString().equals("DORMANT"))
		{
			return true;
		}
		return false;
	}

	public boolean checkArrangementSuspicious(String arrangementId) {
		if(arrangementData.getArrangementWrapper(arrangementId).getState().toString().equals("SUSPICIOUS"))
		{
			return true;
		}
		return false;
	}

	public boolean checkExpiryDate(String dateFrom,String dateTo,ArrangementWrapper wrapper) {
		
		if (dateFrom != null && dateTo != null)
		{
			int statementValiditySpan = wrapper.getStatementValiditySpan();
			Calendar cal = Calendar.getInstance();
			cal.setTime(Calendar.getInstance().getTime());
			cal.add(Calendar.MONTH, -statementValiditySpan);
			Date expiryDate=cal.getTime();
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dFrom;
			
			try {
				dFrom = sdf.parse(dateFrom);
			} catch (Exception e) {
				return false;
			}
			
			if (dFrom.after(expiryDate) || sdf.format(expiryDate).equals(dateFrom))
			{
				return false;
			}
			return true;
		}

		return false;
	
	}
	
	public boolean checkAccountIsWhiteListed(String accountNumber, String sortCode)
	{
		if(beneficiaryData.returnBeneficiaryWhiteList().contains(accountNumber+sortCode))
		{
			return true;
		}
		return false;
	}

	public boolean checkExceedsReceivingLimit(BigDecimal amount) 
	{
		TransactionsDataIpsum transactionData = new TransactionsDataIpsum();
		BigDecimal transactionLimit = transactionData.getTransactionLimitAmount();
		if(transactionLimit.doubleValue() <= amount.doubleValue())
		{
			return true;
		}		
		return false;
	}
	
	
}
