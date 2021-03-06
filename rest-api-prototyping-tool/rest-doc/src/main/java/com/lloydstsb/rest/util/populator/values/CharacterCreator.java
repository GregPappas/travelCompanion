package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

public class CharacterCreator implements ValueCreator<Character> {
	private static final Character CHARACTER = 'a';

	public Character create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		return CHARACTER;
	}
}
