package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

@SuppressWarnings("rawtypes")
public class SetCreator implements ValueCreator<Set> {

	public Set create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		Set<?> output = new HashSet<Object>();

		return output;
	}
}
