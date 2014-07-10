package com.lloydstsb.rest.validation.jsr303;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;

public class Jsr303OutputValidatorTest {
	private Jsr303OutputValidator validator;

	@Before
	public void setUp() throws Exception {
		validator = new Jsr303OutputValidator();
	}

	@Test
	public void testValidateOutput_validOutput() throws Exception {
		DummyBean bean = new DummyBean();
		bean.setField("hello!");

		try {
			validator.validateOutput(bean);
		} catch (ConstraintViolationException e) {
			fail("Did not throw MethodConstraintViolationException when null was passed as an argument annotated with @NotNull");
		}
	}

	@Test
	public void testValidateOutput_invalidOutput() throws Exception {
		DummyBean bean = new DummyBean();
		bean.setField(null);

		try {
			validator.validateOutput(bean);

			fail("Did not throw MethodConstraintViolationException when null was passed as an argument annotated with @NotNull");
		} catch (ConstraintViolationException e) {

		}
	}

	@Test
	public void testValidateOutput_invalidOutputCollection() throws Exception {
		List<DummyBean> invalid = new ArrayList<DummyBean>();
		invalid.add(new DummyBean());
		invalid.add(new DummyBean());

		try {
			validator.validateOutput(invalid);

			fail("Did not throw MethodConstraintViolationException when collection containing invalid items was passed in");
		} catch (ConstraintViolationException e) {

		}
	}

	@Test
	public void testValidateOutput_invalidOutputArray() throws Exception {
		DummyBean[] invalid = new DummyBean[] { new DummyBean(), new DummyBean() };
		
		try {
			validator.validateOutput(invalid);

			fail("Did not throw MethodConstraintViolationException when array containing invalid items was passed in");
		} catch (ConstraintViolationException e) {

		}
	}

	protected class DummyBean {
		@NotNull
		private String aField;

		public String getField() {
			return aField;
		}

		public void setField(String aField) {
			this.aField = aField;
		}
	}
}
