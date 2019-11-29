package com.mmt.libraryApp.exceptions;

public class BookNotFoundException extends Exception {

	public BookNotFoundException() {
		super("Book not found in DB");
	}
}
