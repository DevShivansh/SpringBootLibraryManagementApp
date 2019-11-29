package com.mmt.libraryApp.entity;

import java.util.Calendar;

public class ErrorResponse {

	private String timestamp;

	private String message;

	private String details;

	public ErrorResponse(Exception e) {
		super();
		this.timestamp = Calendar.getInstance().getTime().toString();
		this.message = e.getMessage();
		this.details = e.getStackTrace().toString();
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}
