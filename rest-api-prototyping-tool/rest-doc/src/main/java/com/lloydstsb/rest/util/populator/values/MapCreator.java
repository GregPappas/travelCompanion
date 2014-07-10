package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

@SuppressWarnings("rawtypes")
public class MapCreator implements ValueCreator<Map> {

	public Map create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		Map<?, ?> output = new HashMap<Object, Object>();
		
		return output;
	}
}
