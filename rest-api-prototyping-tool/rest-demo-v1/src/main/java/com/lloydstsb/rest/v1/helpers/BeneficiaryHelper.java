package com.lloydstsb.rest.v1.helpers;

public class BeneficiaryHelper {

	public boolean validateAccountNumberAndSortCodeNull(String accountNumber, String sortCode) 
	{
		if(accountNumber == null&& sortCode ==null)
		{
			return true;
		}
		return false;
	}
	
	
	public boolean checkAccountNumberValid(String accountNumber)
	{
		String regex="^[0-9]{8}$";
		if(accountNumber == null)
		{
			return false;
		}
		return accountNumber.matches(regex);
		
	}
	
	public boolean checkSortCodeValid(String sortCode)
	{
		String regex="^[0-9]{6}$";
		if(sortCode == null)
		{
			return false;
		}
		return sortCode.matches(regex);
		
	}
	
	
}
