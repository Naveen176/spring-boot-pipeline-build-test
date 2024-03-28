package com.nagarro.sortingStrategy;

import java.util.List;

import com.nagarro.entity.ValidatedUserData;
import com.nagarro.repository.ValidatedUserDataRepository;

public class SortByNameAndOdd implements SortingStrategy {

	private ValidatedUserDataRepository sortData;

	public SortByNameAndOdd(ValidatedUserDataRepository sortData) {
		this.sortData = sortData;
	}

	@Override
	public List<ValidatedUserData> sorting(int limit, int offset) {
		
		List<ValidatedUserData> validateUserData = sortData.UsersSortedByNameOdd(limit, offset);
		
		return validateUserData;
	}
}