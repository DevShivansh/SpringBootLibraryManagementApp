package com.mmt.libraryApp.exceptions;

public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super("User not found in DB");
	}
}
