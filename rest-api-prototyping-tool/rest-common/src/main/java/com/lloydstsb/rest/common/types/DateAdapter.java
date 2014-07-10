package com.lloydstsb.rest.common.types;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	@Override
	public Date unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}

		Calendar calendar = DatatypeConverter.parseDateTime(v);

		return new Date(calendar.getTimeInMillis());
	}

	@Override
	public String marshal(Date v) throws Exception {
		if (v == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(v);

		return DatatypeConverter.printDateTime(calendar);
	}
}
