package com.lloydstsb.rest.v1.delegate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.lloydstsb.rest.v1.domain.ArrangementDomain;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.domain.DomainList;
import com.lloydstsb.rest.v1.domain.TransactionDomain;
import com.lloydstsb.rest.v1.repository.TransactionRepository;

@Component
public class TransactionDelegate {

	@Autowired
	private TransactionRepository transactionRepository;

	public DomainList<TransactionDomain> getTransactionsBetweenDates(CustomerDomain customer, ArrangementDomain arrangement, Date startDate, Date endDate, Integer pageIndex, Integer pageSize) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<TransactionDomain> page = transactionRepository.findByArrangementAndArrangementCustomerAndTransactionDateBetween(arrangement, customer, startDate, endDate, pageable); 

		return new DomainList<TransactionDomain>(page.getContent(), (int)page.getTotalElements());
	}
	
	public void saveTransaction(TransactionDomain transaction) {
		transactionRepository.save(transaction);
	}
	
	public TransactionDomain getTransaction(String id) {
		return transactionRepository.findOne(id);
	}
}
