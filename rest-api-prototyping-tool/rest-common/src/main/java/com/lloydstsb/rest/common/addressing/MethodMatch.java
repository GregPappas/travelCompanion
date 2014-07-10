package com.lloydstsb.rest.common.addressing;

import java.lang.reflect.Method;

public class MethodMatch {
	private Class<?> containingClass;
	private Method method;
	private int matchedArguments;
	private int mandatoryArguments;
	private int matchedMandatoryArguments;
	private int totalArguments;
	private int regexMatches;

	public Class<?> getContainingClass() {
		return containingClass;
	}

	public void setContainingClass(Class<?> containingClass) {
		this.containingClass = containingClass;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public int getMatchedArguments() {
		return matchedArguments;
	}

	public void setMatchedArguments(int matchedArguments) {
		this.matchedArguments = matchedArguments;
	}

	public int getMandatoryArguments() {
		return mandatoryArguments;
	}

	public void setMandatoryArguments(int mandatoryArguments) {
		this.mandatoryArguments = mandatoryArguments;
	}

	public int getMatchedMandatoryArguments() {
		return matchedMandatoryArguments;
	}

	public void setMatchedMandatoryArguments(int matchedMandatoryArguments) {
		this.matchedMandatoryArguments = matchedMandatoryArguments;
	}

	public int getTotalArguments() {
		return totalArguments;
	}

	public void setTotalArguments(int totalArguments) {
		this.totalArguments = totalArguments;
	}

	public int getRegexMatches() {
		return regexMatches;
	}

	public void setRegexMatches(int regexMatches) {
		this.regexMatches = regexMatches;
	}
}
