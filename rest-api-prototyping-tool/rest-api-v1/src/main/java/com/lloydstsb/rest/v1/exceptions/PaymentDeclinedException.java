package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;


public class PaymentDeclinedException extends ApiException {
	private static final long serialVersionUID = 1L;
	private static final int PAYMENT_DECLINED = 402;

	public PaymentDeclinedException(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return PAYMENT_DECLINED;
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
