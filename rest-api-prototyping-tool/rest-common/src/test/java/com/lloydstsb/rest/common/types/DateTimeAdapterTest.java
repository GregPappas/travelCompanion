package com.lloydstsb.rest.common.types;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateTimeAdapterTest {
	private DateTimeAdapter adapter = new DateTimeAdapter();

	@Test
	public void shouldUnmarshalNullStringToNull() throws Exception {
		assertEquals(null, adapter.unmarshal(null));
	}

	@Test
	public void shouldUnmarshalStringToYearMonth() throws Exception {
		String dateString = "2012-11-15T12:03:07Z";

		DateTime date = adapter.unmarshal(dateString);

		assertEquals(2012, date.getYear());
		assertEquals(11, date.getMonthOfYear());
		assertEquals(15, date.getDayOfMonth());
		assertEquals(12, date.getHourOfDay());
		assertEquals(3, date.getMinuteOfHour());
		assertEquals(7, date.getSecondOfMinute());
	}

	@Test
	public void shouldMarshalNullYearMonthToNull() throws Exception {
		assertEquals(null, adapter.marshal(null));
	}

	@Test
	public void shouldMarshalYearMonthToString() throws Exception {
		String dateString = "2012-11-15T12:00:00Z";
		DateTime date = adapter.unmarshal(dateString);

		String marshalledDateString = adapter.marshal(date);

		assertEquals(dateString, marshalledDateString);
	}
}
