package com.mmt.libraryApp.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mmt.libraryApp.exceptions.BookNotFoundException;
import com.mmt.libraryApp.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEntity extends User{

	private int id;
	private boolean isActive;
	private List<Book> issuedBooks;

	
	public UserEntity(int id, User user) {
		super(user.getUserName(), user.getEmail(), user.getPassword());
		this.id = id;
		this.isActive = true;
		issuedBooks = new ArrayList<Book>();
	}
	
	public boolean equals(User user) {
		
		if( user.getEmail().equals(getEmail()))
			return true;
		return false;
		
	}
	
	public boolean equals(int id) {
		
		if( this.id == id)
			return true;
		return false;
		
	}

	public void addBook(Book issueBook) {
		issuedBooks.add(issueBook);
		
	}

	public void returnBook(int bookId) throws BookNotFoundException{
		// TODO Auto-generated method stub
		List<Book> books = this.issuedBooks.stream().filter(obj -> obj.equals(bookId)).collect(Collectors.toList());
		if(books.isEmpty())
			throw new BookNotFoundException();
		
		Book book = books.get(0);
		book.unreserve();
		
	}

		
}
