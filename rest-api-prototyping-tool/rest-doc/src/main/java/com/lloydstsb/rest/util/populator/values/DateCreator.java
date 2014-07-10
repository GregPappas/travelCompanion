package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

public class DateCreator implements ValueCreator<Date> {
	private static final Date DATE = new Date();

	public Date create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		return DATE;
	}
}
