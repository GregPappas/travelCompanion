/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: ShareDealingAccount  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 18 Dec 2012
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShareDealingAccount extends GhostAccount {
	private String shareDealingCode;

	/**
	 * @return the shareDealingCode
	 */
	public String getShareDealingCode() {
		return shareDealingCode;
	}

	/**
	 * @param shareDealingCode the shareDealingCode to set
	 */
	public void setShareDealingCode(String shareDealingCode) {
		this.shareDealingCode = shareDealingCode;
	}
	
	/* (non-Javadoc)
	 * @see com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement#getType()
	 */
	@Override
	public ArrangementType getType() {
		return ArrangementType.SHARE_DEALING_ACCOUNT;
	}
}