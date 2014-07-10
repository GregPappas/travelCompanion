package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.NewBeneficiary;
import com.lloydstsb.rest.v1.valueobjects.Payment;

public class ObjectGeneratorTest {

	private ObjectGenerator objectGenerator = new ObjectGenerator();
	

	@Test
	public void createBeneficiaryShouldReturnANewBeneficairyObjectThatIsNotNull()
	{
		//setup
		String accountNumber = "12345678";
		String sortCode = "123456";
		String name = "somebody";
		String id = "id";
		//act
		Beneficiary result = objectGenerator.createBeneficiary(accountNumber, sortCode, name, id);
		//verify
		assertEquals(Beneficiary.class,result.getClass());
		assertEquals("id",result.getId());
	}

	@Test
	public void createNewBeneficiaryShouldReturnNewBeneficiaryObjectThatIsNotNull()
	{
		//setup
			String id="id";
		//act
			NewBeneficiary result = objectGenerator.createNewBeneficiary(id);
		//verify
			assertEquals(NewBeneficiary.class,result.getClass());
			assertEquals("id",result.getTransactionId());
	}
	
	@Test
	public void createNewPaymentShouldReturnPaymentObjectThatIsNotNull()
	{
	//setup
		String reference="reference";
		String newPaymentId = "newPaymentId";
	//act
		Payment result = objectGenerator.createNewPayment(reference, newPaymentId);
	//verify
		assertEquals(Payment.class,result.getClass());
		assertEquals("reference",result.getReference());
	}
	
}
