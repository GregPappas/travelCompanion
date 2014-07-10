/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: PaymentDelayedException  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 15 Nov 2012
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * <p>
 * Error for when payments are held for review.
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class PaymentHeldForReviewException extends ApiException {
	private static final long serialVersionUID = 1L;
	private static final int PAYMENT_HELD_FOR_REVIEW = 202;

	public PaymentHeldForReviewException(IbErrorCode code) {
		super(code);
	}

	public int getStatus() {
		return PAYMENT_HELD_FOR_REVIEW;
	}

	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
