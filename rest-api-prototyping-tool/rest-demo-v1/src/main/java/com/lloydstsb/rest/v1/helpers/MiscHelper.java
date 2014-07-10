package com.lloydstsb.rest.v1.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lloydstsb.rest.v1.data.ArrangementStateType;
import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount.CURRENCY_CODE;
import com.lloydstsb.rest.v1.valueobjects.TransactionType;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ArrangementType;

public class MiscHelper 
{
	public boolean authenticate(String name, String pass )
	{	
		if(!name.equals(pass))
		{
			return false;
		}
		CustomersDataIpsum customers = new CustomersDataIpsum();
		boolean check = customers.getCustomers().containsKey(name);
		return check;
	}
	
	public int sizeToReturn(int page, int requestSize, int listSize)
	{
		if(page == 0)
		{
			if(listSize > requestSize)
			{
				return requestSize;
			}
			
			else
			{
				return listSize;
			}
			
		}

		if(page > 0)
		{
			int count =0;
			while(listSize > requestSize)
			{
				listSize -= requestSize;
				count++;
			}
			
			if(count > page)
			{
				return requestSize;
			}
			
			else
			{
				return listSize;
			}
			
		}

		return 0;
	}
	
	public boolean isDormant(ArrangementStateType state)
	{
		if(state == ArrangementStateType.DORMANT)
		{
			return true;
		}
		return false;
	}
	
	public void validateNextLineArrangementDataIpsum(String[] nextLine)
	{
		validateIsNumeric(nextLine[0]);
		validateArrangementId(nextLine[1]);
		validateArrangementType(nextLine[2]);
		validateAccountNumber(nextLine[3],nextLine[2]);	
		validateSortCode(nextLine[4], nextLine[2]);
		validateCreditCardNumber(nextLine[5],nextLine[2]);
		validateDate(nextLine[7]);		
		validateMonthlyPaymentAmount(nextLine[8],nextLine[9],nextLine[2]);
		validateOverDraftLimitAmount(nextLine[10],nextLine[11],nextLine[2]);
		validateCreditCardLimitAmount(nextLine[12],nextLine[13],nextLine[2]);
		validateInputBoolean(nextLine[14]);
		validateInputBoolean(nextLine[15]);
		validateStatus(nextLine[16]);
		validateInputBoolean(nextLine[17]);
		validateInputBoolean(nextLine[18]);
		validateInputBoolean(nextLine[19]);
		validateInputBoolean(nextLine[20]);
		validateOtherBrandBankName(nextLine[20],nextLine[21]);
		validateReferenceNumber(nextLine[2],nextLine[22]);	
		validateStatementValiditySpan(nextLine[23]);
	}
	
	public void validateNextLineTransactionDataIpsum(String[] nextLine) 
	{
		validateArrangementId(nextLine[0]);		
		validateDate(nextLine[1]);
		validateCurrencyAmount(nextLine[2],nextLine[3]);
		validateCurrencyAmount(nextLine[4],nextLine[3]);
	}
	

	
	public void validateStatementValiditySpan(String statementValiditySpan) 
	{
		String regex="^[0-9]{1,2}$";
		if (!statementValiditySpan.matches(regex))
			throw new IllegalArgumentException(statementValiditySpan + " is not a valid statement validity span");
		
	}
	
	public void validateReferenceNumber(String type, String referenceNumber) 
	{
		if (type.equals(ArrangementType.MORTGAGE_ACCOUNT.toString()))
		{
			String regex="^[0-9]+$";
			if (!referenceNumber.matches(regex))
				throw new IllegalArgumentException(referenceNumber + " is not a valid reference number");

		}
		else
		{
			if(!referenceNumber.equals(""))
				throw new IllegalArgumentException(referenceNumber + " is not a valid reference number");
		}
	}

	public void validateOtherBrandBankName(String isOtherBrand, String brandName) 
	{
		boolean isOtherBrandBoolean = Boolean.parseBoolean(isOtherBrand);
		if (isOtherBrandBoolean)
		{
			if(brandName.equals(""))
			throw new IllegalArgumentException("Brand name cannot be empty if arrangement is other brand name");
		}
		else
			if(!brandName.equals(""))
			throw new IllegalArgumentException(brandName + " is not a valid brand name");
	}

	
	public void validateIsNumeric(String userId)
	{
		String regex="^[0-9]+$";
		if (!userId.matches(regex))
			throw new IllegalArgumentException(userId + " is not a numeric value");
	}
	
	public void validateArrangementId(String arrangementId)
	{
		String regex="^[0-9]{14}$";
		if (!arrangementId.matches(regex))
			throw new IllegalArgumentException(arrangementId + " is not a valid arrangementId");
	}
	
	public void validateArrangementType(String type)
	{	
		boolean check=false;
		for(ArrangementType choice: ArrangementType.values())
		{
	      if (choice.name().equals(type)) 
	    	  check=true;
		}
		if (!check)
			throw new IllegalArgumentException(type + " is not a valid type");
	}

	
	public void validateTransactionType(String type)
	{	
		if(TransactionType.from(type) == null)
			throw new IllegalArgumentException(type + " is not a valid type");
			
	}

	public void validateAccountNumber(String accNumber,String type) {
		if(type.equals(ArrangementType.SAVINGS_ACCOUNT.toString()) || type.equals(ArrangementType.CURRENT_ACCOUNT.toString()))
		{	
			String regex="^[0-9]{8}$";
			if (!accNumber.matches(regex))
				throw new IllegalArgumentException(type + " is not a valid account number");			
		}
		else
		{			
			if(!accNumber.equals(""))
				throw new IllegalArgumentException(type + " is not a valid account number");			

		}
	}
	public void validateSortCode(String sortCode, String type) {
		if(type.equals(ArrangementType.SAVINGS_ACCOUNT.toString()) || type.equals(ArrangementType.CURRENT_ACCOUNT.toString()))
		{	
			String regex="^[0-9]{6}$";
			if (!sortCode.matches(regex))
			throw new IllegalArgumentException(sortCode + " is not a valid sort code");
		}
		else
		{
			if(!sortCode.equals(""))
				throw new IllegalArgumentException("This type of arrangement should not have a sort code");
		}	
	}

	public void validateCreditCardNumber(String creditCardNumber,String type) {
	
		if (type.equals(ArrangementType.CREDITCARD_ACCOUNT.toString()))
		{
			String regex="^[0-9]{16}$";
			if (!creditCardNumber.matches(regex))
				throw new IllegalArgumentException(creditCardNumber + " is not a valid creditcard number");
		}
		else
		{
			if (!creditCardNumber.equals(""))
				throw new IllegalArgumentException("This type of arrangement should not have a creditcard number");
		}
	}

	public void validateDate(String dateString) {
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = sdf.parse(dateString);
			String formatedDate = sdf.format(date);
			if (!dateString.contains(formatedDate))
				throw new IllegalArgumentException(dateString + " is not a valid arrangement date");
			if (!date.before(Calendar.getInstance().getTime()))
				throw new IllegalArgumentException(dateString + " is not a valid arrangement date");
			
		} catch (ParseException e) {
			throw new IllegalArgumentException(dateString + " is not a valid arrangement date");
		}			
	}

	public void validateCurrencyAmount(String amount,String currency) {
		if(amount.equals(""))
			throw new IllegalArgumentException(amount +" is not a valid amount");
		validateIsDoubleNumber(amount);
		boolean check=false;
		for(CURRENCY_CODE choice: CURRENCY_CODE.values())
		{
	      if (choice.name().equals(currency))
	      {
	    	  check=true;
	      }
		}
		if (!check)	
			throw new IllegalArgumentException(currency +" is not a valid currency");
	}
	
	public void validateMonthlyPaymentAmount(String amount,String currency, String type) {
		if (type.equals(ArrangementType.MORTGAGE_ACCOUNT.toString()))
		{
			validateCurrencyAmount(amount, currency);
		}
		else if (!(amount.equals("")&& currency.equals("")))
			throw new IllegalArgumentException("This type of arrangement should not have a Monthly Payment");
			
	}

	public void validateOverDraftLimitAmount(String amount,String currency, String type) {
		if (type.equals(ArrangementType.CURRENT_ACCOUNT.toString()))
		{
			validateCurrencyAmount(amount, currency);
		}
		else if (!(amount.equals("")&& currency.equals("")))
			throw new IllegalArgumentException("This type of arrangement should not have a Monthly Payment");
	}
	
	public void validateCreditCardLimitAmount(String amount,String currency, String type) {
		if (type.equals(ArrangementType.CREDITCARD_ACCOUNT.toString()))
		{
			validateCurrencyAmount(amount, currency);
		}
		else if (!(amount.equals("")&& currency.equals("")))
			throw new IllegalArgumentException("This type of arrangement should not have a Monthly Payment");
		
	}
	public void validateInputBoolean(String input)
	{
		if (!(input.equalsIgnoreCase("true")||input.equalsIgnoreCase("false")))
			throw new IllegalArgumentException(input +" is not a valid boolean");

	}

	public void validateStatus(String status) 
	{
		boolean check=false;
		for(ArrangementStateType choice: ArrangementStateType.values())
		{
			if(choice.name().equals(status))
				check=true;
		}
		if (!check)
			throw new IllegalArgumentException(status + " is not a valid status");
	}


	public String generateType(String amount) 
	{
		if (amount.contains("-"))
			return "DEP";
		else
			return "DEB";
	}


	public void validateIsDoubleNumber(String userId) {
		String regex="[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
		if (!userId.matches(regex))
			throw new IllegalArgumentException(userId + " is not a numeric value");
		
	}
}
