/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: GetContactUsContentService  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 17 Apr 2013
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.contacts;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.lloydstsb.rest.v1.valueobjects.Contacts;



/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
@Path("/contacts")
public interface GetContactsContentService {
	@GET
	@Path("")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({
		@Description(target = DocTarget.METHOD, value = "Gets Contacts content for the app."),
		@Description(target = DocTarget.REQUEST, value = "Standard http request")
	})
	public Contacts getContactsContent();
}