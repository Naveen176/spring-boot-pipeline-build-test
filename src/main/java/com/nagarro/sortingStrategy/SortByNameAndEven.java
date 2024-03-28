package com.nagarro.sortingStrategy;

import java.util.List;

import com.nagarro.entity.ValidatedUserData;
import com.nagarro.repository.ValidatedUserDataRepository;

public class SortByNameAndEven implements SortingStrategy {
	
	private ValidatedUserDataRepository sortData;

	public SortByNameAndEven(ValidatedUserDataRepository sortData) {
        this.sortData = sortData;
    }
	
	@Override
	public List<ValidatedUserData> sorting(int limit, int offset) {
		List<ValidatedUserData> validateUserData = sortData.UsersSortedByNameEven(limit, offset);

		return validateUserData;
	}
}