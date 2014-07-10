package com.lloydstsb.rest.common.content;

import org.springframework.context.NoSuchMessageException;

public interface MessageSource {
	String getMessageDefault(String messageKey, String defaultMessage, Object ...args);
	String getMessage(String messageKey, Object ...args) throws NoSuchMessageException;
}
