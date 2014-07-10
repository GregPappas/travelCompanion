package com.lloydstsb.rest.common.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Path;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@RunWith(MockitoJUnitRunner.class)
public class ServiceBeanListerTest {
	private ServiceBeanLister serviceBeanLister = new ServiceBeanLister();

	@Mock
	private ApplicationContext applicationContext;

	@Test
	public void shouldBeAListFactory() {
		assertEquals(List.class, serviceBeanLister.getObjectType());
	}

	@Test
	public void shouldReturnASingleton() throws Exception {
		assertTrue(serviceBeanLister.isSingleton());
	}
	
	@Test
	public void shouldContainPathAnnotatedBeans() throws Exception {
		List<Object> serviceBeans = new LinkedList<Object>();
		serviceBeans.add(new ClassAnnotatedWithServiceAndPath());
		serviceBeans.add(new ClassAnnotatedWithServiceAndMethodAnnotatedWithPath());
		serviceBeans.add(new ClassThatImplementsPathAnnotatedInterface());
		serviceBeans.add(new ClassThatImplementsInterfaceWithPathAnnotatedMethod());
		serviceBeans.add(new ClassThatImplementsServiceAnnotatedInterfaceAndHasPathAnnotatedMethod());
		
		List<Object> nonServiceBeans = new LinkedList<Object>();
		nonServiceBeans.add(new UnAnnotatedClass());
		nonServiceBeans.add(new ClassAnnotatedWithService());
		nonServiceBeans.add(new ClassThatImplementsServiceAnnotatedInterface());
		
		Map<String, Object> beans = new HashMap<String, Object>();
		
		for(Object bean : serviceBeans) {
			beans.put(UUID.randomUUID().toString(), bean);
		}
		
		for(Object bean : nonServiceBeans) {
			beans.put(UUID.randomUUID().toString(), bean);
		}
		
		when(applicationContext.getBeansWithAnnotation(eq(Service.class))).thenReturn(beans);
		
		serviceBeanLister.setApplicationContext(applicationContext);
		
		List<?> resourceBeans = serviceBeanLister.getObject();
		
		for(Object bean : serviceBeans) {
			assertTrue(resourceBeans.contains(bean));
		}
		
		for(Object bean : nonServiceBeans) {
			assertFalse(resourceBeans.contains(bean));
		}
	}
	
	private class UnAnnotatedClass {
		
	}
	
	@Service
	private class ClassAnnotatedWithService {
		
	}
	
	@Service
	@Path("/foo")
	private class ClassAnnotatedWithServiceAndPath {
		
	}
	
	@Service
	private class ClassAnnotatedWithServiceAndMethodAnnotatedWithPath {
		@Path("/foo")
		public void doBaz() {}
	}
	
	@Service
	private class ClassThatImplementsPathAnnotatedInterface implements InterfaceAnnotatedWithPath {
		
	}
	
	@Service
	private class ClassThatImplementsInterfaceWithPathAnnotatedMethod implements InterfaceWithPathAnnotatedMethod {
		public void doBaz() {}
	}
	
	private class ClassThatImplementsServiceAnnotatedInterfaceAndHasPathAnnotatedMethod implements InterfaceWithServiceAnnotation {
		@Path("/baz")
		public void doBaz() {}
	}
	
	private class ClassThatImplementsServiceAnnotatedInterface implements InterfaceWithServiceAnnotation {
		
	}
	
	@Path("/bar")
	private interface InterfaceAnnotatedWithPath {
		
	}
	
	private interface InterfaceWithPathAnnotatedMethod {
		@Path("/baz")
		public void doBaz();
	}
	
	@Service
	private interface InterfaceWithServiceAnnotation {
		
	}
}
