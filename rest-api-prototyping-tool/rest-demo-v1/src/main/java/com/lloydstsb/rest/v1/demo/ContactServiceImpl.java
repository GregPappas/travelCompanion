package com.lloydstsb.rest.v1.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lloydstsb.rest.v1.contacts.GetContactsContentService;
import com.lloydstsb.rest.v1.data.ContactsDataIpsum;
import com.lloydstsb.rest.v1.valueobjects.Contacts;


@Path("/contacts")
public class ContactServiceImpl implements GetContactsContentService {

    @GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Contacts getContactsContent() {
        ContactsDataIpsum contacts = new ContactsDataIpsum();
    	return contacts.getContacts();
    }
}
