package com.lloydstsb.rest.v1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.BeneficiaryDomain;

@Repository("beneficiaryRepository")
public interface BeneficiaryRepository extends PagingAndSortingRepository<BeneficiaryDomain, String> {
	
	Page<BeneficiaryDomain> findByArrangement(ArrangementDomain arrangement, Pageable pageable);
	
	BeneficiaryDomain findByBeneficiaryIdAndArrangementCustomerCustomerId(String beneficiaryId, String customerId);
}
