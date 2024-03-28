package com.nagarro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.entity.CountryInfoEntity;
import com.nagarro.entity.ErrorMessagesEntity;
import com.nagarro.entity.PageInfoEntity;
import com.nagarro.entity.ResultEntity;
import com.nagarro.entity.UserAndPageInfo;
import com.nagarro.entity.ValidatedUserData;
import com.nagarro.repository.ValidatedUserDataRepository;
import com.nagarro.sortingStrategy.SortByAgeEven;
import com.nagarro.sortingStrategy.SortByAgeOdd;
import com.nagarro.sortingStrategy.SortByNameAndEven;
import com.nagarro.sortingStrategy.SortByNameAndOdd;
import com.nagarro.sortingStrategy.SortingStrategy;
import com.nagarro.validators.EnglishAlphabetsValidator;
import com.nagarro.validators.NumericValidator;
import com.nagarro.validators.ValidatorFactory;
import com.nagarro.validators.ValidatorFactory.ValidatorType;

@Service
public class UserDataValidationService {

	@Autowired
	private ValidatedUserDataRepository validate;

	@Autowired
	private PageInfoService pagination;

	private SortingStrategy sortingStrategy;

	public ValidatedUserData filterData(List<ResultEntity> user, List<CountryInfoEntity> nationality, String gender) {

		final ValidatedUserData userData = new ValidatedUserData();
		String name = "";
		int age = 0;
		String sex = "";
		String dateOfBirth = "";
		String countryId = "";
		String verificationStatus = "TO_BE_VERIFIED";

		for (ResultEntity info : user) {
			name = info.getName().getFirst() + info.getName().getLast();
			sex = info.getGender();
			dateOfBirth = info.getDob().getDate();
			age = info.getDob().getAge();
			countryId = info.getNat();
		}
		for (CountryInfoEntity country : nationality) {
			if (country.getCountry_id().equals(countryId)) {
				if (sex.equals(gender)) {
					verificationStatus = "VERIFIED";
				}
			}
		}

		userData.setName(name);
		userData.setAge(age);
		userData.setGender(sex);
		userData.setDob(dateOfBirth);
		userData.setNationality(countryId);
		userData.setVerification_status(verificationStatus);
		userData.setDate_created(LocalDateTime.now());
		userData.setDate_modified(LocalDateTime.now());
		validate.save(userData);

		return userData;
	}

	public List<ErrorMessagesEntity> validateUserInputs(String sortType, String sortOrder, String limit,
			String offset) {

		NumericValidator numericValidator = (NumericValidator) ValidatorFactory
				.createValidator(ValidatorType.NUMERIC_VALIDATOR);
		EnglishAlphabetsValidator alphabetsValidator = (EnglishAlphabetsValidator) ValidatorFactory
				.createValidator(ValidatorType.ALPHABETS_VALIDATOR);
		List<ErrorMessagesEntity> validationErrors = new ArrayList<>();

		boolean limitStatus = numericValidator.isInteger(limit);
		boolean offsetStatus = numericValidator.isInteger(offset);
		boolean sortTypeStatus = alphabetsValidator.isAlphabet(sortType);
		boolean sortOrderStatus = alphabetsValidator.isAlphabet(sortOrder);

		if (!sortTypeStatus) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Sort Type must have Alphabets only");
			error.setStatus(404);
			error.setTimestamp(LocalDateTime.now());
			validationErrors.add(error);
		}
		if (!sortOrderStatus) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Sort Order must have Alphabets only");
			error.setStatus(404);
			error.setTimestamp(LocalDateTime.now());
			validationErrors.add(error);
		}
		if (!limitStatus) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Limit must be Nummeric only");
			error.setStatus(400);
			error.setTimestamp(LocalDateTime.now());
			validationErrors.add(error);
		}
		if (!offsetStatus) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Off set must be Numeric only");
			error.setStatus(400);
			error.setTimestamp(LocalDateTime.now());
			validationErrors.add(error);
		}
		return validationErrors;
	}

	public void setSortingStrategy(SortingStrategy sortingStrategy) {
		this.sortingStrategy = sortingStrategy;
	}

	public List<UserAndPageInfo> getValidatedUserData(String sortType, String sortOrder, String limit, String offset) {

		List<UserAndPageInfo> userAndPageInfo = new ArrayList<>();

		if (sortType.equalsIgnoreCase("AGE")) {
			if (sortOrder.equalsIgnoreCase("EVEN")) {
				setSortingStrategy(new SortByAgeEven(validate));
			}else {
				setSortingStrategy(new SortByAgeOdd(validate));
			}
		}
		if (sortType.equalsIgnoreCase("NAME")) {
			if (sortOrder.equalsIgnoreCase("EVEN")) {
				setSortingStrategy(new SortByNameAndEven(validate));
			}else {
				setSortingStrategy(new SortByNameAndOdd(validate));
			}
		}
        //System.out.println("Sorting Strategy: " + sortingStrategy.getClass().getSimpleName());

		List<ValidatedUserData> validateUserData = sortingStrategy.sorting(Integer.parseInt(limit), Integer.parseInt(offset));

		for (ValidatedUserData user : validateUserData) {
			PageInfoEntity pageInfo = pagination.paginationInfo((long) user.getUser_id());
			UserAndPageInfo userInfo = new UserAndPageInfo(user, pageInfo);
			userAndPageInfo.add(userInfo);
		}
		return userAndPageInfo;
	}
}