/*********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ********************************************************************/
package com.lloydstsb.ib.common.error;

import java.io.Serializable;

/**
 * @author Anatoly Kupriyanov (9411793)
 * @since 03/05/2013 12:58
 */
public interface IbErrorCode extends Serializable{
	/**
	 * This method returns the error code in a string format of the event passed.
	 *
	 * @return the String representation of the event code passed
	 */
	String getErrorCode();
	/**
	 * Symbolic name of the error, human readable alternative to the error code
	 *
	 * @return the String representation of the event passed
	 */
	String getErrorName();
	/**
	 * THis method returns the error message corresponding to the event passed as it could be described in a source code
	 * (so in case if CMS is not ready).
	 *
	 * @return error message
	 */
	String getErrorDesc();
}
