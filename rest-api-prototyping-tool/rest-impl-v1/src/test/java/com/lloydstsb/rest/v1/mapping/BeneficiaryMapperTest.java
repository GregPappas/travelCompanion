package com.lloydstsb.rest.v1.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.lloydstsb.rest.common.mapping.Mapper;
import com.lloydstsb.rest.v1.domain.BeneficiaryDomain;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;

public class BeneficiaryMapperTest {
	private Mapper<BeneficiaryDomain, Beneficiary> mapper;
	private BeneficiaryDomain domain;

	@Before
	public void setUp() {
		mapper = new BeneficiaryMapper();
		domain = new BeneficiaryDomain();
		domain.setAccountNumber("account number");
		domain.setSortCode("sort code");
		domain.setName("name");
	}

	@Test
	public void testMap() throws Exception {
		Beneficiary value = mapper.map(domain);
		assertEquals(domain.getAccountNumber(), value.getAccountNumber());
		assertEquals(domain.getSortCode(), value.getSortCode());
		assertEquals(domain.getName(), value.getName());
	}
}
