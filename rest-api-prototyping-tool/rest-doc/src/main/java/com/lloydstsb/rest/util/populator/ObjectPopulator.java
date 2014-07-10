package com.lloydstsb.rest.util.populator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lloydstsb.rest.util.populator.values.BigDecimalCreator;
import org.apache.commons.lang.ArrayUtils;

import com.lloydstsb.rest.util.populator.values.BigIntegerCreator;
import com.lloydstsb.rest.util.populator.values.BooleanCreator;
import com.lloydstsb.rest.util.populator.values.CharacterCreator;
import com.lloydstsb.rest.util.populator.values.DateCreator;
import com.lloydstsb.rest.util.populator.values.IntegerCreator;
import com.lloydstsb.rest.util.populator.values.ListCreator;
import com.lloydstsb.rest.util.populator.values.LongCreator;
import com.lloydstsb.rest.util.populator.values.MapCreator;
import com.lloydstsb.rest.util.populator.values.SetCreator;
import com.lloydstsb.rest.util.populator.values.StringCreator;
import com.lloydstsb.rest.util.populator.values.ValueCreator;

public class ObjectPopulator {

	private static final String SET = "set";

	private Map<Class<?>, ValueCreator<?>> valueCreators;

	public ObjectPopulator() {
		valueCreators = new HashMap<Class<?>, ValueCreator<?>>();

		addValueCreator(new CharacterCreator());
		addValueCreator(new DateCreator());
		addValueCreator(new IntegerCreator());
		addValueCreator(new LongCreator());
		addValueCreator(new BooleanCreator());
		addValueCreator(new StringCreator());
		addValueCreator(new ListCreator());
		addValueCreator(new MapCreator());
		addValueCreator(new SetCreator());
		addValueCreator(new BigIntegerCreator());
		addValueCreator(new BigDecimalCreator());
	}

	private void addValueCreator(ValueCreator<?> valueCreator) {
		ParameterizedType parameterizedType = (ParameterizedType) (valueCreator.getClass().getGenericInterfaces()[0]);
		Type type = parameterizedType.getActualTypeArguments()[0];

		valueCreators.put((Class<?>) type, valueCreator);
	}

	public <T> T populate(Class<T> clazz) {
		return populate(clazz, new Annotation[] {}, clazz.getGenericSuperclass());
	}

	public <T> T populate(Class<T> clazz, Annotation[] annotations, Type type) {
		return populate(clazz, annotations, type, null);
	}

	public <T> T populate(Class<T> clazz, Annotation[] annotations, Type type, Class<?> containingClazz) {
		return populate(clazz, annotations, type, containingClazz, null);
	}

	public <T> T populate(Class<T> clazz, Annotation[] annotations, Type type, Class<?> containingClazz, Field valueField) {
		try {
			T value = createValue(clazz, annotations, type, containingClazz, valueField);

			// we were passed a String, Integer, List, etc
			if (value != null) {
				return value;
			}
			
			T instance = clazz.newInstance();
			populate(instance);

			return instance;
		} catch (InstantiationException e) {
			throw new RuntimeException("Could not instantiate " + clazz, e);
		} catch (Exception e) {
			throw new RuntimeException("Error creating and populating new instance of " + clazz.getCanonicalName(), e);
		}
	}

	public Object populate(Object object) throws Exception {
		Class<? extends Object> clazz = object.getClass();
		Method[] allMethods = clazz.getMethods();
		List<Method> setters = new ArrayList<Method>();

		for (Method method : allMethods) {
			if (method.getName().startsWith(SET)) {
				setters.add(method);

				Class<?> parameterType = method.getParameterTypes()[0];
				Annotation[] annotations = method.getParameterAnnotations()[0];
				Type type = method.getGenericParameterTypes()[0];

				// if our object is a POJO, check the field name for extra
				// annotations
				String fieldName = getFieldNameFromMethodName(method.getName());
				Field field = findField(fieldName, clazz);

				if (field != null && field.getAnnotations() != null) {
					annotations = (Annotation[]) ArrayUtils.addAll(annotations, field.getAnnotations());
				}

				Object value = createValue(parameterType, annotations, type, clazz, field);

				if (value != null) {
					method.invoke(object, value);
				} else {
					try {
						Object child = populate(parameterType, annotations, type);
						method.invoke(object, child);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return object;
	}

	protected String getFieldNameFromMethodName(String methodName) {
		if (methodName.startsWith("is")) {
			methodName = methodName.substring(2);
		}

		if (methodName.startsWith("set")) {
			methodName = methodName.substring(3);
		}

		String firstCharacter = methodName.substring(0, 1);

		if (firstCharacter.toUpperCase().equals(firstCharacter)) {
			methodName = firstCharacter.toLowerCase() + methodName.substring(1);
		}

		return methodName;
	}

	public <T> T createValue(Class<T> valueClass) throws Exception {
		return createValue(valueClass, new Annotation[] {}, null, null, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T createValue(Class<T> valueClass, Annotation[] annotations, Type type, Class<?> containingClass, Field valueField) throws Exception {
		if(valueClass.isAssignableFrom(boolean.class)) {
			return (T)new Boolean(true);
		}
		
		if(valueClass.isEnum()) {
			return (T)valueClass.getEnumConstants()[0];
		}
		
		for (Class<?> clazz : valueCreators.keySet()) {
			if (!valueClass.isAssignableFrom(clazz)) {
				continue;
			}

			return (T) valueCreators.get(clazz).create(this, valueClass, annotations, type, containingClass, valueField);
		}

		return null;
	}

	/**
	 * Climbs the inheritance ladder until we find a field with the specified
	 * name
	 * 
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	private Field findField(String fieldName, Class<?> clazz) {
		while (clazz != null) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {

			}

			clazz = clazz.getSuperclass();
		}

		return null;
	}
}
