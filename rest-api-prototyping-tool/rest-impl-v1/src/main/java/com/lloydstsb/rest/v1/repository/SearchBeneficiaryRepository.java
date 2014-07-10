package com.lloydstsb.rest.v1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.lloydstsb.rest.v1.domain.SearchBeneficiaryDomain;

@Repository("searchBeneficiaryRepository")
public interface SearchBeneficiaryRepository extends PagingAndSortingRepository<SearchBeneficiaryDomain, String> {

	Page<SearchBeneficiaryDomain> findBySortCodeAndAccountNumber(String sortCode, String accountNumber, Pageable pageable);
}
