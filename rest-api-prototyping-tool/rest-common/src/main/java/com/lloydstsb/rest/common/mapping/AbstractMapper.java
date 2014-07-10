package com.lloydstsb.rest.common.mapping;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<From, To> implements Mapper<From, To> {

	public List<To> map(List<From> source) {
		if (source == null) {
			return null;
		}
		List<To> output = new ArrayList<To>(source.size());

		for (From d : source) {
			To to = map(d);
			if (to != null) {
				output.add(to);
			}
		}

		return output;
	}

	public List<To> map(From[] source) {
		if (source == null) {
			return null;
		}
		List<To> output = new ArrayList<To>(source.length);

		for (From d : source) {
			To to = map(d);
			if (to != null) {
				output.add(to);
			}
		}

		return output;
	}
}
