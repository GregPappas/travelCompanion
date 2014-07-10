package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.lloydstsb.rest.v1.data.ContactsDataIpsum;
import com.lloydstsb.rest.v1.demo.ContactServiceImpl;
import com.lloydstsb.rest.v1.exceptions.NotFoundException;
import com.lloydstsb.rest.v1.valueobjects.Contacts;

public class ContactServiceTest {

    @Test
    public void getContacts_Method_Returns_List_With_Items_In_It()
    {
    	ContactsDataIpsum service = new ContactsDataIpsum();
    	Contacts contacts = service.getContacts();
    	boolean success = false;
    	if (contacts.getContacts().size()>0)
    	{
    		success = true;
    	}
    	assertTrue(success);
    }
    
    @Test
    public void getContactsContent_Method_Returns_List_With_Items_In_It() throws NotFoundException
    {
    	ContactServiceImpl service = new ContactServiceImpl();
    	Contacts contacts = service.getContactsContent();
    	boolean success = false;
    	if (contacts.getContacts().size()>0)
    	{
    		success = true;
    	}
    	assertTrue(success);
    }
    
}
