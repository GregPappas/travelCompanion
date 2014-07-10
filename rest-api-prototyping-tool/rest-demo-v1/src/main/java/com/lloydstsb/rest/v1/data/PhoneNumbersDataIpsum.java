package com.lloydstsb.rest.v1.data;

import java.util.HashMap;

import com.lloydstsb.rest.v1.valueobjects.PhoneNumber;

public class PhoneNumbersDataIpsum {
	
	private HashMap <String, PhoneNumber> phoneNumbers;
	
	public PhoneNumber getNumber(String key)
	{
		return PersistenceContainer.getInstance().getPhoneNumbers().get(key);
	}
	
	public HashMap<String, PhoneNumber> getPhoneNumbersMap()
	{
		return PersistenceContainer.getInstance().getPhoneNumbers();
	}
	
	
	public void instantiatePhoneNumbers()
	{
		phoneNumbers = new HashMap<String, PhoneNumber>();
		PhoneNumber phoneNumber1 = new PhoneNumber();
		phoneNumber1.setType("LandLine");
		phoneNumber1.setExtension("0044");
		phoneNumber1.setPhoneNumber("02081234567");
		phoneNumber1.setValid(true);
		PhoneNumber phoneNumber2 = new PhoneNumber();
		phoneNumber2.setType("LandLine");
		phoneNumber2.setExtension("0041");
		phoneNumber2.setPhoneNumber("02084434533");
		phoneNumber2.setValid(true);
		PhoneNumber phoneNumber3 = new PhoneNumber();
		phoneNumber3.setType("LandLine");
		phoneNumber3.setExtension("0044");
		phoneNumber3.setPhoneNumber("08451234567");
		phoneNumber3.setValid(true);
		PhoneNumber phoneNumber4 = new PhoneNumber();
		phoneNumber4.setType("Mobile");
		phoneNumber4.setExtension("0044");
		phoneNumber4.setPhoneNumber("07894442221");
		phoneNumber4.setValid(true);
		PhoneNumber phoneNumber5 = new PhoneNumber();
		phoneNumber5.setType("Mobile");
		phoneNumber5.setExtension("0034");
		phoneNumber5.setPhoneNumber("66426065112");
		phoneNumber5.setValid(true);
		PhoneNumber phoneNumber6 = new PhoneNumber();
		phoneNumber6.setType("Mobile");
		phoneNumber6.setExtension("0041");
		phoneNumber6.setPhoneNumber("07875641234");
		phoneNumber6.setValid(true);
		PhoneNumber phoneNumber7 = new PhoneNumber();
		phoneNumber7.setType("Mobile");
		phoneNumber7.setExtension("0044");
		phoneNumber7.setPhoneNumber("07864564321");
		phoneNumber7.setValid(true);	
		PhoneNumber phoneNumber8 = new PhoneNumber();
		phoneNumber8.setType("Mobile");
		phoneNumber8.setExtension("0042");
		phoneNumber8.setPhoneNumber("07762233114");
		phoneNumber8.setValid(true);	
		PhoneNumber phoneNumber9 = new PhoneNumber();
		phoneNumber9.setType("Mobile");
		phoneNumber9.setExtension("0044");
		phoneNumber9.setPhoneNumber("04653314145");
		phoneNumber9.setValid(true);	
		
		phoneNumbers.put(phoneNumber1.getPhoneNumber(),phoneNumber1);
		phoneNumbers.put(phoneNumber2.getPhoneNumber(),phoneNumber2);
		phoneNumbers.put(phoneNumber3.getPhoneNumber(),phoneNumber3);
		phoneNumbers.put(phoneNumber4.getPhoneNumber(),phoneNumber4);
		phoneNumbers.put(phoneNumber5.getPhoneNumber(),phoneNumber5);
		phoneNumbers.put(phoneNumber6.getPhoneNumber(),phoneNumber6);
		phoneNumbers.put(phoneNumber7.getPhoneNumber(),phoneNumber7);
		phoneNumbers.put(phoneNumber8.getPhoneNumber(),phoneNumber8);
		phoneNumbers.put(phoneNumber9.getPhoneNumber(),phoneNumber9);
		
		PersistenceContainer.getInstance().setPhoneNumbers(phoneNumbers);
	}

}
