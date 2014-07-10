package com.lloydstsb.rest.common.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BeanFactoryTest {
	private BeanFactory<Object> beanFactory = new BeanFactory<Object>();
	private Object contained;
	
	@Before
	public void setUp() {
		contained = new Object();
		beanFactory.setObject(contained);
		beanFactory.setClazz(contained.getClass());
	}
	
	@Test
	public void shouldReturnObject() throws Exception {
		assertEquals(contained, beanFactory.getObject());
	}
	
	@Test
	public void shouldReturnObjectClass() throws Exception {
		assertEquals(contained.getClass(), beanFactory.getObjectType());
	}
	
	@Test
	public void shouldReturnASingleton() throws Exception {
		assertTrue(beanFactory.isSingleton());
	}
}
