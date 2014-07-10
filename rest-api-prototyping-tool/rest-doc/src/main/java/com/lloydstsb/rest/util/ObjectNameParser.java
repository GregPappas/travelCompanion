package com.lloydstsb.rest.util;

public class ObjectNameParser {

	public String findClassName(String clazz) {
		try {
			String[] parts = clazz.split("\\[");
			parts = parts[0].split("\\.");

			clazz = "";

			for (int i = 0; i < parts.length - 1; i++) {
				clazz += parts[i];

				if (i < parts.length - 2) {
					clazz += ".";
				}
			}

			return clazz;
		} catch (Throwable t) {
			return null;
		}
	}
	
	public String findMethodName(String clazz) {
		try {
			String[] parts = clazz.split("\\[");
			parts = parts[0].split("\\.");
			
			return parts[parts.length - 1];
		} catch (Throwable t) {
			return null;
		}
	}
	
	public Integer findArgumentIndex(String clazz) {
		try {
			String[] parts = clazz.split("\\[");
			parts = parts[parts.length - 1].split("\\]");
			
			return new Integer(parts[0]);
		} catch (Throwable t) {
			return null;
		}
	}
}
