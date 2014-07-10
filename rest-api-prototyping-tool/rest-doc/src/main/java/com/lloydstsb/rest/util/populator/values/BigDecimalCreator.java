package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

public class BigDecimalCreator implements ValueCreator<BigDecimal> {

	public BigDecimal create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		return new BigDecimal("123.45");
	}
}
