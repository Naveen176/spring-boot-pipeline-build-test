package com.nagarro.sortingStrategy;

import java.util.List;

import com.nagarro.entity.ValidatedUserData;
import com.nagarro.repository.ValidatedUserDataRepository;

public class SortByAgeOdd implements SortingStrategy {
	private ValidatedUserDataRepository sortData;
	
	public SortByAgeOdd(ValidatedUserDataRepository sortData) {
        this.sortData = sortData;
    }
	
	@Override
	public List<ValidatedUserData> sorting(int limit, int offset) {
		List<ValidatedUserData> validateUserData = sortData.UsersSortedByAgeOdd(limit, offset);

		return validateUserData;
	}
}