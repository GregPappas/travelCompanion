/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Date: 29 Oct 2012
 ***********************************************************************/
package com.lloydstsb.rest.documentation.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lloydstsb.rest.util.populator.ObjectPopulator;
import com.lloydstsb.rest.v1.beneficiaries.SearchBeneficiaries;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.Page;

/**
 * <p> 
 * </p>
 *
 * @author Jesper Madsen (CT026780)
 */
@Service
public class BeneficiaryDocumentation implements SearchBeneficiaries {
	private ObjectPopulator objectPopulator = new ObjectPopulator();
	
	public Page<Beneficiary> searchBeneficiaries(
			String sortCode, String accountNumber, String beneficiaryName, int page, int size)
			throws NotFoundException {
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		beneficiaries.add(objectPopulator.populate(Beneficiary.class));
		beneficiaries.add(objectPopulator.populate(Beneficiary.class));

		Page<Beneficiary> beneficiaryList = new Page<Beneficiary>();
		beneficiaryList.setPage(page);
		beneficiaryList.setSize(size);
		beneficiaryList.setItems(beneficiaries);
		beneficiaryList.setTotal(100);
		return beneficiaryList;
	}

}
