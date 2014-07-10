package com.lloydstsb.rest.util.populator.values;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.lloydstsb.rest.util.populator.ObjectPopulator;

@SuppressWarnings("rawtypes")
public class ListCreator implements ValueCreator<List> {

	public List create(ObjectPopulator populator, Class<?> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) throws Exception {
		Class<?> listType = findGenericType(type, containingClazz);

		int items = 0;

		for (Annotation annotation : annotations) {
			if (annotation instanceof Size) {
				Size size = (Size) annotation;
				items = size.min();
			}
		}

		List<Object> output = new ArrayList<Object>();

		for (int i = 0; i < items; i++) {
			output.add(populator.createValue(listType));
		}

		return output;
	}

	private Class<?> findGenericType(Type type, Class<?> containingClazz) {
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

			if (actualTypeArguments[0] instanceof Class<?>) {
				return (Class<?>) actualTypeArguments[0];
			} else if (containingClazz != null) {
				if (actualTypeArguments[0] instanceof TypeVariable) {
					try {
						TypeVariable typeVariable = (TypeVariable) actualTypeArguments[0];

						// e.g. MyFoo<T> would result in typeName being T
						String typeName = typeVariable.getName();

						// e.g. MyFoo<T, A> would result in typeIndex being 0
						int typeIndex = findTypeIndex(typeName, containingClazz);

						if (typeIndex != -1) {
							// oh my.
							if(containingClazz.getGenericSuperclass() instanceof ParameterizedType) {
								return (Class<?>) ((ParameterizedType) containingClazz.getGenericSuperclass()).getActualTypeArguments()[typeIndex];
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					return findGenericType(containingClazz.getGenericSuperclass(), containingClazz.getSuperclass());
				}
			}
		}

		return Object.class;
	}

	private int findTypeIndex(String typeName, Class<?> containingClazz) {
		if (containingClazz == null) {
			return -1;
		}

		for (int i = 0; i < containingClazz.getTypeParameters().length; i++) {
			TypeVariable<?> typeVariable = containingClazz.getTypeParameters()[i];

			if (typeVariable.getName().equals(typeName)) {
				return i;
			}
		}

		return findTypeIndex(typeName, containingClazz.getSuperclass());
	}
}
