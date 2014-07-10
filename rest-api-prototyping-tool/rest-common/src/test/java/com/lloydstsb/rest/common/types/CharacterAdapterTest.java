package com.lloydstsb.rest.common.types;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import org.junit.Test;

public class CharacterAdapterTest {
	private CharacterAdapter adapter = new CharacterAdapter();
	
	@Test
	public void shouldUnmarshalNullStringToNull() throws Exception {
		assertEquals(null, adapter.unmarshal(null));
	}
	
	@Test
	public void shouldUnmarshalStringToCharacter() throws Exception {
		String string = "c";
		
		assertEquals('c', adapter.unmarshal(string).charValue());
	}
	
	@Test(expected = InvalidParameterException.class)
	public void shouldDeclineToUnmarshalLongString() throws Exception {
		String string = "too long";
		
		adapter.unmarshal(string);
	}
	
	@Test
	public void shouldMarshalNullCharacterToNull() throws Exception {
		assertEquals(null, adapter.marshal(null));
	}
	
	@Test
	public void shouldMarshalCharacterToString() throws Exception {
		Character c = 'c';
		
		assertEquals("c", adapter.marshal(c));
	}
}
