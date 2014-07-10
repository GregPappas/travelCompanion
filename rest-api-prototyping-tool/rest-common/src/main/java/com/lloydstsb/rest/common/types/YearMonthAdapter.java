package com.lloydstsb.rest.common.types;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");

	@Override
	public YearMonth unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}

		return YearMonth.parse(v, formatter);
	}

	@Override
	public String marshal(YearMonth v) throws Exception {
		if (v == null) {
			return null;
		}

		return v.toString(formatter);
	}
}
