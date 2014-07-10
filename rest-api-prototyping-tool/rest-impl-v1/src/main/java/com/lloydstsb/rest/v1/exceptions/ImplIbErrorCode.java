/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.exceptions;

import com.lloydstsb.ib.common.error.IbErrorCode;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 03/05/2013 15:49
 */
public class ImplIbErrorCode implements IbErrorCode {

	private final String errorCode;

	private final String errorDesc;

	private ImplIbErrorCode(final String errorCode, final String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public static ImplIbErrorCode errorDescCode(final String errorDesc, final String errorCode) {
		return new ImplIbErrorCode(errorCode, errorDesc);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorName() {
		return "IMPL" + errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	@Override
	public String toString() {
		return "ImplIbErrorCode{" +
				"errorCode='" + errorCode + '\'' +
				", errorDesc='" + errorDesc + '\'' +
				'}';
	}
}
