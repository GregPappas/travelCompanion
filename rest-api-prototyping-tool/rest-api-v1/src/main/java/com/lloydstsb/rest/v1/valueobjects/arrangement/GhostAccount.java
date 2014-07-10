/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: GhostArrangement  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 17 Dec 2012
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;


/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public abstract class GhostAccount extends Arrangement {
	/* (non-Javadoc)
	 * @see com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement#getType()
	 */
	@Override
	public ArrangementType getType() {
		return ArrangementType.GHOST_ACCOUNT;
	}
}
