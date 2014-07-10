package com.lloydstsb.rest.v1.exceptions;


import com.lloydstsb.ib.common.error.IbErrorCode;

public class EmailRequiredForPaperlessStatementException extends BadRequestException {
	private static final long serialVersionUID = 1L;

	public EmailRequiredForPaperlessStatementException(IbErrorCode code) {
		super(code);
	}
}
