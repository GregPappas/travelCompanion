package com.lloydstsb.rest.v1.valueobjects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PageTest {

	@Test
	public void testConstructor() {
		List<String> strings = new ArrayList<String>();
		strings.add("first");
		strings.add("second");
		strings.add("third");
		Page<String> page = new Page<String>(strings, 0, 1);
		assertEquals(page.getItems().size(), 1);
		assertEquals("first", page.getItems().get(0));
		assertEquals(new Integer(0), page.getPage());
		assertEquals(new Integer(1), page.getSize());
		assertEquals(new Integer(3), page.getTotal());
	}

	@Test
	public void testConstructorWithSecondPage() {
		List<String> strings = new ArrayList<String>();
		strings.add("first");
		strings.add("second");
		strings.add("third");
		strings.add("fourth");
		Page<String> page = new Page<String>(strings, 1, 2);
		assertEquals(2, page.getItems().size());
		assertEquals("third", page.getItems().get(0));
		assertEquals("fourth", page.getItems().get(1));
		assertEquals(Integer.valueOf(2), page.getSize());
		assertEquals(Integer.valueOf(1), page.getPage());
		assertEquals(Integer.valueOf(4), page.getTotal());
	}

	@Test
	public void testWithNullList() {
		List<String> strings = null;
		Page<String> page = new Page<String>(strings, 1, 2);
		assertNull(page.getItems());
		assertEquals(Integer.valueOf(2), page.getSize());
		assertEquals(Integer.valueOf(1), page.getPage());
		assertEquals(Integer.valueOf(0), page.getTotal());
	}

	@Test
	public void testIndexOutOfBounds() {
		List<String> strings = new ArrayList<String>();
		strings.add("first");
		Page<String> page = new Page<String>(strings, 1, 2);
		assertNull(page.getItems());
		assertEquals(Integer.valueOf(2), page.getSize());
		assertEquals(Integer.valueOf(1), page.getPage());
		assertEquals(Integer.valueOf(1), page.getTotal());
	}
}
