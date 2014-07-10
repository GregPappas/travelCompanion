package com.lloydstsb.rest.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.HttpMethod;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH {
	/**
     * HTTP PATCH method - http://tools.ietf.org/html/rfc5789
     */
    public static final String PATCH="PATCH";
}
