/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.HashMap;
import java.util.Map;

public enum TransactionType {
//	BANK_GIRO_CREDIT,
//	BILL_PAYMENT,
//	CARD,
//	CHARGE,
//	CHEQUE,
//	COMMISSION,
//	CORRECTION,
//	CASHPOINT,
//	CASH,
//	CASH_OR_CHEQUE,
	DIRECT_DEBIT,
	DEBIT_CARD,
	DEPOSIT,
//	DIVIDEND,
//	OVERDRAWN_BALANCE,
//	EURO_CHEQUE,
//	FASTER_PAYMENTS_INCOMING,
//	FASTER_PAYMENTS_OUTGOING,
//	FEE,
//	INTEREST,
//	INTERNET_BANKING_TRANSFER,
//	MOBILE_TOP_UP,
	PAYMENT,
//	PAYSAVE,
//	SALARY,
//	STANDING_ORDER,
	TRANSFER;
//	WITHDRAWAL;

	private static Map<String, TransactionType> types;

	static {
		types = new HashMap<String, TransactionType>();
//		types.put("BGC", BANK_GIRO_CREDIT);
//		types.put("BP", BILL_PAYMENT);
//		types.put("CD", CARD);
//		types.put("CHG", CHARGE);
//		types.put("CHQ", CHEQUE);
//		types.put("COMM", COMMISSION);
//		types.put("COR", CORRECTION);
//		types.put("CPT", CASHPOINT);
//		types.put("CSH", CASH);
//		types.put("CSQ", CASH_OR_CHEQUE);
		types.put("DD", DIRECT_DEBIT);
		types.put("DEB", DEBIT_CARD);
		types.put("DEP", DEPOSIT);
//		types.put("DIV", DIVIDEND);
//		types.put("DR", OVERDRAWN_BALANCE);
//		types.put("EUR", EURO_CHEQUE);
//		types.put("FPI", FASTER_PAYMENTS_INCOMING);
//		types.put("FPO", FASTER_PAYMENTS_OUTGOING);
//		types.put("FEE", FEE);
//		types.put("INT", INTEREST);
//		types.put("IB", INTERNET_BANKING_TRANSFER);
//		types.put("MTU", MOBILE_TOP_UP);
		types.put("PAY", PAYMENT);
//		types.put("PSV", PAYSAVE);
//		types.put("SAL", SALARY);
//		types.put("SO", STANDING_ORDER);
		types.put("TFR", TRANSFER);
//		types.put("WIT", WITHDRAWAL);
	}

	public static TransactionType from(String string) {
		return types.get(string);
	}
}
