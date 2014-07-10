package com.lloydstsb.rest.v1.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.domain.DomainList;
import com.lloydstsb.rest.v1.repository.ArrangementRepository;

@Component
public class ArrangementDelegate {
	
	@Autowired
	private ArrangementRepository arrangementRepository;

	public DomainList<ArrangementDomain> getArrangements(CustomerDomain customer, Integer pageIndex, Integer pageSize) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<ArrangementDomain> page = arrangementRepository.findByCustomer(customer, pageable); 

		return new DomainList<ArrangementDomain>(page.getContent(), (int)page.getTotalElements());
	}

	public ArrangementDomain getArrangement(CustomerDomain customer, String arrangementId) {
		return arrangementRepository.findByCustomerAndArrangementId(customer, arrangementId);
	}
}
