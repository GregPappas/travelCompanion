package com.lloydstsb.rest.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lloydstsb.rest.v1.customer.AcceptTermsAndConditions;
import com.lloydstsb.rest.v1.customer.CaptureAliasNameService;
import com.lloydstsb.rest.v1.customer.CreateMemorableInformation;
import com.lloydstsb.rest.v1.customer.GetCustomerService;
import com.lloydstsb.rest.v1.customer.UpdateCustomer;
import com.lloydstsb.rest.v1.delegate.CustomerDelegate;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.exceptions.AliasNameNotUniqueException;
import com.lloydstsb.rest.v1.exceptions.EnrolmentLimitReachedException;
import com.lloydstsb.rest.v1.mapping.CustomerMapper;
import com.lloydstsb.rest.v1.valueobjects.AuthenticationType;
import com.lloydstsb.rest.v1.valueobjects.Customer;
import com.lloydstsb.rest.v1.valueobjects.EnrolmentAuthentication;
import com.lloydstsb.rest.v1.valueobjects.PhoneNumber;

@Service
public class JpaRepositoryCustomerService implements GetCustomerService, AcceptTermsAndConditions, CreateMemorableInformation, UpdateCustomer, CaptureAliasNameService {
	
	@Autowired
	private CustomerDelegate customerDelegate;

	@Autowired
	private CustomerMapper customerMapper;

	@Context
	private HttpServletRequest request;

	public Customer getCustomer() {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		return customerMapper.map(customer);
	}

	public void acceptTermsAndConditions() {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		customer.setShowTermsAndConditions(false);
		customerDelegate.updateCustomer(customer);
	}

	public void createMemorableInformation(String memorableInformation) {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		customer.setMemorableWord(memorableInformation);
		customerDelegate.updateCustomer(customer);
	}

	public void updateCustomer(MultivaluedMap<String, String> formData) {
		String customerId = Util.getAuthenticatedCustomerId(request);
		CustomerDomain customer = customerDelegate.getCustomer(customerId);
		if(formData.containsKey("email")){
			customer.setEmail(formData.getFirst("email").toString());
			customerDelegate.updateCustomer(customer);
		}
	}
	
	public EnrolmentAuthentication captureAliasName(String aliasName)
			throws EnrolmentLimitReachedException, AliasNameNotUniqueException {
		EnrolmentAuthentication pns = new EnrolmentAuthentication();
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>(2);
		PhoneNumber pn = new PhoneNumber();
		pn.setPhoneNumber("07832934853");
		pn.setType("Mobile");
		pn.setValid(true);
		numbers.add(pn);
		pn = new PhoneNumber();
		pn.setPhoneNumber("02079393233");
		pn.setType("Work");
		pn.setValid(false);
		numbers.add(pn);
		pns.setPhoneNumbers(numbers);
		pns.setAuthenticationType(AuthenticationType.EIA);
		pns.setTransactionId("821371881631-12312312");
		return pns;
	}
}