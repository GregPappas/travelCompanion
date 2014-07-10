/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: BadReferenceException  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 15 Nov 2012
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

/**
 * <p>
 * Exception thrown for bad references (for payment / beneficiary).
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class BadReferenceException extends BadRequestException {
	private static final long serialVersionUID = 1L;

	public BadReferenceException(IbErrorCode code) {
		super(code);
	}
}
