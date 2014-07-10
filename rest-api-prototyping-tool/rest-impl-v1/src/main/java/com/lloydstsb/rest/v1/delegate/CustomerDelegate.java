package com.lloydstsb.rest.v1.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.repository.CustomerRepository;

@Component
public class CustomerDelegate {
	
	@Autowired
	private CustomerRepository customerRepository;

	public CustomerDomain getCustomer(String customerId) {
		return customerRepository.findByCustomerId(customerId);
	}
	
	public void updateCustomer(CustomerDomain customerDomain){
		customerRepository.save(customerDomain);
	}
}
