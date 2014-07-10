/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.rest.v1.content;

import com.lloydstsb.ib.common.content.IbErrorToCmsMap;
import com.lloydstsb.rest.v1.constants.RestErrorConstants;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 16/05/2013 11:19
 */
public class RestErrorMapping extends IbErrorToCmsMap {
	@Override
	protected void populateMap() {
		map(RestErrorConstants.EXCP_RUNTIME_EXCEPTION, "cmsmsg912_002");
		map(RestErrorConstants.EXCP_ILLEGAL_ARGUMENT_EXCEPTION, "cmsmsg912_002");
		map(RestErrorConstants.SERVICE_UNAVAILABLE, "cmsmsg912_002");
		map(RestErrorConstants.CONSTRAINT_VIOLATION, "cmsmsg912_002");
	}
}
