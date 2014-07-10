package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigInteger;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

public class BigIntegerCreator implements ValueCreator<BigInteger> {

	public BigInteger create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		return new BigInteger("100.0");
	}
}