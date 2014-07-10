package com.lloydstsb.rest.documentation.v1;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.springframework.stereotype.Service;

import com.lloydstsb.rest.util.populator.ObjectPopulator;
import com.lloydstsb.rest.v1.customer.GetCustomerService;
import com.lloydstsb.rest.v1.exceptions.AliasNameNotUniqueException;
import com.lloydstsb.rest.v1.exceptions.EnrolmentLimitReachedException;
import com.lloydstsb.rest.v1.valueobjects.Address;
import com.lloydstsb.rest.v1.valueobjects.Customer;
import com.lloydstsb.rest.v1.valueobjects.PhoneNumber;

@Service
public class CustomerDocumentation implements GetCustomerService/*, AcceptTermsAndConditions, CreateMemorableInformation, UpdateCustomer, EnrolCustomerService, UnEnrolCustomerService, CaptureAliasNameService*/ {


	private ObjectPopulator objectPopulator = new ObjectPopulator();

	public Customer getCustomer() {
		Customer customer = objectPopulator.populate(Customer.class);
		List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		PhoneNumber mobileNumber = new PhoneNumber();
		mobileNumber.setPhoneNumber("44123****297");
		mobileNumber.setType("MOBILE");
		mobileNumber.setValid(true);
		phoneNumbers.add(mobileNumber);
		
		PhoneNumber workNumber = new PhoneNumber();
		workNumber.setPhoneNumber("44121****581");
		workNumber.setType("WORK");
		workNumber.setExtension("1234");
		workNumber.setValid(true);
		phoneNumbers.add(workNumber);
		
		PhoneNumber homeNumber = new PhoneNumber();
		homeNumber.setPhoneNumber("4427****122");
		homeNumber.setType("HOME");
		homeNumber.setValid(true);
		phoneNumbers.add(homeNumber);
		
		customer.setTelephoneNumbers(phoneNumbers);
		
		Address address = objectPopulator.populate(Address.class);
		customer.setAddress(address);
		
		return customer;
	}

	public void acceptTermsAndConditions() {}

	public void createMemorableInformation(String memorableInformation) {}

//	public void enrolCustomer(String authMessage, String wasMessage, String transactionId, String deviceType) {
//	}

//	public void updateCustomer(MultivaluedMap<String, String> formData) {
//	}


//	public void unEnrolCustomer(String authMessage) {
//	}
	
//	public EnrolmentAuthentication captureAliasName(String aliasName)
//			throws EnrolmentLimitReachedException,
//			AliasNameNotUniqueException {
//		return objectPopulator.populate(EnrolmentAuthentication.class);
//	}
}
