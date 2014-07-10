package com.lloydstsb.rest.v1.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.domain.TransactionDomain;

@Repository("customerRepository")
public interface TransactionRepository extends PagingAndSortingRepository<TransactionDomain, String> {
	
	Page<TransactionDomain> findByArrangementAndArrangementCustomerAndTransactionDateBetween(ArrangementDomain arrangement, CustomerDomain customer, Date startDate, Date endDate, Pageable pageable);
}
