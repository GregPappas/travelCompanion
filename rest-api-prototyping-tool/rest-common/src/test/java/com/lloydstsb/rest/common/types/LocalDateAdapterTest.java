package com.lloydstsb.rest.common.types;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalDateAdapterTest {
	private LocalDateAdapter adapter = new LocalDateAdapter();

	@Test
	public void shouldUnmarshalNullStringToNull() throws Exception {
		assertEquals(null, adapter.unmarshal(null));
	}

	@Test
	public void shouldUnmarshalStringToYearMonth() throws Exception {
		String dateString = "2012-11-15";

		LocalDate date = adapter.unmarshal(dateString);

		assertEquals(2012, date.getYear());
		assertEquals(11, date.getMonthOfYear());
		assertEquals(15, date.getDayOfMonth());
	}

	@Test
	public void shouldMarshalNullYearMonthToNull() throws Exception {
		assertEquals(null, adapter.marshal(null));
	}

	@Test
	public void shouldMarshalYearMonthToString() throws Exception {
		String dateString = "2012-11-15";
		LocalDate date = adapter.unmarshal(dateString);

		String marshalledDateString = adapter.marshal(date);

		assertEquals(dateString, marshalledDateString);
	}
}
