/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.ib.common.content;

import java.util.HashMap;
import java.util.Map;

import com.lloydstsb.ib.common.error.IbErrorCode;

public abstract class IbErrorToCmsMap {

	@Deprecated
	protected static final String NOT_MAPPED = "";

	private final Map<IbErrorCode, String> mapping = new HashMap<IbErrorCode, String>();

	public IbErrorToCmsMap() {
		populateMap();
	}

	public Map<IbErrorCode, String> getMapping() {
		return mapping;
	}

	abstract protected void populateMap();
	
	protected void map(IbErrorCode error, String cmsKey){
		if (error == null) {
			throw new NullPointerException("error == null");
		}
		if (cmsKey == null) {
			throw new NullPointerException("cmsKey == null");
		}
		final String existing = mapping.put(error, cmsKey);
		if (existing != null) {
			throw new IllegalStateException("Duplicate '" + cmsKey + "' for '" + error + "' and " + existing + "'");
		}
	}
}
