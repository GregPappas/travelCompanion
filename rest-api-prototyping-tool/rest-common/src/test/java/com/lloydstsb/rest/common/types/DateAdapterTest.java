package com.lloydstsb.rest.common.types;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateAdapterTest {
	private DateAdapter adapter = new DateAdapter();

	@Test
	public void shouldUnmarshalNullStringToNull() throws Exception {
		assertEquals(null, adapter.unmarshal(null));
	}

	@Test
	public void shouldUnmarshalStringToDate() throws Exception {
		String dateString = "2012-11-02T19:52:54.834Z";

		Date date = adapter.unmarshal(dateString);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		assertEquals(2012, calendar.get(Calendar.YEAR));
		assertEquals(Calendar.NOVEMBER, calendar.get(Calendar.MONTH));
		assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(19, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(52, calendar.get(Calendar.MINUTE));
		assertEquals(54, calendar.get(Calendar.SECOND));
	}

	@Test
	public void shouldMarshalNullDateToNull() throws Exception {
		assertEquals(null, adapter.marshal(null));
	}

	@Test
	public void shouldMarshalDateToString() throws Exception {
		String dateString = "2012-11-02T19:52:54.834Z";
		Date date = adapter.unmarshal(dateString);

		String marshalledDateString = adapter.marshal(date);

		assertEquals(dateString, marshalledDateString);
	}
}
