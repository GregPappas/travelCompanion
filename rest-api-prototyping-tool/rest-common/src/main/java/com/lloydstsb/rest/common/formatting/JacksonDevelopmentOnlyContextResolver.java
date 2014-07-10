package com.lloydstsb.rest.common.formatting;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

/**
 * Configures Jackson ObjectMapper to pretty print output to make development a
 * little easier.
 * 
 * In production please use JacksonContextResolver superclass instead.
 * 
 * @author secret
 */
@Component
public class JacksonDevelopmentOnlyContextResolver extends JacksonContextResolver {

	public void setObjectMapper(ObjectMapper objectMapper) {
		super.setObjectMapper(objectMapper);

		this.objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}
}