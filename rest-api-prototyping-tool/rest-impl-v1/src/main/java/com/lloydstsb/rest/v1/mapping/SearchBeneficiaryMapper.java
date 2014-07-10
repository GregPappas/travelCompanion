package com.lloydstsb.rest.v1.mapping;

import org.springframework.stereotype.Component;

import com.lloydstsb.rest.common.mapping.AbstractMapper;
import com.lloydstsb.rest.v1.domain.SearchBeneficiaryDomain;
import com.lloydstsb.rest.v1.valueobjects.SearchBeneficiary;

@Component
public class SearchBeneficiaryMapper extends AbstractMapper<SearchBeneficiaryDomain, SearchBeneficiary> {

	public SearchBeneficiary map(SearchBeneficiaryDomain source) {
		SearchBeneficiary destination = new SearchBeneficiary();
		destination.setAccountNumber(source.getAccountNumber());
		destination.setName(source.getName());
		destination.setSortCode(source.getSortCode());
		destination.setNotes(source.getNotes());
		return destination;
	}
}
