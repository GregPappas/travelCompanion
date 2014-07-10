/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.constants;

import com.lloydstsb.ib.common.error.IbErrorCode;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 03/05/2013 15:06
 */
public enum RestErrorConstants implements IbErrorCode {

	/**
	 * Error code for generic runtime exceptions.
	 */
	EXCP_RUNTIME_EXCEPTION("9310000", "Unhandled runtime exception"),
	/**
	 * Error code for generic illegal argument exceptions.
	 */
	EXCP_ILLEGAL_ARGUMENT_EXCEPTION("9310001", "Illegal argument exception"),
	SERVICE_UNAVAILABLE("9310002"),
	CONSTRAINT_VIOLATION("9310003"),
	ERR_RESOURCE_MISSING("9310004", "The requested resource is missing in WCM."),
	USER_PARTIALLY_REGISTERED("9310005", "User is partially registered."),

	HEALTH_MESSAGE_JAILBROKEN_ROOTED_SDK_PRE_ENROL("9300100"),
	HEALTH_MESSAGE_JAILBROKEN_ROOTED_SDK_DE_ENROL("9300101"),
	HEALTH_MESSAGE_TAMPERED_PRE_ENROL("9300102"),
	HEALTH_MESSAGE_TAMPERED_DE_ENROL("9300103"),
	HEALTH_MESSAGE_MALWARE_PRE_ENROL("9300104"),
	HEALTH_MESSAGE_MALWARE_DE_ENROL("9300105"),
	DEVICE_RISK_SCORE_PRE_ENROL("9300106"),
	DEVICE_RISK_SCORE_DE_ENROL("9300107"),
	BAPI_TRY_AGAIN_LATER("9200001"),
	TRY_AGAIN_LATER("9200009"),
	BAPI_TRY_AGAIN_LATER_SCENRAIO("9100003"),
	BAPI_TRY_AGAIN_LATER_SCENRAIO1("9200003"),
	BAPI_TRY_AGAIN_LATER_SCENRAIO2("1100090"),
	BAPI_TRY_AGAIN_LATER_SCENRAIO3("1700023"),
	BAPI_TRY_AGAIN_LATER_SCENRAIO7("9200080"),
	BAPI_TRY_AGAIN_LATER_SCENRAIO9("9201000"),
	BAPI_TRY_AGAIN_LATER_SCENRAI10("9200790"),
	BAPI_TRY_AGAIN_LATER_SCENRAI11("1600226"),
	BAPI_TRY_AGAIN_LATER_SCENRAI12("9200220"),
	BAPI_TRY_AGAIN_LATER_SCENRAI13("9210221"),
	BAPI_TRY_AGAIN_LATER_SCENRAI14("9200086"),
	AUTH_FAILED_ERROR_CASE1("1100212"),
	AUTH_FAILED_ERROR_CASE2("9200352"),
	ARRANGEMENT_SETUP_FAILURE("131440"),
	RETRY_EXCEPTION("8000066"),
	RESET_PASSWORD_MI("9200142"),
	NOT_REGISTERED_EXCEPTION("9200267"),

	;

	private final String errorCode;
	private final String errorDesc;


	RestErrorConstants(final String errorCode, final String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}


	RestErrorConstants(final String errorCode) {
		this.errorCode = errorCode;
		this.errorDesc = name();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorName() {
		return name();
	}

	public String getErrorDesc() {
		return errorDesc;
	}
}
