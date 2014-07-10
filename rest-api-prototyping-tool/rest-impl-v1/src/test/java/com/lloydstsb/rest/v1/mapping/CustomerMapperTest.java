package com.lloydstsb.rest.v1.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.lloydstsb.rest.common.mapping.Mapper;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.valueobjects.Customer;

public class CustomerMapperTest {
	private Mapper<CustomerDomain, Customer> mapper;
	private CustomerDomain domain;

	@Before
	public void setUp() {
		mapper = new CustomerMapper();
		domain = new CustomerDomain();
		domain.setFirstname("First name");
		domain.setLastname("last name");
		domain.setTitle("title");
	}

	@Test
	public void testMap() throws Exception {
		Customer value = mapper.map(domain);
		assertEquals(domain.getFirstname(), value.getFirstname());
		assertEquals(domain.getLastname(), value.getLastname());
		assertEquals(domain.getTitle(), value.getTitle());
	}
}
