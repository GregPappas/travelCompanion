/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.ib.common.error;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 03/05/2013 16:22
 * @deprecated This class is used to describe ad-hock undeclared errors. Please specify dedicated error constant instead
 */
@Deprecated
public class UndeclaredIbErrorCode implements IbErrorCode {
	private static final long serialVersionUID = 6276486453664765529L;

	private final String errorCode;
	private final String errorName;
	private final String errorDesc;

	public UndeclaredIbErrorCode(final String errorCode, final String errorDesc) {
		this(errorCode, "UNDECLARED" + errorCode, errorDesc);
	}
	public UndeclaredIbErrorCode(final String errorCode, final String errorName, final String errorDesc) {
		this.errorCode = errorCode;
		this.errorName = errorName;
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorName() {
		return errorName;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	@Override
	public String toString() {
		return "UndeclaredIbErrorCode{" +
				"errorCode='" + errorCode + '\'' +
				", errorName='" + errorName + '\'' +
				", errorDesc='" + errorDesc + '\'' +
				'}';
	}
}
