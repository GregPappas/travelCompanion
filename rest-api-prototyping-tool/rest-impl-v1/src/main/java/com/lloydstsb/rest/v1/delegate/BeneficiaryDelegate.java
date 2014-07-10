package com.lloydstsb.rest.v1.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.BeneficiaryDomain;
import com.lloydstsb.rest.v1.domain.DomainList;
import com.lloydstsb.rest.v1.domain.SearchBeneficiaryDomain;
import com.lloydstsb.rest.v1.repository.BeneficiaryRepository;
import com.lloydstsb.rest.v1.repository.SearchBeneficiaryRepository;

@Component
public class BeneficiaryDelegate {

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private SearchBeneficiaryRepository searchBeneficiaryRepository;

	public DomainList<BeneficiaryDomain> getBeneficiariesByArrangement(ArrangementDomain arrangement, Integer pageIndex, Integer pageSize) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<BeneficiaryDomain> page = beneficiaryRepository.findByArrangement(arrangement, pageable); 

		return new DomainList<BeneficiaryDomain>(page.getContent(), (int)page.getTotalElements());
	}

	public BeneficiaryDomain getBeneficiaryByBeneficiaryId(String customerId, String beneficiaryId) {
		return beneficiaryRepository.findByBeneficiaryIdAndArrangementCustomerCustomerId(beneficiaryId, customerId);
	}

	public void saveBeneficiary(BeneficiaryDomain beneficiary) {
		beneficiaryRepository.save(beneficiary);
	}
	
	public DomainList<SearchBeneficiaryDomain> getBeneficiaries(String sortcode, String accountNo, int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<SearchBeneficiaryDomain> page = searchBeneficiaryRepository.findBySortCodeAndAccountNumber(sortcode, accountNo, pageable);

		return new DomainList<SearchBeneficiaryDomain>(page.getContent(), (int)page.getTotalElements());
	}
}
