package com.lloydstsb.rest.v1.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseDomain {

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
