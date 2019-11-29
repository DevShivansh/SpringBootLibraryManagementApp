package com.mmt.libraryApp.service;

import com.mmt.libraryApp.entity.Book;
import com.mmt.libraryApp.entity.UserEntity;
import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.exceptions.BookReservedException;
import com.mmt.libraryApp.exceptions.UserAlreadyPresentException;
import com.mmt.libraryApp.exceptions.UserNotFoundException;
import com.mmt.libraryApp.model.User;

public interface LibraryService {

	public UserEntity createUser(User user) throws UserAlreadyPresentException;

	public boolean checkBookAvailability(int bookId) throws BookNotFoundException;

	public Book reserveBook(int bookId, int userId)
			throws BookNotFoundException, UserNotFoundException, BookReservedException;

	public Book unreserveBook(int bookId) throws BookNotFoundException;

	public Book issueBook(int bookId, int userId)
			throws BookNotFoundException, UserNotFoundException, BookReservedException;

	public String returnBook(int bookId, int userId)
			throws BookNotFoundException, UserNotFoundException;

}
