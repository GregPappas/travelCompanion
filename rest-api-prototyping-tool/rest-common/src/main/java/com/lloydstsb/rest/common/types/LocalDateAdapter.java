package com.lloydstsb.rest.common.types;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}
 
		return LocalDate.parse(v, formatter);
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		if (v == null) {
			return null;
		}

		return v.toString(formatter);
	}
}
