/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 26/02/2013 13:51
 */
public class TransferDeclinedOtherReasonException extends TransferDeclinedException {
	private static final long serialVersionUID = 1L;

	public TransferDeclinedOtherReasonException(final IbErrorCode code) {
		super(code);
	}
}
