/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *   
 * Date: 5 Nov 2012 
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import org.springframework.http.HttpStatus;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * <p>
 * Exception thrown when the creation of a new beneficiary fails.
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class CreateBeneficiaryDeclined extends ApiException {
	private static final long serialVersionUID = 1L;

	public CreateBeneficiaryDeclined(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return HttpStatus.PRECONDITION_FAILED.value();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
