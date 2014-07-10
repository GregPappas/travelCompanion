package com.lloydstsb.rest.documentation.v1;

import org.springframework.stereotype.Service;

import com.lloydstsb.rest.util.populator.ObjectPopulator;
import com.lloydstsb.rest.v1.contacts.GetContactsContentService;
import com.lloydstsb.rest.v1.valueobjects.Contacts;

@Service
public class ContactsDataDocumentation implements GetContactsContentService {
	private ObjectPopulator objectPopulator = new ObjectPopulator();

	public Contacts getContactsContent(){
		return objectPopulator.populate(Contacts.class);
	}
}
