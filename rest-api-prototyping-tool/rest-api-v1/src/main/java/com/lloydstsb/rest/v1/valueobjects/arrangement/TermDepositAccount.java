package com.lloydstsb.rest.v1.valueobjects.arrangement;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lloydstsb.rest.common.types.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TermDepositAccount extends DepositAccount {

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date maturityDate;

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	@Override
	public ArrangementType getType() {
		return ArrangementType.TERM_DEPOSIT_ACCOUNT;
	}
}
