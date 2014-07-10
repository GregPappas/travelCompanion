/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.ib.common.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lloydstsb.ib.common.error.IbErrorCode;

public class IbErrorToCmsMapper {

	@SuppressWarnings("deprecation")// We support the deprecated mapping.
	private static final String NOT_MAPPED = IbErrorToCmsMap.NOT_MAPPED;

	private final Map<String, String> codeToCmsKey = new HashMap<String, String>();

	private final Map<String, IbErrorCode> codeToErrorCode = new HashMap<String, IbErrorCode>();

	public String getCmsKey(IbErrorCode error){
		final String cmsKey = codeToCmsKey.get(error.getErrorCode());
		return NOT_MAPPED.equals(cmsKey) ? null : cmsKey;
	}
	
	public void setMaps(final List<IbErrorToCmsMap> maps) {
		for (final IbErrorToCmsMap map : maps) {
			final Map<IbErrorCode, String> mapping = map.getMapping();
			for (final Map.Entry<IbErrorCode, String> entry : mapping.entrySet()) {
				final String cmsKey = entry.getValue();
				final IbErrorCode errorCode = entry.getKey();
				final String code = errorCode.getErrorCode();
				final String existingCmsKey = codeToCmsKey.put(code, cmsKey);
				if (existingCmsKey != null) {
					if (!existingCmsKey.equals(cmsKey)) {
						throw new IllegalStateException("Error code " + code + " has ambiguous mapping: '" + cmsKey + "' and '" + existingCmsKey + "'");
					}
				}
				codeToErrorCode.put(code, errorCode);
			}
		}
	}

	public Map<String, IbErrorCode> getCodeToErrorCode() {
		return codeToErrorCode;
	}
}
