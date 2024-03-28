package com.nagarro.sortingStrategy;

import java.util.List;

import com.nagarro.entity.ValidatedUserData;

public interface SortingStrategy {

	List<ValidatedUserData> sorting(int limit, int offset);
}