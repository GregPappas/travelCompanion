/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;

/**
 * Contains a page of items.
 * 
 * @author alex
 * 
 * @param <T>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ Beneficiary.class, Arrangement.class, Transaction.class})
public class Page<T> {
	/**
	 * The total number of available entities
	 */
	@NotNull
	private Integer total;

	/**
	 * The page size - e.g. 10 items in a page
	 */
	@NotNull
	private Integer size;

	/**
	 * The page number - e.g. page 10
	 */
	@NotNull
	private Integer page;

	@XmlElement(name = "item")
	@XmlElementWrapper(name = "items")
	private List<T> items;

	public Page() {

	}

	public Page(List<T> entities) {
		this.items = entities;
	}

	public Page(List<T> entities, int page, int size) {
		this.page = page;
		this.size = size;
		this.total = 0;

		if (entities != null) {
			this.total = Integer.valueOf(entities.size());
		}

		int start = page * size;
		int end = start + size;

		if (entities == null || start > this.total) {
			return;
		}

		if (end > entities.size()) {
			end = entities.size();
		}

		this.items = entities.subList(start, end);
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
