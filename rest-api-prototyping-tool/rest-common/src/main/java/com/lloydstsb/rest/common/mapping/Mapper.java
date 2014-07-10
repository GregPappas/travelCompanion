package com.lloydstsb.rest.common.mapping;

import java.util.List;

public interface Mapper<From, To> {

	public To map(From source);
	
	public List<To> map(List<From> source);
}
