package com.mmt.libraryApp.exceptions;

public class BookReservedException extends Exception{

	public BookReservedException() {
		super("Book Already reserved");
	}
}
