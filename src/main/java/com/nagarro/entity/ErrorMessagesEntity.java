package com.nagarro.entity;

import java.time.LocalDateTime;

public class ErrorMessagesEntity {
	private String message;
	private int status;
	private LocalDateTime timestamp;

	public ErrorMessagesEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorMessagesEntity(String message, int status, LocalDateTime timestamp) {
		super();
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
