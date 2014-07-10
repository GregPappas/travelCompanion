package com.lloydstsb.rest.v1.mapping;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.lloydstsb.rest.common.mapping.AbstractMapper;
import com.lloydstsb.rest.v1.domain.TransactionDomain;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.Transaction;

@Component
public class TransactionMapper extends AbstractMapper<TransactionDomain, Transaction> {

	public Transaction map(TransactionDomain source) {
		Transaction destination = new Transaction();
		
		destination.setDate(source.getTransactionDate());
		destination.setDescription(source.getDescription());
		
		CurrencyAmount amountCurrency = new CurrencyAmount();
		amountCurrency.setAmount(new BigDecimal(source.getAmount()));
		amountCurrency.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
		
		destination.setAmount(amountCurrency);
		
		CurrencyAmount runningBalancecurrency = new CurrencyAmount();
		runningBalancecurrency.setAmount(new BigDecimal(source.getRunningBalance()));
		runningBalancecurrency.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
		
		destination.setRunningBalance(runningBalancecurrency);
		return destination;
	}
}
