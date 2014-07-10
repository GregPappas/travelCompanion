package com.lloydstsb.rest.common.mapping;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class AbstractMapperTest {
	AbstractMapper<DummyDomain, DummyView> mapper = new DummyMapper();

	@Test
	public void shouldMapNullListToNull() {
		List<DummyDomain> list = null;

		List<DummyView> mapped = mapper.map(list);

		assertEquals(null, mapped);
	}

	@Test
	public void shouldMapListToList() {
		List<DummyDomain> list = new LinkedList<DummyDomain>();
		list.add(new DummyDomain());
		list.add(new DummyDomain());
		list.add(new DummyDomain());

		List<DummyView> mapped = mapper.map(list);

		for (int i = 0; i < mapped.size(); i++) {
			assertEquals(mapped.get(i).getBar(), list.get(i).getFoo());
		}
	}

	@Test
	public void shouldMapNullArrayToNull() {
		DummyDomain[] list = null;

		List<DummyView> mapped = mapper.map(list);

		assertEquals(null, mapped);
	}

	@Test
	public void shouldMapArrayToList() {
		DummyDomain[] list = new DummyDomain[] {
				new DummyDomain(),
				new DummyDomain(),
				new DummyDomain()
		};
		
		List<DummyView> mapped = mapper.map(list);
		
		for(int i = 0; i < mapped.size(); i++) {
			assertEquals(mapped.get(i).getBar(), list[i].getFoo());
		}
	}

	private class DummyMapper extends AbstractMapper<DummyDomain, DummyView> {
		public DummyView map(DummyDomain source) {
			DummyView destination = new DummyView();
			destination.setBar(source.getFoo());

			return destination;
		}

	}

	private class DummyDomain {
		private String foo = UUID.randomUUID().toString();

		public String getFoo() {
			return foo;
		}

		@SuppressWarnings("unused")
		public void setFoo(String foo) {
			this.foo = foo;
		}
	}

	private class DummyView {
		private String bar;

		public String getBar() {
			return bar;
		}

		public void setBar(String bar) {
			this.bar = bar;
		}
	}
}
