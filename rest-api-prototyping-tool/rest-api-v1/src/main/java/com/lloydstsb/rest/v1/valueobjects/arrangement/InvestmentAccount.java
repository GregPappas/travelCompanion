package com.lloydstsb.rest.v1.valueobjects.arrangement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InvestmentAccount extends NamedAccount {

	@Override
	public ArrangementType getType() {
		return ArrangementType.INVESTMENT_ACCOUNT;
	}
}