package com.lloydstsb.rest.v1.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.lloydstsb.rest.v1.valueobjects.Address;
import com.lloydstsb.rest.v1.valueobjects.Customer;
import com.lloydstsb.rest.v1.valueobjects.PhoneNumber;
public class CustomersDataIpsum 
{
	private HashMap <String, Customer> customers;
	
	public HashMap<String, Customer> getCustomers()
	{
		return PersistenceContainer.getInstance().getCustomers();
	}
	
	
	public void instantiateCustomers ()
	{
		customers = new HashMap<String, Customer>();
		PhoneNumbersDataIpsum numbersData = new PhoneNumbersDataIpsum();

		
		Customer customer1 = new Customer();
		customer1.setId("69877");
		customer1.setEmail("69877@lloydstsb.com");
		customer1.setFirstname("John");
		customer1.setLastname("Malkovichi");
		customer1.setOcisID("69877");
		ArrayList<PhoneNumber> phoneList1= new ArrayList<PhoneNumber>();
		phoneList1.add(numbersData.getNumber("02081234567"));
		phoneList1.add(numbersData.getNumber("07894442221"));
		customer1.setTelephoneNumbers(phoneList1);
		Address address1 = new Address();
		address1.setFirstLine("23 Long Rd");
		address1.setSecondLine("Lazy Town");
		address1.setPostCode("s13 3sm");
		customer1.setAddress(address1);
		
		Customer customer2 = new Customer();
		customer2.setId("69888");
		customer2.setEmail("69888@lloydstsb.com");
		customer2.setFirstname("Jane");
		customer2.setLastname("Malkovichi");
		customer2.setOcisID("69888");
		ArrayList<PhoneNumber> phoneList2= new ArrayList<PhoneNumber>();
		phoneList2.add(numbersData.getNumber("02081234567"));
		phoneList2.add(numbersData.getNumber("07875641234"));
		phoneList2.add(numbersData.getNumber("66426065112"));
		customer2.setTelephoneNumbers(phoneList2);
		customer2.setAddress(address1);

		Customer customer3 = new Customer();
		customer3.setId("79882");
		customer3.setEmail("79882@lloydstsb.com");
		customer3.setFirstname("Jhon");
		customer3.setLastname("Doe");
		customer3.setOcisID("79882");
		ArrayList<PhoneNumber> phoneList3= new ArrayList<PhoneNumber>();
		phoneList3.add(numbersData.getNumber("02084434533"));
		phoneList3.add(numbersData.getNumber("08451234567"));
		phoneList3.add(numbersData.getNumber("07875641234"));
		phoneList3.add(numbersData.getNumber("07864564321"));		
		customer3.setTelephoneNumbers(phoneList3);
		Address address2 = new Address();
		address2.setFirstLine("2 Short Ln");
		address2.setSecondLine("Beeish");
		address2.setPostCode("k10 1FM");
		customer3.setAddress(address2);
		
		Customer customer4 = new Customer();
		customer4.setId("80082");
		customer4.setEmail("80082@lloydstsb.com");
		customer4.setFirstname("Anne");
		customer4.setLastname("Doe");
		customer4.setOcisID("80082");
		ArrayList<PhoneNumber> phoneList4= new ArrayList<PhoneNumber>();
		phoneList4.add(numbersData.getNumber("02084434533"));
		phoneList4.add(numbersData.getNumber("08451234567"));
		phoneList4.add(numbersData.getNumber("07762233114"));
		phoneList4.add(numbersData.getNumber("04653314145"));
		customer4.setTelephoneNumbers(phoneList4);
		customer4.setAddress(address2);
		
		Customer customerS1 = new Customer();
		customer4.setId("69006");
		customer4.setEmail("69006@lloydstsb.com");
		customer4.setFirstname("Antonie");
		customer4.setLastname("Daimiel");
		customer4.setOcisID("69006");
		ArrayList<PhoneNumber> phoneListS1= new ArrayList<PhoneNumber>();
		phoneList4.add(numbersData.getNumber("02045477533"));
		phoneList4.add(numbersData.getNumber("08412345567"));
		phoneList4.add(numbersData.getNumber("07762254314"));
		phoneList4.add(numbersData.getNumber("04651234545"));
		customer4.setTelephoneNumbers(phoneListS1);
		Address addressS1 = new Address();
		address2.setFirstLine("34 Long Road");
		address2.setSecondLine("Brixton");
		address2.setPostCode("SW3 1YT");
		customer4.setAddress(addressS1);
		
		Customer customerS2 = new Customer();
		customer4.setId("69007");
		customer4.setEmail("69007@lloydstsb.com");
		customer4.setFirstname("Andrew");
		customer4.setLastname("Hills");
		customer4.setOcisID("69007");
		ArrayList<PhoneNumber> phoneListS2= new ArrayList<PhoneNumber>();
		phoneList4.add(numbersData.getNumber("02045409833"));
		phoneList4.add(numbersData.getNumber("08412345567"));
		phoneList4.add(numbersData.getNumber("07765836314"));
		phoneList4.add(numbersData.getNumber("04633934545"));
		customer4.setTelephoneNumbers(phoneListS2);
		Address addressS2 = new Address();
		address2.setFirstLine("124 Churchill Road");
		address2.setSecondLine("London Bridge");
		address2.setPostCode("S13 1AG");
		customer4.setAddress(addressS2);
		
		Customer customerS3 = new Customer();
		customer4.setId("69008");
		customer4.setEmail("69008@lloydstsb.com");
		customer4.setFirstname("Helena");
		customer4.setLastname("Astopenko");
		customer4.setOcisID("69009");
		ArrayList<PhoneNumber> phoneListS3= new ArrayList<PhoneNumber>();
		phoneList4.add(numbersData.getNumber("02041122333"));
		phoneList4.add(numbersData.getNumber("08414455567"));
		phoneList4.add(numbersData.getNumber("07765866774"));
		phoneList4.add(numbersData.getNumber("04633998765"));
		customer4.setTelephoneNumbers(phoneListS3);
		Address addressS3 = new Address();
		address2.setFirstLine("23 Maison Road");
		address2.setSecondLine("Finsbury Park");
		address2.setPostCode("SL6 6LJ");
		customer4.setAddress(addressS3);
		
		Customer customerS4 = new Customer();
		customer4.setId("69009");
		customer4.setEmail("69009@lloydstsb.com");
		customer4.setFirstname("David");
		customer4.setLastname("Whistle");
		customer4.setOcisID("69009");
		ArrayList<PhoneNumber> phoneListS4= new ArrayList<PhoneNumber>();
		phoneList4.add(numbersData.getNumber("02046900933"));
		phoneList4.add(numbersData.getNumber("08414690067"));
		phoneList4.add(numbersData.getNumber("07690096774"));
		phoneList4.add(numbersData.getNumber("04633996900"));
		customer4.setTelephoneNumbers(phoneListS4);
		Address addressS4 = new Address();
		address2.setFirstLine("12 Chapter Road");
		address2.setSecondLine("Seven Sisters");
		address2.setPostCode("EH5 7KI");
		customer4.setAddress(addressS4);
		
		
		Customer customerS5 = new Customer();
		customer4.setId("69010");
		customer4.setEmail("69010@lloydstsb.com");
		customer4.setFirstname("Lewis");
		customer4.setLastname("Pappas");
		customer4.setOcisID("69010");
		ArrayList<PhoneNumber> phoneListS5= new ArrayList<PhoneNumber>();
		phoneList4.add(numbersData.getNumber("02041690103"));
		phoneList4.add(numbersData.getNumber("08414455567"));
		phoneList4.add(numbersData.getNumber("07769010774"));
		phoneList4.add(numbersData.getNumber("06901078765"));
		customer4.setTelephoneNumbers(phoneListS5);
		Address addressS5 = new Address();
		address2.setFirstLine("45 Docklands Avenue");
		address2.setSecondLine("Plaistow");
		address2.setPostCode("S45 6YR");
		customer4.setAddress(addressS5);

		Customer customerg1 = new Customer();
		customerg1.setId("69000");
		customerg1.setEmail("69000@lloydstsb.com");
		customerg1.setFirstname("Fernando");
		customerg1.setLastname("Garcia");
		customerg1.setOcisID("69000");
		ArrayList<PhoneNumber> phoneListg1= new ArrayList<PhoneNumber>();
		phoneListg1.add(numbersData.getNumber("12345678910"));
		phoneListg1.add(numbersData.getNumber("01987653210"));
		phoneListg1.add(numbersData.getNumber("07742356331"));
		phoneListg1.add(numbersData.getNumber("04645633141"));
		customerg1.setTelephoneNumbers(phoneListg1);
		Address addressg1 = new Address();
		addressg1.setFirstLine("742 EverGreen Terrace");
		addressg1.setSecondLine("SpringField");
		addressg1.setPostCode("S1 MP5");
		customerg1.setAddress(addressg1);

		Customer customerg2 = new Customer();
		customerg2.setId("69001");
		customerg2.setEmail("69001@lloydstsb.com");
		customerg2.setFirstname("Allonso");
		customerg2.setLastname("Garcia");
		customerg2.setOcisID("69001");
		ArrayList<PhoneNumber> phoneListg2= new ArrayList<PhoneNumber>();
		phoneListg2.add(numbersData.getNumber("12311178910"));
		phoneListg2.add(numbersData.getNumber("01922253210"));
		phoneListg2.add(numbersData.getNumber("07733356331"));
		phoneListg2.add(numbersData.getNumber("04644433141"));
		customerg1.setTelephoneNumbers(phoneListg2);
		Address addressg2 = new Address();
		addressg2.setFirstLine("1 Buckingham Palace");
		addressg2.setSecondLine("London");
		addressg2.setPostCode("SW1A 1AA");
		customerg2.setAddress(addressg2);
		
		Customer customerg3 = new Customer();
		customerg3.setId("69002");
		customerg3.setEmail("69002@lloydstsb.com");
		customerg3.setFirstname("Jerry");
		customerg3.setLastname("Garcia");
		customerg3.setOcisID("69002");
		ArrayList<PhoneNumber> phoneListg3= new ArrayList<PhoneNumber>();
		phoneListg3.add(numbersData.getNumber("22211178910"));
		phoneListg3.add(numbersData.getNumber("22222253210"));
		phoneListg3.add(numbersData.getNumber("33333356331"));
		phoneListg3.add(numbersData.getNumber("44444433141"));
		customerg3.setTelephoneNumbers(phoneListg3);
		Address addressg3 = new Address();
		addressg3.setFirstLine("2 Buckingham Palace");
		addressg3.setSecondLine("London");
		addressg3.setPostCode("SW2A 2AA");
		customerg3.setAddress(addressg3);
		
		Customer customerg4 = new Customer();
		customerg4.setId("69003");
		customerg4.setEmail("69003@lloydstsb.com");
		customerg4.setFirstname("Jerry");
		customerg4.setLastname("Garcia");
		customerg4.setOcisID("69003");
		ArrayList<PhoneNumber> phoneListg4= new ArrayList<PhoneNumber>();
		phoneListg4.add(numbersData.getNumber("22211178222"));
		phoneListg4.add(numbersData.getNumber("22222253333"));
		phoneListg4.add(numbersData.getNumber("33333356444"));
		phoneListg4.add(numbersData.getNumber("44444433555"));
		customerg4.setTelephoneNumbers(phoneListg4);
		Address addressg4 = new Address();
		addressg4.setFirstLine("3 Buckingham Palace");
		addressg4.setSecondLine("London");
		addressg4.setPostCode("SW3A 3AA");
		customerg4.setAddress(addressg3);
		
		Customer customerg5 = new Customer();
		customerg5.setId("69004");
		customerg5.setEmail("69004@lloydstsb.com");
		customerg5.setFirstname("Super");
		customerg5.setLastname("Mario");
		customerg5.setOcisID("69004");
		ArrayList<PhoneNumber> phoneListg5= new ArrayList<PhoneNumber>();
		phoneListg5.add(numbersData.getNumber("86451324585"));
		phoneListg5.add(numbersData.getNumber("86451444485"));
		phoneListg5.add(numbersData.getNumber("36663356444"));
		phoneListg5.add(numbersData.getNumber("44777733555"));
		customerg5.setTelephoneNumbers(phoneListg5);
		Address addressg5 = new Address();
		addressg5.setFirstLine("4 Buckingham Palace");
		addressg5.setSecondLine("London");
		addressg5.setPostCode("SW4A 4AA");
		customerg5.setAddress(addressg5);
		
		
		Customer customerg6 = new Customer();
		customerg6.setId("69005");
		customerg6.setEmail("69005@lloydstsb.com");
		customerg6.setFirstname("Luigi");
		customerg6.setLastname("Mario");
		customerg6.setOcisID("69005");
		ArrayList<PhoneNumber> phoneListg6= new ArrayList<PhoneNumber>();
		phoneListg6.add(numbersData.getNumber("86451324585"));
		phoneListg6.add(numbersData.getNumber("86451444485"));
		phoneListg6.add(numbersData.getNumber("36663356444"));
		phoneListg6.add(numbersData.getNumber("44777733555"));
		customerg6.setTelephoneNumbers(phoneListg6);
		Address addressg6 = new Address();
		addressg6.setFirstLine("5 Buckingham Palace");
		addressg6.setSecondLine("London");
		addressg6.setPostCode("SW5A 5AA");
		customerg6.setAddress(addressg6);
		
		
		Customer customer111 = new Customer();
		customer111.setId("69011");
		customer111.setEmail("69011@lloydstsb.com");
		customer111.setFirstname("Frederick");
		customer111.setLastname("Flint");
		customer111.setOcisID("69011");
		ArrayList<PhoneNumber> phoneList111= new ArrayList<PhoneNumber>();
		phoneList111.add(numbersData.getNumber("02081693217"));
		phoneList111.add(numbersData.getNumber("07856542351"));
		customer111.setTelephoneNumbers(phoneList111);
		Address address111 = new Address();
		address111.setFirstLine("345 Cavestone Road");
		address111.setSecondLine("Bedrock");
		address111.setPostCode("s3 1fm");
		customer111.setAddress(address111);
		
		
		Customer customer112 = new Customer();
		customer112.setId("69012");
		customer112.setEmail("69012@lloydstsb.com");
		customer112.setFirstname("Whelma");
		customer112.setLastname("Flint");
		customer112.setOcisID("69012");
		ArrayList<PhoneNumber> phoneList112= new ArrayList<PhoneNumber>();
		phoneList112.add(numbersData.getNumber("02081693217"));
		phoneList112.add(numbersData.getNumber("07835581123"));
		customer112.setTelephoneNumbers(phoneList112);
		Address address112 = new Address();
		address112.setFirstLine("345 Cavestone Road");
		address112.setSecondLine("Bedrock");
		address112.setPostCode("s3 1fm");
		customer112.setAddress(address112);
		
		
		Customer customer113 = new Customer();
		customer113.setId("69013");
		customer113.setEmail("69013@lloydstsb.com");
		customer113.setFirstname("George");
		customer113.setLastname("Etson");
		customer113.setOcisID("69013");
		ArrayList<PhoneNumber> phoneList113= new ArrayList<PhoneNumber>();
		phoneList113.add(numbersData.getNumber("02073215317"));
		phoneList113.add(numbersData.getNumber("07858735951"));
		customer113.setTelephoneNumbers(phoneList113);
		Address address113 = new Address();
		address113.setFirstLine("31 Skyway Street");
		address113.setSecondLine("Orbit");
		address113.setPostCode("JT2 3BS");
		customer113.setAddress(address113);

		Customer customer114 = new Customer();
		customer114.setId("69014");
		customer114.setEmail("69014@lloydstsb.com");
		customer114.setFirstname("Judy");
		customer114.setLastname("Etson");
		customer114.setOcisID("69014");
		ArrayList<PhoneNumber> phoneList114= new ArrayList<PhoneNumber>();
		phoneList114.add(numbersData.getNumber("02073215317"));
		phoneList114.add(numbersData.getNumber("07858735951"));
		customer114.setTelephoneNumbers(phoneList114);
		Address address114 = new Address();
		address114.setFirstLine("31 Skyway Street");
		address114.setSecondLine("Orbit");
		address114.setPostCode("JT2 3BS");
		customer114.setAddress(address114);
		
		Customer customer115 = new Customer();
		customer115.setId("69015");
		customer115.setEmail("69015@lloydstsb.com");
		customer115.setFirstname("Adrian");
		customer115.setLastname("Aringhe");
		customer115.setOcisID("69015");
		ArrayList<PhoneNumber> phoneList115= new ArrayList<PhoneNumber>();
		phoneList115.add(numbersData.getNumber("02084965468"));
		phoneList115.add(numbersData.getNumber("07865462155"));
		customer115.setTelephoneNumbers(phoneList115);
		Address address115 = new Address();
		address115.setFirstLine("66 Independence Street");
		address115.setSecondLine("Petrosani");
		address115.setPostCode("P37 5an");
		customer115.setAddress(address115);
		
		customers.put(customer1.getId(), customer1);
		customers.put(customer2.getId(), customer2);
		customers.put(customer3.getId(), customer3);
		customers.put(customer4.getId(), customer4);
		
		customers.put(customer111.getId(), customer111);
		customers.put(customer112.getId(), customer112);
		customers.put(customer113.getId(), customer113);
		customers.put(customer114.getId(), customer114);
		customers.put(customer115.getId(), customer115);

		customers.put(customerS1.getId(), customerS1);
		customers.put(customerS2.getId(), customerS2);
		customers.put(customerS3.getId(), customerS3);
		customers.put(customerS4.getId(), customerS4);
		customers.put(customerS5.getId(), customerS5);

		customers.put(customerg1.getId(), customerg1);
		customers.put(customerg2.getId(), customerg2);
		customers.put(customerg3.getId(), customerg3);
		customers.put(customerg4.getId(), customerg4);
		customers.put(customerg5.getId(), customerg5);
		customers.put(customerg6.getId(), customerg6);



		PersistenceContainer.getInstance().setCustomers(customers);
	}

}
