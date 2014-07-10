package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lloydstsb.rest.v1.data.ErrorHelper;
import com.lloydstsb.rest.v1.valueobjects.Error;


public class ErrorHelperTest {
	
	private ErrorHelper errorHelper = new ErrorHelper();
	
	
	@Test
	public void getPaymentsDisabledErrorShouldReturnErrorCode412() 
	{
		//act
			Error error = errorHelper.getPaymentsDisabledError();
		//verify
		assertEquals("412", error.getCode());
	}
	
	@Test
	public void getStatementExpiredErrorErrorShouldReturnErrorCode410() 
	{
		//act
			Error error = errorHelper.getStatementExpiredError();
		//verify
		assertEquals("410", error.getCode());
	}
	
	@Test
	public void getBeneficiaryDeclinedErrorShouldReturnErrorCode412() 
	{
		//act
			Error error = errorHelper.getBeneficiaryDeclinedError();
		//verify
		assertEquals("412", error.getCode());
	}

	@Test
	public void getTransferDeclinedOtherReasonErrorShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getTransferDeclinedOtherReasonError();
		//verify
		assertEquals("400", error.getCode());
	}
	
	@Test
	public void getPaymentDeclinedErrorShouldReturnErrorCode402() 
	{
		//act
			Error error = errorHelper.getPaymentDeclinedError();
		//verify
		assertEquals("402", error.getCode());
	}

	@Test
	public void getUnexpectedErrorShouldReturnErrorCode500() 
	{
		//act
			Error error = errorHelper.getUnexpectedError();
		//verify
		assertEquals("500", error.getCode());
	}

	@Test
	public void getForcedLogoutErrorShouldReturnErrorCode403() 
	{
		//act
			Error error = errorHelper.getForcedLogoutError();
		//verify
		assertEquals("403", error.getCode());
	}
	
	@Test
	public void getInvalidCredentialErrorShouldReturnErrorCode401() 
	{
		//act
			Error error = errorHelper.getInvalidCredentialError();
		//verify
		assertEquals("401", error.getCode());
	}
	
	@Test
	public void getUnauthorisedErrorShouldReturnErrorCode403() 
	{
		//act
			Error error = errorHelper.getUnauthorisedError();
		//verify
		assertEquals("403", error.getCode());
	}
	
	@Test
	public void getNotFoundShouldReturnErrorCode404() 
	{
		//act
			Error error = errorHelper.getNotFound();
		//verify
		assertEquals("404", error.getCode());
	}
	
	@Test
	public void getAmountExceedsBalanceErrorShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getAmountExceedsBalanceError();
		//verify
		assertEquals("400", error.getCode());
	}
	
	@Test
	public void getSameAccountsErrorShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getSameAccountsError();
		//verify
		assertEquals("400", error.getCode());
	}
	
	@Test
	public void getIsAMinimumTransferShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getIsAMinimumTransfer();
		//verify
		assertEquals("400", error.getCode());
	}
	
	@Test
	public void getBadRequestShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getBadRequest();
		//verify
		assertEquals("400", error.getCode());
	}
	
	@Test
	public void getDormantArrangementShouldReturnErrorCode423() 
	{
		//act
			Error error = errorHelper.getDormantArrangement();
		//verify
		assertEquals("423", error.getCode());
	}
	
	@Test
	public void getHeldForReviewShouldReturnErrorCode202() 
	{
		//act
			Error error = errorHelper.getHeldForReview();
		//verify
		assertEquals("202", error.getCode());
	}
	
	@Test
	public void getBadSortcodeShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getBadSortcode();
		//verify
		assertEquals("400", error.getCode());
	}
	

	@Test
	public void getProhibitiveIndicatorErrorShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getProhibitiveIndicatorError();
		//verify
		assertEquals("400", error.getCode());
	}
	
	@Test
	public void getExceedsReceivingLimitErrorShouldReturnErrorCode400() 
	{
		//act
			Error error = errorHelper.getExceedsReceivingLimitError();
		//verify
		assertEquals("400", error.getCode());
	}
	
	
}
