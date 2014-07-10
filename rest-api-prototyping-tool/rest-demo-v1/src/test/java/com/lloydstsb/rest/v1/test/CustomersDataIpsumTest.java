package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.PhoneNumbersDataIpsum;
import com.lloydstsb.rest.v1.valueobjects.Customer;

public class CustomersDataIpsumTest {
	CustomersDataIpsum customers;
	PhoneNumbersDataIpsum phoneNumbers;
	@Before
	public void initialize()
	{
		phoneNumbers = new PhoneNumbersDataIpsum();
		phoneNumbers.instantiatePhoneNumbers();
		customers = new CustomersDataIpsum();
		customers.instantiateCustomers();
		
	}
	@After
	public void deconstruct()
	{	phoneNumbers.getPhoneNumbersMap().clear();
		customers.getCustomers().clear();
	}
	@Test
	public void getCustomers_Should_Return_Valid_Data()
	{	
		assertNotNull(customers);
		assertTrue(customers.getCustomers().size() == 16);
	}
	@Test
	public void HashMap_Returned_By_getCustomers_Should_Have_Customer_Id_As_Key()
	{
		Customer[] customer_array = customers.getCustomers().values().toArray(new Customer[0]);
		String customerID = customer_array[0].getId();
		assertTrue(customers.getCustomers().containsKey(customerID));
		assertEquals(customers.getCustomers().get(customerID),customer_array[0]);
	}
}
