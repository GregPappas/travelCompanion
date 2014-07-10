/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: CapAccount  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 19 Dec 2012
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
public class CapAccount extends NamedAccount {

	/* (non-Javadoc)
	 * @see com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement#getType()
	 */
	@Override
	public ArrangementType getType() {
		return ArrangementType.CAP_ACCOUNT;
	}
}
