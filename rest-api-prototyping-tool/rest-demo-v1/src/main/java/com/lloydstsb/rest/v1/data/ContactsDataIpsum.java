package com.lloydstsb.rest.v1.data;

import com.lloydstsb.rest.v1.valueobjects.Contact;
import com.lloydstsb.rest.v1.valueobjects.Contacts;

public class ContactsDataIpsum {

    public Contacts getContacts() {
        Contact contact = new Contact();
        contact.setName("Support");
        contact.setOpeningTimes("9.00 - 17.00");
        contact.setPhone("01234 456 789");
        contact.setText("Please call for any Problems you may have");
        Contact contact2 = new Contact();
        contact2.setName("Fraud & Crime Department");
        contact2.setOpeningTimes("24hr");
        contact2.setPhone("01234 789 456");
        contact2.setText("Please call if you feel as though you have been a victim of crime");
        Contacts contactsList = new Contacts();
        contactsList.addContact(contact);
        contactsList.addContact(contact2);
        return contactsList;
    }

}
