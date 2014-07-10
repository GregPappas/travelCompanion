package com.lloydstsb.rest.common.types;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeAdapter extends XmlAdapter<String, DateTime> {
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Override
	public DateTime unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}
 
		return DateTime.parse(v, formatter);
	}

	@Override
	public String marshal(DateTime v) throws Exception {
		if (v == null) {
			return null;
		}

		return v.toString(formatter);
	}
}
