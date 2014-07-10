package com.lloydstsb.rest.v1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lloydstsb.rest.v1.beneficiaries.SearchBeneficiaries;
import com.lloydstsb.rest.v1.delegate.ArrangementDelegate;
import com.lloydstsb.rest.v1.delegate.BeneficiaryDelegate;
import com.lloydstsb.rest.v1.delegate.CustomerDelegate;
import com.lloydstsb.rest.v1.delegate.TransactionDelegate;
import com.lloydstsb.rest.v1.domain.DomainList;
import com.lloydstsb.rest.v1.domain.SearchBeneficiaryDomain;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.mapping.SearchBeneficiaryMapper;
import com.lloydstsb.rest.v1.valueobjects.Page;
import com.lloydstsb.rest.v1.valueobjects.SearchBeneficiary;

@Service
public class JpaRepositoryBeneficiaryService implements SearchBeneficiaries {

	@Autowired
	private ArrangementDelegate arrangementDelegate;

	@Autowired
	private TransactionDelegate transactionDelegate;

	@Autowired
	private BeneficiaryDelegate beneficiaryDelegate;
	
	@Autowired
	private CustomerDelegate customerDelegate;

	@Context
	private HttpServletRequest request;
	
	@Autowired
	private SearchBeneficiaryMapper searchBeneficiaryMapper;

	public Page<SearchBeneficiary> searchBeneficiaries(
			String sortCode,
			String accountNumber,
			String name,
			int page,
			int size)
			throws NotFoundException {

		DomainList<SearchBeneficiaryDomain> beneficiaryDomains = beneficiaryDelegate.getBeneficiaries(sortCode, accountNumber, page, size);
		List<SearchBeneficiary> beneficiaries = searchBeneficiaryMapper.map(beneficiaryDomains.getEntities());
		
		Page<SearchBeneficiary> beneficiaryList = new Page<SearchBeneficiary>();
		beneficiaryList.setPage(page);
		beneficiaryList.setSize(size);
		beneficiaryList.setTotal(beneficiaryDomains.getTotalAvailable());
		beneficiaryList.setItems(beneficiaries);
 
		return beneficiaryList;
	}
}