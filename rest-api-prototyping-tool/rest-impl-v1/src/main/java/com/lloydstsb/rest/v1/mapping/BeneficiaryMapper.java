package com.lloydstsb.rest.v1.mapping;

import org.springframework.stereotype.Component;

import com.lloydstsb.rest.common.mapping.AbstractMapper;
import com.lloydstsb.rest.v1.domain.BeneficiaryDomain;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

@Component
public class BeneficiaryMapper extends AbstractMapper<BeneficiaryDomain, Beneficiary> {

	public Beneficiary map(BeneficiaryDomain source) {
		Beneficiary destination = new Beneficiary();
		destination.setAccountNumber(source.getAccountNumber());
		destination.setName(source.getName());
		destination.setSortCode(source.getSortCode());
		return destination;
	}
}
