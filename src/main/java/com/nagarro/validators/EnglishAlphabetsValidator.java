package com.nagarro.validators;

public class EnglishAlphabetsValidator {
	private static EnglishAlphabetsValidator validator;

	private EnglishAlphabetsValidator() {

	}

	public static EnglishAlphabetsValidator getInstance() {
		if (validator == null) {
			validator = new EnglishAlphabetsValidator();
		}
		return validator;
	}

	public boolean isAlphabet(String input) {
		return input.matches("[a-zA-Z]+");
	}
}
