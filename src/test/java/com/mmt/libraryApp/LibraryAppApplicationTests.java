package com.mmt.libraryApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mmt.libraryApp.entity.Book;
import com.mmt.libraryApp.entity.UserEntity;
import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.exceptions.UserAlreadyPresentException;
import com.mmt.libraryApp.model.User;
import com.mmt.libraryApp.repository.InMemoryLibraryRepository;
import com.mmt.libraryApp.service.LibraryServiceImpl;

import lombok.extern.slf4j.Slf4j;


@Slf4j
class LibraryAppApplicationTests {

		
	@Test
	public void testCreateUser() throws UserAlreadyPresentException {
		
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
		
		
		User user = new User("testName", "testEmail", "testPassword");
		
		UserEntity entity = service.createUser(user);
		
		assertEquals(user.getEmail(), entity.getEmail());
		
		
		
	}
	
	@Test
	public void testCreateUserException() throws UserAlreadyPresentException {
		
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
		
		
		User user = new User("testName", "testEmail", "testPassword");
		
		UserEntity entity = service.createUser(user);
		
		assertEquals(user.getEmail(), entity.getEmail());
		
		try {
			service.createUser(user);
		}catch(UserAlreadyPresentException e) {
			assertEquals(e.getMessage(), "User already present in Record");
		}
		
	}
	
	@Test
	public void testCheckBookAvailability() throws BookNotFoundException {
	
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
		
		boolean isAvail = service.checkBookAvailability(1);
		assertEquals(true, isAvail);
		
	}
	
	@Test
	public void testCheckBookAvailabilityException() throws BookNotFoundException {
	
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
		
		try {
			boolean isAvail = service.checkBookAvailability(1);
		}catch(BookNotFoundException e) {
			assertEquals("Book not found in DB", e.getMessage());
		}
		
	}
	
	
	@Test
	public void testReserveBook() throws Exception {
		
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
	
		User user = new User("testName", "testEmail", "testPassword");
		
		UserEntity entity = service.createUser(user);
		
		
		Book book =  service.reserveBook(1, entity.getId());
		
		assertEquals(book.isReserved(), true);
		
	}
	
	@Test
	public void testUnreserveBook() throws Exception {
		
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
	
		User user = new User("testName", "testEmail", "testPassword");
		
		UserEntity entity = service.createUser(user);
		
		
		Book book =  service.reserveBook(1, entity.getId());
		
		assertEquals(book.isReserved(), true);
		
		service.unreserveBook(1);
		
		assertEquals(book.isReserved(), false);
		
	}
	
	
	@Test
	public void testIssueBook() throws Exception {
		
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
	
		User user = new User("testName", "testEmail", "testPassword");
		
		UserEntity entity = service.createUser(user);
		
		
		Book book =  service.issueBook(1, entity.getId());
		
		assertEquals(book.isReserved(), true);
		
	}
	
	@Test
	public void testReturnBook() throws Exception {
		
		LibraryServiceImpl service = new LibraryServiceImpl();
		service.setLibraryRepository(new InMemoryLibraryRepository());
	
		User user = new User("testName", "testEmail", "testPassword");
		
		UserEntity entity = service.createUser(user);
		
		
		Book book =  service.issueBook(1, entity.getId());
		
		assertEquals(book.isReserved(), true);
		
		String result = service.returnBook(1, entity.getId());
		
		assertEquals(book.isReserved(), false);
		assertEquals(result, LibraryServiceImpl.UNRESERVED_BOOK);
		
	}
	
	
}
