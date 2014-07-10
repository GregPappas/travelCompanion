package com.lloydstsb.rest.common.formatting;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * Configures Jackson ObjectMapper.
 * 
 * @author secret
 */
public class JacksonContextResolver {

	protected ObjectMapper objectMapper;

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		this.objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.objectMapper.getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ"));
	}
}