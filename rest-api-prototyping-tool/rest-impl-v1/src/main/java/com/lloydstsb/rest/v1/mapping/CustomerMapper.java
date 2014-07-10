package com.lloydstsb.rest.v1.mapping;

import org.springframework.stereotype.Component;

import com.lloydstsb.rest.common.mapping.AbstractMapper;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.valueobjects.Customer;

@Component
public class CustomerMapper extends AbstractMapper<CustomerDomain, Customer> {


	public Customer map(CustomerDomain source) {
		Customer destination = new Customer();
		destination.setFirstname(source.getFirstname());
		destination.setLastname(source.getLastname());
		destination.setTitle(source.getTitle());
		destination.setShowTermsAndConditions(source.isShowTermsAndConditions());
		destination.setShowWelcomePage(source.isShowWelcomeScreen());
		return destination;
	}
}
