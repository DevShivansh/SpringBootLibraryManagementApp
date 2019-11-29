package com.mmt.libraryApp.repository;

import com.mmt.libraryApp.entity.Book;
import com.mmt.libraryApp.entity.UserEntity;
import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.exceptions.BookReservedException;
import com.mmt.libraryApp.exceptions.UserNotFoundException;
import com.mmt.libraryApp.model.User;

public interface LibraryRepository {

	public boolean isPresent(User user);

	public boolean isPresent(int id);

	public UserEntity addNewUser(User user);

	public boolean isBookAvailable(int bookId) throws BookNotFoundException;

	public UserEntity getUser(int userId) throws UserNotFoundException;

	public Book reserveBookForUser(int bookId, UserEntity userEntity);

	public Book unreserveBook(int bookId) throws BookNotFoundException;

	public Book issueBookForUser(int bookId, UserEntity userEntity) throws BookNotFoundException, BookReservedException;

}
