/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ CurrentAccount.class,/* SavingsAccount.class, CreditCardAccount.class, IsaAccount.class, MortgageAccount.class, 
	NonCbsPersonalLoanAccount.class, TermDepositAccount.class, CbsPersonalLoanAccount.class, SavedLoanAccount.class,
	InvestmentAccount.class, GiaAccount.class, MortgageUfssAccount.class, CapAccount.class, ShareDealingAccount.class,
	IsaInvestmentAccount.class */ })
@JsonTypeInfo(use=Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ 
	@Type(value = CurrentAccount.class, name = "CURRENT_ACCOUNT"),
//	@Type(value = SavingsAccount.class, name = "SAVINGS_ACOUNT"),
//	@Type(value = CreditCardAccount.class, name = "CREDITCARD_ACCOUNT"),
//	@Type(value = IsaAccount.class, name = "ISA_ACCOUNT"),
//	@Type(value = MortgageAccount.class, name = "MORTGAGE_ACCOUNT"),
//	@Type(value = CbsPersonalLoanAccount.class, name = "CBS_PERSONAL_LOAN_ACCOUNT"),
//	@Type(value = NonCbsPersonalLoanAccount.class, name = "NON_CBS_PERSONAL_LOAN_ACCOUNT"),
//	@Type(value = TermDepositAccount.class, name = "TERM_DEPOSIT_ACCOUNT"),
//	@Type(value = SavedLoanAccount.class, name = "SAVED_LOAN_ACCOUNT"),
//	@Type(value = InvestmentAccount.class, name = "INVESTMENT_ACCOUNT"),
//	@Type(value = GiaAccount.class, name = "GIA_ACCOUNT"),
//	@Type(value = MortgageUfssAccount.class, name = "MORTGAGE_UFSS_ACCOUNT"),
//	@Type(value = CapAccount.class, name = "CAP_ACCOUNT"),
//	@Type(value = ShareDealingAccount.class, name = "SHARE_DEALING_ACCOUNT"),
//	@Type(value = IsaInvestmentAccount.class, name = "ISA_INVESTMENT_ACCOUNT")
})
public abstract class Arrangement {
	public abstract ArrangementType getType();
	private List<String> messages = new ArrayList<String>();
	
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public void addMessage(String message){
		this.getMessages().add(message);
	}
}