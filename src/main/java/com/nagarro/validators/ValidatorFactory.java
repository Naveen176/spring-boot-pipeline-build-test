package com.nagarro.validators;

public class ValidatorFactory {

	public enum ValidatorType {
		NUMERIC_VALIDATOR, ALPHABETS_VALIDATOR
	}

	public static Object createValidator(ValidatorType type) {
		switch (type) {
		case NUMERIC_VALIDATOR:
			return NumericValidator.getInstance();
		case ALPHABETS_VALIDATOR:
			return EnglishAlphabetsValidator.getInstance();
		default:
			throw new IllegalArgumentException("Invalid validator type");
		}
	}
}
