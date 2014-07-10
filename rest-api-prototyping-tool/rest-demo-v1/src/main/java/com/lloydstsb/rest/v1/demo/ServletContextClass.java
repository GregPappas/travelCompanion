package com.lloydstsb.rest.v1.demo;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.ext.Provider;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.PersistenceContainer;
import com.lloydstsb.rest.v1.data.PhoneNumbersDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;

import java.io.IOException;

@Provider
public class ServletContextClass implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent event) {
		
		   System.out.println("<----------------resource model initiated--------------->");
		   
		   PersistenceContainer.getInstance().setContext(event.getServletContext());
		   
		   PhoneNumbersDataIpsum phoneNumbers = new PhoneNumbersDataIpsum();
		   CustomersDataIpsum customers = new CustomersDataIpsum();
		   phoneNumbers.instantiatePhoneNumbers();
		   customers.instantiateCustomers();
		   ArrangementServiceDataIpsum arrangements = new ArrangementServiceDataIpsum();
		   arrangements.instantiateExchangeLockCustomersArrangements();
		   TransactionsDataIpsum transactions = new TransactionsDataIpsum();
            try {
                transactions.readAndParseCSVFile();
            } catch (IOException e) {
                throw new IllegalStateException("Could not parse the transaction data: ", e);
            }
            BeneficiaryDataIpsum beneficiary = new BeneficiaryDataIpsum();
		    beneficiary.generateHashMapOfBeneficiaries();
		}
}