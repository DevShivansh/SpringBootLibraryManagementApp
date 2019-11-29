package com.mmt.libraryApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmt.libraryApp.entity.Book;
import com.mmt.libraryApp.entity.UserEntity;
import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.exceptions.BookReservedException;
import com.mmt.libraryApp.exceptions.UserAlreadyPresentException;
import com.mmt.libraryApp.exceptions.UserNotFoundException;
import com.mmt.libraryApp.model.User;
import com.mmt.libraryApp.repository.LibraryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService{
	
	public static final String UNRESERVED_BOOK = "Successfully unreserved Book";
	
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	public void setLibraryRepository(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}

	@Override
	public UserEntity createUser(User user) throws UserAlreadyPresentException {
		
		if( libraryRepository.isPresent(user)) {
			log.info("User already present, throwing exception!");
			throw new UserAlreadyPresentException();
		}
		log.info("Adding user {} in DB" , user);
		return libraryRepository.addNewUser(user);
	}

	@Override
	public boolean checkBookAvailability(int bookId) throws BookNotFoundException {
		log.info("Checking Book availability for Book ID : {}", bookId);
		if( libraryRepository.isBookAvailable(bookId)) {
			return true;
		}
		return false;
	}

	@Override
	public Book reserveBook(int bookId, int userId) throws BookNotFoundException,
		UserNotFoundException, BookReservedException {
		
		log.info("Reserving book with id {} for user id {}", bookId, userId);
		UserEntity userEntity = libraryRepository.getUser(userId);
		
		if( checkBookAvailability(bookId)) {
			return libraryRepository.reserveBookForUser(bookId, userEntity);
		}
		log.info("Book already reserved, throwing exception!");
		throw new BookReservedException();
	}

	@Override
	public Book unreserveBook(int bookId) throws BookNotFoundException {
		return libraryRepository.unreserveBook( bookId);
	}

	@Override
	public Book issueBook(int bookId, int userId)
			throws BookNotFoundException, UserNotFoundException, BookReservedException {
		log.info("Issuing book with id {} for user id {}", bookId, userId);
		UserEntity userEntity = libraryRepository.getUser(userId);
		
		Book issueBook = libraryRepository.issueBookForUser(bookId, userEntity);
		userEntity.addBook(issueBook);
		return issueBook;
	}

	@Override
	public String returnBook(int bookId, int userId) throws BookNotFoundException, UserNotFoundException {
		log.info("Returning book with id {} for user id {}", bookId, userId);
		UserEntity userEntity = libraryRepository.getUser(userId);
		userEntity.returnBook(bookId);
		
		return UNRESERVED_BOOK;
	}

	
	
}
