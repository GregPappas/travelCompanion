package com.lloydstsb.rest.common.spring;

import org.springframework.beans.factory.FactoryBean;

public class BeanFactory<C> implements FactoryBean<C> {
	private C object;
	private Class<?> clazz;

	public C getObject() throws Exception {
		return object;
	}

	public Class<?> getObjectType() {
		return clazz;
	}
	
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setObject(C object) {
		this.object = object;
	}

	public boolean isSingleton() {
		return true;
	}
}
