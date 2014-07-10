/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: MortgageUfssAccount  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 11 Dec 2012
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
public class MortgageUfssAccount extends MortgageAccount {
	private boolean possession;
	private boolean offset;

	/**
	 * @return the possession
	 */
	public boolean isPossession() {
		return possession;
	}

	/**
	 * @param possession the possession to set
	 */
	public void setPossession(boolean possession) {
		this.possession = possession;
	}

	/**
	 * @return the offset
	 */
	public boolean isOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(boolean offset) {
		this.offset = offset;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.MORTGAGE_UFSS_ACCOUNT;
	}
}