/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: EnrollmentLimitReachedException  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 8 Jan 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * <p>
 * Thrown when the number of devices enrolled exceeds the limit.
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class
		EnrolmentLimitReachedException extends ApiException {
	private static final long serialVersionUID = 1L;

	public EnrolmentLimitReachedException(IbErrorCode code) {
		super(code);
	}

	/* (non-Javadoc)
	 * @see com.lloydstsb.rest.v1.exceptions.ApiException#getStatus()
	 */
	@Override
	public int getStatus() {
		return Response.Status.BAD_REQUEST.getStatusCode();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
