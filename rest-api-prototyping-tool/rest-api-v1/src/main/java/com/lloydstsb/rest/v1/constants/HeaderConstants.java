/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: HeaderConstants  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 7 Feb 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.constants;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public class HeaderConstants {
	
	public static final String HEALTH_MESSAGE = "X-1-TRANSPORT";
	public static final String WAS_MESSAGE = "X-2-TRANSPORT";
	public static final String AUTH_MESSAGE = "X-3-TRANSPORT";
	public static final String WAS_KEY = "X-4-TRANSPORT";
	public static final String WAS_SIGNATURE = "X-5-TRANSPORT";
	public static final String ALIAS_NAME = "Alias-Name";
	public static final String PLATFORM = "Platform";
	public static final String APP_VERSION = "App-Version";
	public static final String APP_NAME = "App-Name";
	public static final String KEY_VERSION = "X-6-TRANSPORT";
	public static final String APP_VERSION_DEPRECATED = "App-Version-Deprecated";
	public static final String RESOURCE_VERSION_DEPRECATED = "Resource-Deprecated";

	private HeaderConstants() {
		super();
	}
}

