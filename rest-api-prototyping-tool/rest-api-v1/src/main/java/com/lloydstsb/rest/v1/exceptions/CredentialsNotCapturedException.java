/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: CredentialsNotCapturedException  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 3 Dec 2012
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import javax.ws.rs.core.Response;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class CredentialsNotCapturedException extends NonLoggingException {
	private static final long serialVersionUID = 1L;

	public CredentialsNotCapturedException(IbErrorCode code) {
		super(code);
	}
	
	public int getStatus() {
		return Response.Status.PRECONDITION_FAILED.getStatusCode();
	}
	
	public ErrorCategory getCategory() {
		return ErrorCategory.FAILED_OUTCOME;
	}
}
