package com.lloydstsb.rest.util.populator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ObjectPopulatorTest {
	private ObjectPopulator objectPopulator;

	@Before
	public void setUp() throws Exception {
		objectPopulator = new ObjectPopulator();
	}

	@Test
	public void testGetFieldNameFromMethodName() {
		assertEquals("foo", objectPopulator.getFieldNameFromMethodName("setFoo"));
		assertEquals("foo", objectPopulator.getFieldNameFromMethodName("isFoo"));
	}

	@Test
	public void shouldPopulateListWhenAbstractSuperclassContainsTypeInfo() {
		Bar bar = objectPopulator.populate(Bar.class);

		// did we respect the @Size annotation?
		assertEquals(1, bar.getThings().size());
		assertEquals(1, bar.getOtherThings().size());
		
		// did we guess the correct types?
		assertTrue(bar.getThings().get(0) instanceof String);
		assertTrue(bar.getOtherThings().get(0) instanceof Integer);
	}
}
