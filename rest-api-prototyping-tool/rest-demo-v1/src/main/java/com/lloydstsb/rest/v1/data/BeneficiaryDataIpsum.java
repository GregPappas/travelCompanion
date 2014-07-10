package com.lloydstsb.rest.v1.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

public class BeneficiaryDataIpsum 
{
	
	private ArrayList<String> beneficiaryWhiteList = new ArrayList<String>();
	
	private String[] arrangementAccountsArr = new String[8];
	private HashMap<String, ArrayList<Beneficiary>> beneficiaryMap = new HashMap<String, ArrayList<Beneficiary>>();
	 
	
	private void FillArrangementAccountsArr()
	{								 
		arrangementAccountsArr[0] = "20756882123456";
		arrangementAccountsArr[1] = "28004089123456";
		arrangementAccountsArr[2] = "28194341123456";
		arrangementAccountsArr[3] = "28290313123456";
		arrangementAccountsArr[4] = "28290375123456";
		arrangementAccountsArr[5] = "28290392123456";
		arrangementAccountsArr[6] = "28398287123456";
		arrangementAccountsArr[7] = "28398412123456";
	}
	public String getNewBeneficiaryID() 
	{		
		return PersistenceContainer.getInstance().getNewBeneficiaryID();
	}

	public void addNewBeneficiaryList(ArrayList<Beneficiary> beneficiaryList, String arrangementId)
	{
		PersistenceContainer.getInstance().getBeneficiaries().put(arrangementId, beneficiaryList);
	}
	
	
	
	public ArrayList<Beneficiary> getBeneficiaryList(String arrangementId)
	{
		if(!getBeneficiaryMap().containsKey(arrangementId))
		{
			HashMap<String, ArrayList<Beneficiary>> beneficiaryMap = getBeneficiaryMap();
			beneficiaryMap.put(arrangementId, new ArrayList<Beneficiary>());
		}
			return PersistenceContainer.getInstance().getBeneficiaries().get(arrangementId);
	}
	
	public HashMap<String, ArrayList<Beneficiary>> getBeneficiaryMap()
	{
		return PersistenceContainer.getInstance().getBeneficiaries();
	}
	
	
	public Beneficiary[] generateBeneficiaries()
	{
		Beneficiary[] beneficiaries = new Beneficiary[10];

		beneficiaries[0] = new Beneficiary();
		beneficiaries[0].setAccountNumber("11111111");
		beneficiaries[0].setSortCode("111111");
		beneficiaries[0].setEnabled(true);
		beneficiaries[0].setReferenceAllowed(true);
		beneficiaries[0].setReference("Vodafone");
		beneficiaries[0].setName("Vodafone Pay Monthly");
		beneficiaries[0].setId(beneficiaries[0].getAccountNumber()+beneficiaries[0].getSortCode());
		
		
		beneficiaries[1] = new Beneficiary();
		beneficiaries[1].setAccountNumber("22222222");
		beneficiaries[1].setSortCode("222222");
		beneficiaries[1].setEnabled(true);
		beneficiaries[1].setReferenceAllowed(false);
		beneficiaries[1].setName("Mrs Betty White");
		beneficiaries[1].setId(beneficiaries[1].getAccountNumber()+beneficiaries[1].getSortCode());
		
		
		beneficiaries[2] = new Beneficiary();
		beneficiaries[2].setAccountNumber("33333333");
		beneficiaries[2].setSortCode("333333");
		beneficiaries[2].setEnabled(true);
		beneficiaries[2].setReferenceAllowed(true);
		beneficiaries[2].setReference("EDF");
		beneficiaries[2].setName("EDF Energy Bill");
		beneficiaries[2].setId(beneficiaries[2].getAccountNumber()+beneficiaries[2].getSortCode());
		
		beneficiaries[3] = new Beneficiary();
		beneficiaries[3].setAccountNumber("44444444");
		beneficiaries[3].setSortCode("444444");
		beneficiaries[3].setEnabled(true);
		beneficiaries[3].setReferenceAllowed(false);
		beneficiaries[3].setName("Enrique Iglesias");
		beneficiaries[3].setId(beneficiaries[3].getAccountNumber()+beneficiaries[3].getSortCode());
		
		beneficiaries[4] = new Beneficiary();
		beneficiaries[4].setAccountNumber("55555555");
		beneficiaries[4].setSortCode("555555");
		beneficiaries[4].setEnabled(true);
		beneficiaries[4].setReferenceAllowed(true);
		beneficiaries[4].setReference("Sky");
		beneficiaries[4].setName("Sky Broadband");
		beneficiaries[4].setId(beneficiaries[4].getAccountNumber()+beneficiaries[4].getSortCode());
		
		beneficiaries[5] = new Beneficiary();
		beneficiaries[5].setAccountNumber("66666666");
		beneficiaries[5].setSortCode("666666");
		beneficiaries[5].setEnabled(true);
		beneficiaries[5].setReferenceAllowed(false);
		beneficiaries[5].setName("Andrei");
		beneficiaries[5].setId(beneficiaries[5].getAccountNumber()+beneficiaries[5].getSortCode());
		
		beneficiaries[6] = new Beneficiary();
		beneficiaries[6].setAccountNumber("77777777");
		beneficiaries[6].setSortCode("777777");
		beneficiaries[6].setEnabled(true);
		beneficiaries[6].setReferenceAllowed(true);
		beneficiaries[6].setReference("TvLicense");
		beneficiaries[6].setName("Television Licensing Company");
		beneficiaries[6].setId(beneficiaries[6].getAccountNumber()+beneficiaries[6].getSortCode());
		
		beneficiaries[7] = new Beneficiary();
		beneficiaries[7].setAccountNumber("88888888");
		beneficiaries[7].setSortCode("888888");
		beneficiaries[7].setEnabled(true);
		beneficiaries[7].setReferenceAllowed(false);
		beneficiaries[7].setName("Julio");
		beneficiaries[7].setId(beneficiaries[7].getAccountNumber()+beneficiaries[7].getSortCode());
		
		beneficiaries[8] = new Beneficiary();
		beneficiaries[8].setAccountNumber("99999999");
		beneficiaries[8].setSortCode("999999");
		beneficiaries[8].setEnabled(true);
		beneficiaries[8].setReferenceAllowed(true);
		beneficiaries[8].setReference("Odeon");
		beneficiaries[8].setName("Odeon Cinema");
		beneficiaries[8].setId(beneficiaries[8].getAccountNumber()+beneficiaries[8].getSortCode());
		
		beneficiaries[9] = new Beneficiary();
		beneficiaries[9].setAccountNumber("10101010");
		beneficiaries[9].setSortCode("101010");
		beneficiaries[9].setEnabled(true);
		beneficiaries[9].setReferenceAllowed(false);
		beneficiaries[9].setName("Nosmo King");
		beneficiaries[9].setId(beneficiaries[9].getAccountNumber()+beneficiaries[9].getSortCode());
		
		return beneficiaries;
	}
	
	public void generateHashMapOfBeneficiaries()
	{
		FillArrangementAccountsArr();
		
		Beneficiary[] beneficiaries = generateBeneficiaries();
		int count=0;
		for(String arrangementId: arrangementAccountsArr)
		{	
			if(count<2)
			{
				ArrayList<Beneficiary> benList = new ArrayList<Beneficiary>();
				benList.add(beneficiaries[count]);
				benList.add(beneficiaries[count+1]);
				benList.add(beneficiaries[count+2]);
				count+=3;
				beneficiaryMap.put(arrangementId,benList);
			}	
			else
			{
				ArrayList<Beneficiary> benList = new ArrayList<Beneficiary>();
				benList.add(beneficiaries[count]);
				count++;
				beneficiaryMap.put(arrangementId,benList);
				
			}	
		}
		
		PersistenceContainer.getInstance().setBeneficiary(beneficiaryMap);
	}
	
	public ArrayList<Beneficiary> getAllBeneficiariesBelongingToUser(String userId)
	{	
		ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
		ArrayList<ArrangementWrapper> wrapperList = arrangementData.getArrangementWrappers(userId);
		ArrayList<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();
		
		for(ArrangementWrapper wrapper: wrapperList)
		{
			if(!wrapper.getState().equals(ArrangementStateType.DORMANT) && !wrapper.getState().equals(ArrangementStateType.DISABLED))
			beneficiaryList.addAll(getBeneficiaryList(wrapper.getId()));
		}
		return beneficiaryList;
	}
	
	
	public ArrayList<Beneficiary> getBeneficiariesListFromUser(ArrayList<ArrangementWrapper> wrappers,String sortCode, String accountNumber) 
	{
		ArrayList<Beneficiary> sortedList = new ArrayList<Beneficiary>();
		String arrangementId = null;
		for(ArrangementWrapper wrapper : wrappers)
		{
			arrangementId=wrapper.getId();
			ArrayList<Beneficiary> beneficiaries = getBeneficiaryMap().get(arrangementId);
			sortedList.addAll(beneficiaries);
		}
			return sortedList;
	}
	
	public ArrayList<Beneficiary> addBeneficiariesToListFromAccountAndSortCodeSearch(String userId,String sortCode, String accountNumber)
	{	
		ArrayList<Beneficiary> sortedList = new ArrayList<Beneficiary>();
		for (Beneficiary beneficiary : getAllBeneficiariesBelongingToUser(userId))
		{
			if (beneficiary.getAccountNumber().equals(accountNumber) && beneficiary.getSortCode().equals(sortCode))
			{
				sortedList.add(beneficiary);
			}
		}
		return sortedList;
	}
	
	public ArrayList<Beneficiary> addBeneficiariesToListFromNameSearch(String userId,String name)
	{			
		ArrayList<Beneficiary> sortedList = new ArrayList<Beneficiary>();
		for (Beneficiary beneficiary : getAllBeneficiariesBelongingToUser(userId))
		{
			String beneficiaryName= beneficiary.getName().toUpperCase();
			String queryName = name.toUpperCase();
			if (beneficiaryName.contains(queryName))
			{
				sortedList.add(beneficiary);
			}
		}
		return sortedList;
	}
	public ArrayList<String> returnBeneficiaryWhiteList() 
	{
			beneficiaryWhiteList.add("22222222222222");
			beneficiaryWhiteList.add("33333333333333");
			beneficiaryWhiteList.add("44444444444444");
			beneficiaryWhiteList.add("55555555555555");
			beneficiaryWhiteList.add("66666666666666");
			
		return beneficiaryWhiteList;
	}
	
	public ArrayList<BeneficiaryObject> convertFromBeneficiaryListToSearchBeneficiaryList(ArrayList<Beneficiary> beneficiaries)
	{
		
		ArrayList<BeneficiaryObject> sortedList = new ArrayList<BeneficiaryObject>();
	
		for (Beneficiary beneficiary : beneficiaries)
		{
		
			BeneficiaryObject benTemp = new BeneficiaryObject(beneficiary);
			sortedList.add(benTemp);
		
		}

		return sortedList;

	}
	
}
