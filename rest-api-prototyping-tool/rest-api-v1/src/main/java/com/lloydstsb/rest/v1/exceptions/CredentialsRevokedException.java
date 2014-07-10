/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: CredentialsRevokedException  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 3 Dec 2012
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
public class CredentialsRevokedException extends ForbiddenException {
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param code
	 */
	public CredentialsRevokedException(IbErrorCode code) {
		super(code);
	}
}
