package com.mmt.libraryApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmt.libraryApp.entity.Book;
import com.mmt.libraryApp.entity.ErrorResponse;
import com.mmt.libraryApp.entity.ResponseTO;
import com.mmt.libraryApp.entity.UserEntity;
import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.exceptions.BookReservedException;
import com.mmt.libraryApp.exceptions.UserAlreadyPresentException;
import com.mmt.libraryApp.exceptions.UserNotFoundException;
import com.mmt.libraryApp.model.User;
import com.mmt.libraryApp.service.LibraryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/library")
public class AppController {
	
	@Autowired
	private LibraryService libraryService;
	
	/**
	 * API to create user
	 * return BAD_REQUEST if user is already present
	 * @param user
	 * @return
	 */
	@ApiOperation(value = "Creates new library user and return its Entity", response = UserEntity.class)
	@PostMapping(value = "/createUser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createPerson(@RequestBody User user) {

		UserEntity entity = null;
		try {
			entity = libraryService.createUser(user);
		} catch (UserAlreadyPresentException e) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<UserEntity>(entity, HttpStatus.OK);
	}
	
	
	/**
	 * API to check is book is available
	 * return true if book is not reserved
	 * return false if book is reserved
	 * return BAD_REQUEST if book is not present in DB
	 * @param bookId
	 * @return
	 */
	@ApiOperation(value = "Check book availability", response = ResponseTO.class)
	@GetMapping(value = "/isAvailability/{bookId}", produces = "application/json")
	public ResponseEntity<?> isBookAvailable(@PathVariable final int bookId) {

		ResponseTO response = null;
		try {
			boolean isAvailable = libraryService.checkBookAvailability(bookId);
			response = new ResponseTO("Is Available", isAvailable);
		} catch (BookNotFoundException e) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<ResponseTO>(response, HttpStatus.OK);
	}

	/**
	 * API to reserve book
	 * return book reference if reserved successfully
	 * return BAD_REQUEST if user is invalid, book is not in DB or Book is already reserved
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "Reserve book", response = Book.class)
	@PutMapping(value = "/reserve/{bookId}/{userId}", produces = "application/json")
	public ResponseEntity<?> reserve(@PathVariable final int bookId, @PathVariable final int userId) {

		Book entity = null;
		try {
			entity = libraryService.reserveBook(bookId, userId);
		} catch (BookNotFoundException | UserNotFoundException | BookReservedException e) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Book>(entity, HttpStatus.OK);
	}
	
	/**
	 * API to unreserve book
	 * return BAD_REQUEST is book is not found in DB
	 * @param bookId
	 * @return
	 */
	@ApiOperation(value = "Unreserve book", response = Book.class)
	@PutMapping(value = "/unreserve/{bookId}", produces = "application/json")
	public ResponseEntity<?> unreserve(@PathVariable final int bookId) {

		Book entity = null;
		try {
			entity = libraryService.unreserveBook(bookId);
		} catch (BookNotFoundException e) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Book>(entity, HttpStatus.OK);
	}


	/**
	 * API to issue a book
	 * return BAD_REQUEST is book is not found in DB or user is invalid or book is already reserved
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "Issue a book", response = Book.class)
	@PutMapping(value = "/issue/{bookId}/{userId}", produces = "application/json")
	public ResponseEntity<?> issueBook(@PathVariable final int bookId, @PathVariable final int userId) {

		Book entity = null;
		try {
			entity = libraryService.issueBook(bookId, userId);
		} catch (BookNotFoundException | UserNotFoundException | BookReservedException e) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Book>(entity, HttpStatus.OK);
	}
	
	/**
	 * API to return a book
	 * return BAD_REQUEST is book is not found in DB or user is invalid
	 * @param bookId
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "return a book", response = Book.class)
	@PutMapping(value = "/returnbook/{bookId}/{userId}", produces = "application/json")
	public ResponseEntity<?> returnBook(@PathVariable final int bookId, @PathVariable final int userId) {

		ResponseTO response = null;
		try {
			String result = libraryService.returnBook(bookId, userId);
			response = new ResponseTO("RESULT", result);
		} catch (BookNotFoundException | UserNotFoundException e) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<ResponseTO>(response, HttpStatus.OK);
	}
	
	


}
