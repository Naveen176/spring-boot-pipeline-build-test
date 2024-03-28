package com.nagarro.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.entity.ErrorMessagesEntity;
import com.nagarro.entity.SizeRequest;
import com.nagarro.entity.UserAndPageInfo;
import com.nagarro.entity.ValidatedUserData;
import com.nagarro.service.ApiService;
import com.nagarro.service.ErrorMessageService;
import com.nagarro.service.UserDataValidationService;

@RestController
public class ApiController {

	@Autowired
	private ApiService apiService;

	@Autowired
	private ErrorMessageService errorMessageService;

	@Autowired
	private UserDataValidationService userDataValidationService;

	@PostMapping("/users")
	public ResponseEntity<?> savedUsersList(@RequestBody SizeRequest size)
			throws InterruptedException, ExecutionException {
		if (size.getSize() <= 5 && size.getSize() > 0) {
			List<ValidatedUserData> users = apiService.getName(size.getSize());
			return ResponseEntity.ok(users);
		}
		return ResponseEntity.badRequest().body(errorMessageService.errorMessages("size"));
	}

	@GetMapping("/users")
	public ResponseEntity<?> getUser(@RequestParam String sortType, @RequestParam String sortOrder,
			@RequestParam String limit, @RequestParam String offset) {

		List<ErrorMessagesEntity> validationErrors = userDataValidationService.validateUserInputs(sortType, sortOrder, limit,
				offset);

		if (validationErrors.size() == 0) {
			List<ErrorMessagesEntity> errors = errorMessageService.errorMessages(sortType, sortOrder, Integer.parseInt(limit),
					Integer.parseInt(offset));
			if (errors.size() == 0) {
				List<UserAndPageInfo> userData = userDataValidationService.getValidatedUserData(sortType, sortOrder, limit,
						offset);
				return ResponseEntity.ok(userData);
			}
			return ResponseEntity.badRequest().body(errors);
		}
		return ResponseEntity.badRequest().body(validationErrors);
	}
}