package com.lloydstsb.rest.v1.data;


import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.Error.ErrorCategory;

public class ErrorHelper 
{
	public static final String FORCED_LOGOUT_MESSAGE = "Forced Logout";
	public static final String NOT_FOUND_MESSAGE = "Not Found";
	public static final String BAD_REQUEST_MESSAGE = "Bad Request";
	public static final String INVALID_CREDENTIAL_MESSAGE ="Invalid Credentials";
	public static final String AMOUNT_EXCEEDS_BALANCE_MESSAGE = "Ammount exceeds balance";
	public static final String SAME_ACCOUNTS= "Same accounts";
	public static final String IS_A_MINIMUM_TRANSFER= "Is a minimum transfer";
	public static final String UNEXPECTED_ERROR = "Unexpected error encountered";
	public static final String PAYMENT_DECLINED = "Payment Declined";
	private static final String DORMANT_ARRANGEMENT = "Dormant Arrangement";
	private static final String HELD_FOR_REVIEW = "Payment held for review";
	private static final String PAYMENTS_DISABLED= "Payments disabled";
	private static final String TRANSFER_DECLINED_OTHER_REASON = "Transfer declined other reason";
	private static final String BENEFICIARY_DECLINED = "Beneficiary declined";
	private static final String STATEMENT_EXPIRED = "Statement expired";
	private static final String BAD_SORTCODE= "Bad sortcode";
	private static final String UNAUTHORISED_MESSAGE = "Unauthorised";
	private static final String PROHIBITIVE_INDICATOR = "Prohibitive indicator";
	private static final String EXCEEDS_RECEIVING_LIMIT = "Exceeds receiving limit";
	
	public Error getPaymentsDisabledError()
	{
		Error error = new Error("412",PAYMENTS_DISABLED,Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	
	public Error getStatementExpiredError()
	{
		Error error = new Error("410",STATEMENT_EXPIRED,Error.ErrorCategory.BAD_REQUEST);
		return error;
	}

	public Error getBeneficiaryDeclinedError()
	{
		Error error = new Error("412",BENEFICIARY_DECLINED,Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	
	public Error getTransferDeclinedOtherReasonError()
	{
		Error error = new Error("400",TRANSFER_DECLINED_OTHER_REASON,Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	public Error getPaymentDeclinedError()
	{
		Error error = new Error("402",PAYMENT_DECLINED,Error.ErrorCategory.FAILED_OUTCOME);
		return error;
	}
	public Error getUnexpectedError()
	{
		Error error = new Error("500",UNEXPECTED_ERROR,Error.ErrorCategory.FAILED_OUTCOME);
		return error;
	}
	public Error getForcedLogoutError()
	{
		Error error = new Error("403",FORCED_LOGOUT_MESSAGE,Error.ErrorCategory.FORCED_LOGOUT);
		return error;
	}
	
	public Error getInvalidCredentialError()
	{
		Error error = new Error("401",INVALID_CREDENTIAL_MESSAGE,Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	
	public Error getUnauthorisedError()
	{
		Error error = new Error("403", UNAUTHORISED_MESSAGE,Error.ErrorCategory.FORCED_LOGOUT);
		return error;
	}
	
	public Error getNotFound()
	{
		Error error = new Error ("404", NOT_FOUND_MESSAGE, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	public Error getAmountExceedsBalanceError()
	{
		Error error = new Error ("400", AMOUNT_EXCEEDS_BALANCE_MESSAGE, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	public Error getSameAccountsError()
	{
		Error error = new Error ("400", SAME_ACCOUNTS, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}

	public Error getIsAMinimumTransfer() 
	{
		Error error = new Error ("400", IS_A_MINIMUM_TRANSFER, ErrorCategory.BAD_REQUEST);
		return error;
	}
	
	public Error getBadRequest()
	{
		Error error = new Error ("400", BAD_REQUEST_MESSAGE, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	public Error getDormantArrangement() {
		Error error = new Error ("423", DORMANT_ARRANGEMENT, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	
	public Error getHeldForReview() {
		Error error = new Error ("202", HELD_FOR_REVIEW, Error.ErrorCategory.FAILED_OUTCOME);
		return error;
	}

	public Error getBadSortcode() {
		Error error = new Error ("400", BAD_SORTCODE, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
	
	public Error getProhibitiveIndicatorError()
	{
		Error error = new Error("400", PROHIBITIVE_INDICATOR, Error.ErrorCategory.BAD_REQUEST);
		return error;
	}

	public Error getExceedsReceivingLimitError() 
	{
		Error error = new Error("400",EXCEEDS_RECEIVING_LIMIT,Error.ErrorCategory.BAD_REQUEST);
		return error;
	}
}
