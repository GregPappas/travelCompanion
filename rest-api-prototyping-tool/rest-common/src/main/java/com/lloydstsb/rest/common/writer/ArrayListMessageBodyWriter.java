package com.lloydstsb.rest.common.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
@Produces("application/xml")
public class ArrayListMessageBodyWriter implements MessageBodyWriter<List<?>> {
	private Map<Class<?>, String> typeNameOverrides;
	private Map<Class<?>, String> typeWrapperNameOverrides;

	public long getSize(List<?> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (genericType instanceof ParameterizedType) {
			return true;
		}

		return false;
	}

	public void writeTo(List<?> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		String typeName = "";

		if (genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			Type[] typeArray = parameterizedType.getActualTypeArguments();
			Type listType = typeArray[0];
			Class<?> cType = (Class<?>) listType;
			typeName = getSimpleTypeWrapperName(cType);
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(entityStream));
		String ts = "<" + typeName + ">";

		for (Object s : t) {
			ts += "<" + getSimpleTypeName(s.getClass()) + ">" + s.toString() + "</" + getSimpleTypeName(s.getClass()) + ">";
		}

		ts += "</" + typeName + ">";

		bw.write(ts);
		bw.flush();
	}

	protected String getSimpleTypeName(Class<?> c) {
		if (typeNameOverrides != null && typeNameOverrides.containsKey(c)) {
			return typeNameOverrides.get(c);
		}

		String name = c.getSimpleName();
		name = name.toLowerCase();

		return name;
	}

	protected String getSimpleTypeWrapperName(Class<?> c) {
		if (typeWrapperNameOverrides != null && typeWrapperNameOverrides.containsKey(c)) {
			return typeWrapperNameOverrides.get(c);
		}

		String name = c.getSimpleName();
		name = name.toLowerCase();

		if (name.endsWith("y")) {
			name = name.substring(0, name.length() - 1) + "ies";
		} else {
			name += "s";
		}

		return name;
	}

	public void setTypeNameOverrides(Map<Class<?>, String> typeNameOverrides) {
		this.typeNameOverrides = typeNameOverrides;
	}

	public void setTypeWrapperNameOverrides(Map<Class<?>, String> typeWrapperNameOverrides) {
		this.typeWrapperNameOverrides = typeWrapperNameOverrides;
	}
}