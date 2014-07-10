package com.lloydstsb.rest.v1.mapping;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.lloydstsb.rest.common.mapping.Mapper;
import com.lloydstsb.rest.v1.domain.TransactionDomain;
import com.lloydstsb.rest.v1.valueobjects.Transaction;

public class TransactionMapperTest {
	private Mapper<TransactionDomain, Transaction> mapper;
	private TransactionDomain domain;

	@Before
	public void setUp() {
		mapper = new TransactionMapper();
		domain = new TransactionDomain();
		domain.setRunningBalance("50.0");
		domain.setRunningBalanceCurrency("GBP");
		domain.setAmount("10.0");
		domain.setAmountCurrency("GBP");
		domain.setDescription("description");
		domain.setTransactionDate(new Date());
	}

	@Test
	public void testMap() throws Exception {
		Transaction value = mapper.map(domain);
		assertEquals(domain.getAmount(), value.getAmount().getAmount().toString());
		assertEquals(domain.getAmountCurrency(), value.getAmount().getCurrency().toString());
		assertEquals(domain.getDescription(), value.getDescription());
		assertEquals(domain.getRunningBalance(), value.getRunningBalance().getAmount().toString());
		assertEquals(domain.getRunningBalanceCurrency(), value.getRunningBalance().getCurrency().toString());
		assertEquals(domain.getTransactionDate(), value.getDate());
	}
}
