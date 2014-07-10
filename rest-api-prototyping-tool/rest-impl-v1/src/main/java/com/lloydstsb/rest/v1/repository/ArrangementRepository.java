package com.lloydstsb.rest.v1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.CustomerDomain;

@Repository("arrangementRepository")
public interface ArrangementRepository extends PagingAndSortingRepository<ArrangementDomain, String> {
	
	ArrangementDomain findByCustomerAndArrangementId(CustomerDomain customer, String arrangementId);
	
	Page<ArrangementDomain> findByCustomer(CustomerDomain customer, Pageable pageable);
}
