package com.lloydstsb.rest.v1;

import org.springframework.stereotype.Service;

import com.lloydstsb.rest.v1.authentication.GetPhoneAuthenticationStatusService;
import com.lloydstsb.rest.v1.authentication.InitiatePhoneAuthenticationService;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.valueobjects.PhoneAuthentication;
import com.lloydstsb.rest.v1.valueobjects.PhoneAuthenticationStatus;

@Service
public class JpaRepositoryAuthenticationService implements GetPhoneAuthenticationStatusService, InitiatePhoneAuthenticationService {

	public PhoneAuthentication initiatePhoneAuthentication(String transactionId, String phoneNumberType)
			throws NotFoundException {
		
		PhoneAuthentication pa = new PhoneAuthentication();
		pa.setStatus(PhoneAuthenticationStatus.CALLINITIATED);
		return pa;
	}
	public PhoneAuthentication getPhoneAuthenticationStatus(String transactionId)
			throws NotFoundException {
		PhoneAuthentication pa = new PhoneAuthentication();
		pa.setStatus(PhoneAuthenticationStatus.CALLSUCCESS);
		return pa;
	}	
}