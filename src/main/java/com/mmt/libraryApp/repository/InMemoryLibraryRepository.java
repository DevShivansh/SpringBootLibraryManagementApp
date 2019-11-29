package com.mmt.libraryApp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mmt.libraryApp.entity.Book;
import com.mmt.libraryApp.entity.UserEntity;
import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.exceptions.BookReservedException;
import com.mmt.libraryApp.exceptions.UserNotFoundException;
import com.mmt.libraryApp.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InMemoryLibraryRepository implements LibraryRepository {

	private static List<Book> books = null;

	private static List<UserEntity> registeredUsers = null;

	private static int userCount = 0;

	static {
		books = new ArrayList<Book>();
		books.add(new Book(1, "Harry Potter & Goblet Of Fire"));
		books.add(new Book(2, "Game of thrones"));
		books.add(new Book(3, "House of Dragon"));
		books.add(new Book(4, "Dance of Dragons"));
		books.add(new Book(5, "Journey to the center of the world"));

		registeredUsers = new ArrayList<UserEntity>();

	}

	@Override
	public boolean isPresent(User user) {
		if (registeredUsers.isEmpty()) {
			log.info("User list if empty, returning false");
			return false;
		}

		return !registeredUsers.stream().filter(obj -> obj.equals(user)).collect(Collectors.toList()).isEmpty();

	}

	@Override
	public UserEntity addNewUser(User user) {
		UserEntity entity = new UserEntity(++userCount, user);
		registeredUsers.add(entity);
		log.info("User added Successfully : {}", entity);
		return entity;
	}

	@Override
	public boolean isBookAvailable(int bookId) throws BookNotFoundException {

		if (books.isEmpty()) {
			log.error("No book available in DB with id : {} , throwing exception", bookId);
			throw new BookNotFoundException();
		}

		List<Book> books = this.books.stream().filter(obj -> obj.equals(bookId)).collect(Collectors.toList());
		if (books.isEmpty()) {
			log.error("No book available in DB with id : {} , throwing exception", bookId);
			throw new BookNotFoundException();
		}
		return !books.get(0).isReserved();

	}

	@Override
	public boolean isPresent(int id) {
		if (registeredUsers.isEmpty())
			return false;

		return !registeredUsers.stream().filter(obj -> obj.equals(id)).collect(Collectors.toList()).isEmpty();
	}

	@Override
	public UserEntity getUser(int userId) throws UserNotFoundException {
		if (registeredUsers.isEmpty()) {
			log.error("User not registered with ID : {} , throwing exception", userId);
			throw new UserNotFoundException();
		}
		List<UserEntity> users = registeredUsers.stream().filter(obj -> obj.equals(userId))
				.collect(Collectors.toList());
		if (users.isEmpty()) {
			log.error("User not registered with ID : {} , throwing exception", userId);
			throw new UserNotFoundException();
		}

		return users.get(0);
	}

	@Override
	public Book reserveBookForUser(int bookId, UserEntity userEntity) {
		
		log.info("Reserving book with ID : {} for user : {}", bookId, userEntity);
		List<Book> books = this.books.stream().filter(obj -> obj.equals(bookId)).collect(Collectors.toList());
		Book requestedBook = books.get(0);
		requestedBook.reserve(userEntity);

		return requestedBook;
	}

	@Override
	public Book unreserveBook(int bookId) throws BookNotFoundException {
		log.info("Unreserving book with id : {}", bookId);
		List<Book> books = this.books.stream().filter(obj -> obj.equals(bookId)).collect(Collectors.toList());
		if (books.isEmpty())
			throw new BookNotFoundException();
		Book book = books.get(0);
		book.unreserve();
		return book;
	}

	@Override
	public Book issueBookForUser(int bookId, UserEntity userEntity)
			throws BookNotFoundException, BookReservedException {
		log.info("Issuing book with ID : {} for user : {}", bookId, userEntity);
		List<Book> books = this.books.stream().filter(obj -> obj.equals(bookId)).collect(Collectors.toList());
		if (books.isEmpty())
			throw new BookNotFoundException();
		Book book = books.get(0);

		if (!book.isReserved() || book.isValidReserver(userEntity)) {
			book.reserve(userEntity);
			return book;
		}

		throw new BookReservedException();
	}

}
