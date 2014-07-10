package com.lloydstsb.rest.v1.domain;

import java.util.List;

public class DomainList<T> {
	private List<T> entities;
	private int totalAvailable;
	
	public DomainList(List<T> entities, int totalAvailable) {
		this.entities = entities;
		this.totalAvailable = totalAvailable;
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public int getTotalAvailable() {
		return totalAvailable;
	}

	public void setTotalAvailable(int totalAvailable) {
		this.totalAvailable = totalAvailable;
	}
}
