package com.mmt.libraryApp.exceptions;

public class UserAlreadyPresentException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyPresentException() {
		super("User already present in Record");
	}
}
