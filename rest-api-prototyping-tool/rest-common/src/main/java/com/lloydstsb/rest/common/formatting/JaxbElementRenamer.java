package com.lloydstsb.rest.common.formatting;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.springframework.stereotype.Component;

@Component
@Provider
@Produces(MediaType.APPLICATION_XML)
public class JaxbElementRenamer extends JAXBElementProvider<Object> {
	public JaxbElementRenamer() {
		// this map contains a list of key=>pair values of collection wrapper
		// names to override
		Map<String, String> wrappers = new HashMap<String, String>();
		wrappers.put("com.lloydstsb.rest.v1.valueobjects.Beneficiary", "beneficiaries");
		wrappers.put("com.lloydstsb.rest.v1.valueobjects.arrangements.Arrangement", "arrangements");

		setCollectionWrapperMap(wrappers);
	}
}
