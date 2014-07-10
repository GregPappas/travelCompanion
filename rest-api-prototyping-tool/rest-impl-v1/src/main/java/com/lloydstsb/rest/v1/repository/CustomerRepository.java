package com.lloydstsb.rest.v1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lloydstsb.rest.v1.domain.CustomerDomain;

@Repository("customerRepository")
public interface CustomerRepository extends CrudRepository<CustomerDomain, String> {
	
	CustomerDomain findByCustomerId(String customerId);

}
