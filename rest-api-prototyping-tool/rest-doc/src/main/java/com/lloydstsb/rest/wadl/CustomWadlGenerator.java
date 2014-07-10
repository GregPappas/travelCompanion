package com.lloydstsb.rest.wadl;

import org.apache.cxf.jaxrs.model.wadl.WadlGenerator;
import org.springframework.stereotype.Component;

@Component
public class CustomWadlGenerator extends WadlGenerator {
	public CustomWadlGenerator() {
		super();
		this.construct(this);
	}

	public CustomWadlGenerator(WadlGenerator other) {
		super(other);
		this.construct(other);
		
	}
	
	private void construct(WadlGenerator wadlGenerator){
		//wadlGenerator.setSchemaLocations(Collections.singletonList("json.schema"));
		// do not create JAXB context
		// other.setUseJaxbContextForQnames(false);
		// let JSON provider handle it
		//wadlGenerator.setIgnoreMessageWriters(false);
		wadlGenerator.setIgnoreForwardSlash(true);
		wadlGenerator.setApplicationTitle("Lloyds Bank REST API");
	}
}
