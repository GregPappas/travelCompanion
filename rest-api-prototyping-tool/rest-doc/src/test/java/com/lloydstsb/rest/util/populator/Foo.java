package com.lloydstsb.rest.util.populator;

import java.util.List;

import javax.validation.constraints.Size;

public abstract class Foo<T, A> {
	@Size(min = 1)
	private List<T> things;
	
	@Size(min = 1)
	private List<A> otherThings;

	public List<T> getThings() {
		return things;
	}

	public void setThings(List<T> things) {
		this.things = things;
	}

	public List<A> getOtherThings() {
		return otherThings;
	}

	public void setOtherThings(List<A> otherThings) {
		this.otherThings = otherThings;
	}
}
