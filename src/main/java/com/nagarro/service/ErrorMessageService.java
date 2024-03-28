package com.nagarro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.entity.ErrorMessagesEntity;
import com.nagarro.repository.ValidatedUserDataRepository;

@Service

public class ErrorMessageService {
	
	@Autowired
	private ValidatedUserDataRepository validate;

	public List<ErrorMessagesEntity> errorMessages(String sortType, String sortOrder, int limit, int offset) {

		List<ErrorMessagesEntity> errors = new ArrayList<>();

		if (!(sortType.equalsIgnoreCase("Name") || sortType.equalsIgnoreCase("Age"))) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Sort Type should be Name or Age");
			error.setStatus(404);
			error.setTimestamp(LocalDateTime.now());
			errors.add(error);
		}
		if (!(sortOrder.equalsIgnoreCase("Even") || sortOrder.equalsIgnoreCase("Odd"))) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Sort Order should be Even or Odd");
			error.setStatus(404);
			error.setTimestamp(LocalDateTime.now());
			errors.add(error);
		}
		if (limit < 0 || limit > 5) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Limit should be in Range 1-5");
			error.setStatus(400);
			error.setTimestamp(LocalDateTime.now());
			errors.add(error);
		}
		if (offset > validate.count() || offset < 0) {
			ErrorMessagesEntity error = new ErrorMessagesEntity();
			error.setMessage("Offset should be in Greater than 0 and Less than Length of DB");
			error.setStatus(400);
			error.setTimestamp(LocalDateTime.now());
			errors.add(error);
		}
		return errors;
	}

	public ErrorMessagesEntity errorMessages(String parameter) {
		ErrorMessagesEntity error = new ErrorMessagesEntity();
		if (parameter.equalsIgnoreCase("size")) {
			error.setMessage("size should be in Range 1-5");
			error.setStatus(400);
			error.setTimestamp(LocalDateTime.now());
		}
		return error;
	}
}
