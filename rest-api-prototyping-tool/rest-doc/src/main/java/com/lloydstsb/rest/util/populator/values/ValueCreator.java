package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

public interface ValueCreator<T> {

	/**
	 * 
	 * @param populator			The populator doing the populating
	 * @param clazz				The Class of this value
	 * @param annotations		Any annotations that would be applied to this value
	 * @param type				The Type of this value
	 * @param containingClazz	The class that this value will be contained in, if available
	 * @param valueField		The field that this value will be contained in, if available
	 * @return
	 * @throws Exception
	 */
	public T create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception;
}
