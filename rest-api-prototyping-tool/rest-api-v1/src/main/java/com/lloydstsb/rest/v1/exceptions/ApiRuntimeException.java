/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: ApiRuntimeException  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 11 Mar 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

/**
 * <p>
 * Exception base class for API runtime exceptions;
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public abstract class ApiRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final IbErrorCode code;

	public ApiRuntimeException(final IbErrorCode code) {
		super(code.getErrorDesc());
		this.code = code;
	}

	public IbErrorCode getCode() {
		return code;
	}

	public abstract int getStatus();
	public abstract ErrorCategory getCategory();
}
