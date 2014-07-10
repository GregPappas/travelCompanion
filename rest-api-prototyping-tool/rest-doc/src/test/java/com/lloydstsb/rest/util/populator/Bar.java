package com.lloydstsb.rest.util.populator;

import java.util.List;

public class Bar extends Foo<String, Integer> {
	public List<String> getThings() {
		return super.getThings();
	}

	public List<Integer> getOtherThings() {
		return super.getOtherThings();
	}
}