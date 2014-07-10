/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 * 
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;

@XmlRootElement
public class Arrangements {
	
	public Arrangements() {
		super();
	}
	
	public Arrangements(Page<Arrangement> arrangements) {
		super();
		this.arrangements = arrangements;
	}
	
	private List<String> messages = new ArrayList<String>();
	private Page<Arrangement> arrangements = new Page<Arrangement>();
	
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public void addMessage(String message){
		this.getMessages().add(message);
	}

	public Page<Arrangement> getArrangements() {
		return arrangements;
	}
	public void setArrangements(Page<Arrangement> arrangements) {
		this.arrangements = arrangements;
	}
}
