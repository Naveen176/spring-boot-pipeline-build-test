package com.nagarro.validators;

public class NumericValidator {

	private static NumericValidator validator;

	private NumericValidator() {

	}

	public static NumericValidator getInstance() {
		if (validator == null) {
			validator = new NumericValidator();
		}
		return validator;
	}

	public boolean isInteger(String userInput) {
		try {
			Integer.parseInt(userInput);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
