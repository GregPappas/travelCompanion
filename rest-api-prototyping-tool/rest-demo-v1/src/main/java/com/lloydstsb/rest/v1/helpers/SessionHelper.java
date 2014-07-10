package com.lloydstsb.rest.v1.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.Transaction;

public class SessionHelper{

	private HttpServletRequest request;
	
	public SessionHelper(HttpServletRequest request){
		this.setRequest(request);
	}
	
	public SessionHelper(){
		
	}
	
	public void addVariableToSession(String returnedClientId, String sessionVariableName ){
		getSession().setAttribute(sessionVariableName, returnedClientId);
	}
	
	
	public HttpSession getSession(){
		return this.getRequest().getSession(true);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	//returns null if no user is logged
	public String returnSessionVariable(String sessionVariableName){
		
		if (getSession().getAttribute(sessionVariableName)!=null) {
			return getSession().getAttribute(sessionVariableName).toString();
		}
		return null;
	}
	
	public void setBeneficiary(Beneficiary beneficiary)
	{
		getSession().setAttribute(beneficiary.getId(), beneficiary);
	}
	
	public Beneficiary returnBeneficiarySessionVariable(String beneficiaryId)
	{
		if (getSession().getAttribute(beneficiaryId)!=null) 
		{	
			return (Beneficiary)getSession().getAttribute(beneficiaryId);
		}
		return null;
	}
	
	public void setTransaction(Transaction transaction){
		getSession().setAttribute(transaction.getId(), transaction);

	}


	public Transaction returnTransactionSessionVariable(String transactionId)
	{
		if (getSession().getAttribute(transactionId)!=null) 
		{	
			return (Transaction)getSession().getAttribute(transactionId);
		}
		return null;
	}
	
	public String getUserId(HttpServletRequest request) 
	{
		setRequest(request);
		String returnedUserId = this.returnSessionVariable("userId");
		return returnedUserId; // will be null if the attribute has not been set in the session
	}
	
	
}