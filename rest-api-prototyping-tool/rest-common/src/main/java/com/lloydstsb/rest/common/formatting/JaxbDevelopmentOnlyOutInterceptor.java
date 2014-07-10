package com.lloydstsb.rest.common.formatting;

import javax.xml.bind.Marshaller;

import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

/**
 * Tells JAXB to pretty print output.  Not necessary in production.
 * 
 * @author secret
 */
@Component
public class JaxbDevelopmentOnlyOutInterceptor extends AbstractOutDatabindingInterceptor {

	public JaxbDevelopmentOnlyOutInterceptor() {
		super(Phase.MARSHAL);
	}

	public void handleMessage(Message message) {
		message.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}
}