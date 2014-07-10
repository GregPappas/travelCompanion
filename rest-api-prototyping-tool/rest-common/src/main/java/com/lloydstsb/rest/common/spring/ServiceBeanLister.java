package com.lloydstsb.rest.common.spring;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Path;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

public class ServiceBeanLister implements ApplicationContextAware, FactoryBean<List<?>> {
	private List<Object> services = new LinkedList<Object>();

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		for (Object service : applicationContext.getBeansWithAnnotation(Service.class).values()) {
			if (isJaxRsService(service.getClass())) {
				services.add(service);
			}

			for (Class<?> clazz : service.getClass().getInterfaces()) {
				if (isJaxRsService(clazz)) {
					services.add(service);
				}
			}
		}
	}

	private boolean isJaxRsService(Class<?> clazz) {
		if (clazz.getAnnotation(Path.class) != null) {
			return true;
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getAnnotation(Path.class) != null) {
				return true;
			}
		}

		return false;
	}

	public List<?> getObject() throws Exception {
		return services;
	}

	public Class<?> getObjectType() {
		return List.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
