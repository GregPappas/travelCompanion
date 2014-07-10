/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class AmountExceedsBalanceException extends BadTransferRequestException {
	private static final long serialVersionUID = 1L;

	public AmountExceedsBalanceException(IbErrorCode code) {
		super(code);
	}
}
