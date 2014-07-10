/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: NoValidPhoneNumbersException  
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
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class BadPhoneNumberException extends BadRequestException {
	private static final long serialVersionUID = 1L;

	public BadPhoneNumberException(final IbErrorCode code) {
		super(code);
	}
}
