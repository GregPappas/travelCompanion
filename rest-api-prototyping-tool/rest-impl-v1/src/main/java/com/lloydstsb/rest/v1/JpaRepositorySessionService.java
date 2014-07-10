package com.lloydstsb.rest.v1;

import java.util.Arrays;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lloydstsb.rest.v1.delegate.CustomerDelegate;
import com.lloydstsb.rest.v1.domain.CustomerDomain;
import com.lloydstsb.rest.v1.exceptions.AuthorisationAttemptsExceededException;
import com.lloydstsb.rest.v1.exceptions.InvalidCredentialsException;
import com.lloydstsb.rest.v1.exceptions.NotLoggedInException;
import com.lloydstsb.rest.v1.mapping.CustomerMapper;
import com.lloydstsb.rest.v1.session.KeepAliveService;
import com.lloydstsb.rest.v1.session.LoginService;
import com.lloydstsb.rest.v1.session.LogoutService;
import com.lloydstsb.rest.v1.session.ReSubmitMemorableCharactersService;
import com.lloydstsb.rest.v1.session.RequestMemorableCharactersService;
import com.lloydstsb.rest.v1.session.SubmitMemorableCharactersService;
import com.lloydstsb.rest.v1.valueobjects.MemorableCharacters;

import static com.lloydstsb.rest.v1.exceptions.ImplIbErrorCode.errorDescCode;

@Service
public class JpaRepositorySessionService implements LoginService, LogoutService, RequestMemorableCharactersService, SubmitMemorableCharactersService, ReSubmitMemorableCharactersService, KeepAliveService {

	@Autowired
	private CustomerDelegate customerDelegate;

	@Autowired
	private JpaRepositoryCustomerService jpaRepositoryCustomerService;

	@Autowired
	private CustomerMapper customerMapper;

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	public void login(String customerId, String password) throws InvalidCredentialsException {
		CustomerDomain customer = customerDelegate.getCustomer(customerId);

		if (customer == null || !customer.getPassword().equals(password)) {
			throw new InvalidCredentialsException(errorDescCode("Invalid login credentials", "00"));
		}

		HttpSession session = request.getSession(true);
		session.setAttribute(SessionValues.UNAUTHENTICATED_CUSTOMER.toString(), customer.getCustomerId());
		session.setAttribute(SessionValues.LOGIN_ATTEMPT.toString(), 0);

		// replicate server behaviour
		response.addCookie(new Cookie("redirect", UUID.randomUUID().toString()));
	}

	public MemorableCharacters getMemorableCharacters(String aliasName) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			try {
				throw new InvalidCredentialsException(errorDescCode("No Session defined", "0"));
			} catch (InvalidCredentialsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String customerId = session.getAttribute(SessionValues.UNAUTHENTICATED_CUSTOMER.toString()).toString();
		CustomerDomain customer = customerDelegate.getCustomer(customerId);

		if (customer == null) {
			try {
				throw new NotLoggedInException(errorDescCode("No user in session", ""));
			} catch (NotLoggedInException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new MemorableCharacters(Arrays.asList(new Integer[] { 1, 2, 3 }));
	}

	public void enterMemorableCharacters(String characters) {
		HttpSession session = request.getSession(false);

		Integer loginAttempt = (Integer) session.getAttribute(SessionValues.LOGIN_ATTEMPT.toString());
		String customerId = session.getAttribute(SessionValues.UNAUTHENTICATED_CUSTOMER.toString()).toString();
		CustomerDomain customer = customerDelegate.getCustomer(customerId);

		if (loginAttempt != 0) {
			throw new IllegalArgumentException("Too many login attempts");
		}

		int i = 0;

		for (char c : characters.toCharArray()) {
			if (c != customer.getMemorableWord().charAt(i)) {
				session.setAttribute(SessionValues.LOGIN_ATTEMPT.toString(), loginAttempt + 1);

				throw new IllegalArgumentException("Invalid Login attempt");
			}

			i++;
		}

		session.removeAttribute(SessionValues.UNAUTHENTICATED_CUSTOMER.toString());
		session.setAttribute(SessionValues.AUTHENTICATED_CUSTOMER.toString(), customer.getCustomerId());
	}

	public void reenterMemorableCharacters(String password, String characters) throws AuthorisationAttemptsExceededException {
		HttpSession session = request.getSession(false);

		Integer loginAttempt = (Integer) session.getAttribute(SessionValues.LOGIN_ATTEMPT.toString());

		if (loginAttempt > 2) {
			throw new AuthorisationAttemptsExceededException(errorDescCode("Login attempts exceeded", ""));
		}

		String customerId = session.getAttribute(SessionValues.UNAUTHENTICATED_CUSTOMER.toString()).toString();
		CustomerDomain customer = customerDelegate.getCustomer(customerId);

		String[] chars = characters.split("");

		for (int i = 0; i < 3; i++) {
			if (chars[i].charAt(0) != customer.getMemorableWord().charAt(i)) {
				session.setAttribute(SessionValues.LOGIN_ATTEMPT.toString(), loginAttempt + 1);

				throw new IllegalArgumentException("Invalid Login attempt");
			}
		}

		session.removeAttribute(SessionValues.UNAUTHENTICATED_CUSTOMER.toString());
		session.setAttribute(SessionValues.AUTHENTICATED_CUSTOMER.toString(), customer.getCustomerId());
	}

	public void logout() {
		request.getSession().invalidate();
	}
	
	public void keepAlive(){
		
	}
}
