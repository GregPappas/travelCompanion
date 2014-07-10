package com.lloydstsb.rest.v1.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bank_bankuser")
public class CustomerDomain extends BaseDomain {

	@Id
	@Column(name="userId")
	private String customerId;
	
	@Column(name="password")
	private String password;
	
	@Column(name="prefixTitle")
	private String title;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;

	@Column(name="mi")
	private String memorableWord; 
	
	@Column(name="showTsandCs")
	private boolean showTermsAndConditions;
	
	@Column(name="showWelcomeScreen")
	private boolean showWelcomeScreen;
	
	@Column(name="loginAttemptsLeft")
	private Integer loginAttemptsLeft;
	
	@Column(name="miAttemptsLeft")
	private Integer miAttemptsLeft;
	
	@Column(name="dormant")
	private boolean dormant;
	
	@Column(name="email")
	private String email;

	@OneToMany(fetch=FetchType.EAGER, mappedBy="customer")
	private Set<ArrangementDomain> arrangements = new HashSet<ArrangementDomain>();

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMemorableWord() {
		return memorableWord;
	}

	public void setMemorableWord(String memorableWord) {
		this.memorableWord = memorableWord;
	}

	public Integer getLoginAttemptsLeft() {
		return loginAttemptsLeft;
	}

	public void setLoginAttemptsLeft(Integer loginAttemptsLeft) {
		this.loginAttemptsLeft = loginAttemptsLeft;
	}

	public Integer getMiAttemptsLeft() {
		return miAttemptsLeft;
	}

	public void setMiAttemptsLeft(Integer miAttemptsLeft) {
		this.miAttemptsLeft = miAttemptsLeft;
	}

	public Set<ArrangementDomain> getArrangements() {
		return arrangements;
	}

	public void setArrangements(Set<ArrangementDomain> arrangements) {
		this.arrangements = arrangements;
	}

	/**
	 * @return the showTermsAndConditions
	 */
	public boolean isShowTermsAndConditions() {
		return showTermsAndConditions;
	}

	/**
	 * @param showTermsAndConditions the showTermsAndConditions to set
	 */
	public void setShowTermsAndConditions(boolean showTermsAndConditions) {
		this.showTermsAndConditions = showTermsAndConditions;
	}

	/**
	 * @return the showWelcomeScreen
	 */
	public boolean isShowWelcomeScreen() {
		return showWelcomeScreen;
	}

	/**
	 * @param showWelcomeScreen the showWelcomeScreen to set
	 */
	public void setShowWelcomeScreen(boolean showWelcomeScreen) {
		this.showWelcomeScreen = showWelcomeScreen;
	}

	/**
	 * @return the dormant
	 */
	public boolean isDormant() {
		return dormant;
	}

	/**
	 * @param dormant the dormant to set
	 */
	public void setDormant(boolean dormant) {
		this.dormant = dormant;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
