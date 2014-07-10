package com.lloydstsb.rest.documentation.v1;

import org.springframework.stereotype.Service;

import com.lloydstsb.rest.util.populator.ObjectPopulator;
import com.lloydstsb.rest.v1.exceptions.InvalidLightLoginTokenExchangeException;
import com.lloydstsb.rest.v1.exceptions.ThrottleCookieException;
import com.lloydstsb.rest.v1.session.LoginService;
import com.lloydstsb.rest.v1.session.LogoutService;


@Service
public class SessionDocumentation implements LoginService, LogoutService/*, RequestMemorableCharactersService, SubmitMemorableCharactersService, ReSubmitMemorableCharactersService, KeepAliveService, GetLightLoginTokenService, VerifyLightLoginTokenService*/ {

	private ObjectPopulator objectPopulator = new ObjectPopulator();

	public void login(String username, String password) {

	}

	public void logout() {

	}

	public void enterMemorableCharacters(String characters) {
	}

	public void reenterMemorableCharacters(String password, String characters) {
	}

	public void keepAlive() {
	}
	
	public void getLightLoginToken(String authMessage) {
	}

	public void verifyLightLoginToken(String authMessage) throws InvalidLightLoginTokenExchangeException, ThrottleCookieException {
	}
}