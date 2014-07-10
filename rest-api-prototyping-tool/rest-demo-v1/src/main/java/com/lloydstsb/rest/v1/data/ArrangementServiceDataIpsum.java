package com.lloydstsb.rest.v1.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;

import com.lloydstsb.rest.v1.helpers.MiscHelper;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.ArrangementType;



public class ArrangementServiceDataIpsum
{

	private MiscHelper miscHelper = new MiscHelper();
	private ObjectGenerator objectGenerator = new ObjectGenerator();
	private HashMap<String, ArrayList<ArrangementWrapper>> wrapperMap = new HashMap<String, ArrayList<ArrangementWrapper>>();
	
	
	public void clearArrangementWrapperMap()
	{
		PersistenceContainer.getInstance().getArrangementWrapper().clear();
	}
	
	public ArrangementWrapper getArrangementWrapper(String arrangementId)
	{
		for (Entry<String, ArrayList<ArrangementWrapper>> wrapperList: PersistenceContainer.getInstance().getArrangementWrapper().entrySet())
		{
			for(ArrangementWrapper wrapper: wrapperList.getValue())
			{
				if(wrapper.getId().equals(arrangementId))
				{
					return wrapper;	
				}
			}
		}
		return null;
	}
	
	public ArrayList<ArrangementWrapper> getAllArrangementWrappers()
	{
		ArrayList<ArrangementWrapper> allArrangementWrappers = new ArrayList<ArrangementWrapper>();
		for (Entry<String, ArrayList<ArrangementWrapper>> wrapperList: PersistenceContainer.getInstance().getArrangementWrapper().entrySet())
		{
			for(ArrangementWrapper wrapper: wrapperList.getValue())
			{
				allArrangementWrappers.add(wrapper);

			}
		}
		return allArrangementWrappers;
	}
	
	public ArrayList<ArrangementWrapper> getArrangementWrappers(String userId)
	{
		return PersistenceContainer.getInstance().getArrangementWrapper().get(userId);
	}
	
	public Arrangement getArrangementFromArrangementWrapper(ArrangementWrapper wrapper)
	{
		Arrangement arrangement = null;
		arrangement = objectGenerator.createCurrentAccount(wrapper);
		return arrangement;
	}
	
	public ArrayList<Arrangement> getArrangementsFromArrangementWrapperList(String userId)
	{
		ArrayList<Arrangement> arrangementList = new ArrayList<Arrangement>();
		for(ArrangementWrapper wrapper : this.getArrangementWrappers(userId))
		{
			if(wrapper.getState()!= ArrangementStateType.DISABLED && wrapper.getState() != ArrangementStateType.DORMANT)
			{
				arrangementList.add(getArrangementFromArrangementWrapper(wrapper));	
			}
		}
		return arrangementList;
	}
	
	public void instantiateCustomersArrangements() 
	{			
				CSVReader reader;
				InputStream input = this.getClass().getClassLoader().getResourceAsStream("arrangements.csv");
				Charset cs = Charset.forName("UTF8");
				InputStreamReader isr = new InputStreamReader(input, cs);
				reader = new CSVReader(isr); 
				String[] nextLine;
				int lineCounter = 1;
				try {
					while ((nextLine = reader.readNext()) != null) {
						ArrangementWrapper wrapper;
						try{
						miscHelper.validateIsNumeric(nextLine[0]);
						wrapper = readLineFromReader(nextLine);
						}catch(IllegalArgumentException e)
						{
							reader.close();
							throw new IllegalStateException("Illegal argument found in arrangements.csv on line " + lineCounter,e);
						}
						if (wrapperMap.containsKey(nextLine[0]))//if contains the arrangement id we retrieve the list and add the new transaction
						{
							wrapperMap.get(nextLine[0]).add(wrapper);
						}
						else // we create the new list if the arrangementId doesn't exist 
						{
							ArrayList<ArrangementWrapper> wrappersList =new ArrayList<ArrangementWrapper>();
							wrappersList.add(wrapper);
							wrapperMap.put(nextLine[0], wrappersList);
						}
						lineCounter++;
					}
					reader.close();
				} catch (IOException e) 
				{
					throw new IllegalStateException("Unable to read file.");
				}
				
				PersistenceContainer.getInstance().setArrangementWrapper(wrapperMap);

	}
	
	public ArrangementWrapper readLineFromReader(String[] nextLine)
	{
		// nextLine[] is an array of values from the line
		ArrangementWrapper wrapper = new ArrangementWrapper();
		
		miscHelper.validateNextLineArrangementDataIpsum(nextLine);
		wrapper.setId(nextLine[1]);
		wrapper.setType(ArrangementType.valueOf(nextLine[2]));
		wrapper.setAccountNumber(nextLine[3]);
		wrapper.setSortCode(nextLine[4]);
		wrapper.setCardNumber(nextLine[5]);
		wrapper.setAccountName(nextLine[6]);
			Date date;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(nextLine[7]);
				wrapper.setStartDate(date);
			} catch (ParseException e) {
				throw new IllegalStateException();
			}			
		wrapper.setAvailableBalance(objectGenerator.createCurrencyAmount("365", "GBP"));
		wrapper.setBalance(objectGenerator.createCurrencyAmount("365", "GBP"));
		wrapper.setMonthlyPayment(objectGenerator.createCurrencyAmount(nextLine[8], nextLine[9]));
		wrapper.setOverdraftLimit(objectGenerator.createCurrencyAmount(nextLine[10], nextLine[11]));
		wrapper.setLimit(objectGenerator.createCurrencyAmount(nextLine[12], nextLine[13]));
		wrapper.setAcceptsTransfers(Boolean.parseBoolean(nextLine[14]));
		wrapper.setCreateBeneficiaryAvailable(Boolean.parseBoolean(nextLine[15]));
		wrapper.setState(ArrangementStateType.valueOf(nextLine[16]));
		wrapper.setMakePaymentAvailable(Boolean.parseBoolean(nextLine[17]));
		wrapper.setMakeTransferAvailable(Boolean.parseBoolean(nextLine[18]));
		wrapper.setNonMigratedAccount(Boolean.parseBoolean(nextLine[19]));
		wrapper.setOtherBrand(Boolean.parseBoolean(nextLine[20]));
		wrapper.setOtherBrandBankName(nextLine[21]);
		wrapper.setReferenceNumber(nextLine[22]);
		wrapper.setStatementValiditySpan(nextLine[23]);
		wrapper.setProhibitiveIndicator(Boolean.parseBoolean(nextLine[24]));
		
		return wrapper;
	}

}
