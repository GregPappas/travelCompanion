package com.lloydstsb.rest.v1.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 

public class BeanFactory {
	
	private static final String BEANS_FILE_NAME = "beans.xml";
	private ApplicationContext context;
	
	public BeanFactory() {
		super();
		this.context = new ClassPathXmlApplicationContext(BEANS_FILE_NAME);
	}

	public BeanFactory(String beansFileName) {
		super();
		this.context = new ClassPathXmlApplicationContext(beansFileName);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName, Class<T> target){
		return (T) context.getBean(beanName);
	}
}
