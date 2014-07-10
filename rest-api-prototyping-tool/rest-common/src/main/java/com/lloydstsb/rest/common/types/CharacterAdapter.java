package com.lloydstsb.rest.common.types;

import java.security.InvalidParameterException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.stereotype.Component;

/**
 * I perform char to string mappings for JAXB, otherwise chars are output as a
 * number. Which, of course, they are.
 * 
 * @author alex
 */
@Component
public class CharacterAdapter extends XmlAdapter<String, Character> {

	@Override
	public Character unmarshal(String s) throws Exception {
		if (s == null) {
			return null;
		}

		if (s.length() != 1) {
			throw new InvalidParameterException("Can only marshal to Character if passed string is one character long.");
		}

		return s.charAt(0);
	}

	@Override
	public String marshal(Character c) throws Exception {
		if (c == null) {
			return null;
		}

		return c.toString();
	}
}
