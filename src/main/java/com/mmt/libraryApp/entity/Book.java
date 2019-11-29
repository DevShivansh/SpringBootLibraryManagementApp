package com.mmt.libraryApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Book {

	private int id;
	private String bookName;
	private boolean isReserved;
	@JsonIgnore
	private UserEntity occupiedUser;
	
	public Book(int id, String bookName) {
		this.id = id;
		this.bookName = bookName;
		this.isReserved = false;
	}
	
	
	
	public boolean equals(int id) {
		
		if( this.id == id)
			return true;
		return false;
		
	}



	public void reserve(UserEntity userEntity) {
		isReserved = true;
		occupiedUser = userEntity;
		
	}



	public void unreserve() {
		isReserved = false;
		occupiedUser = null;
		
	}



	public boolean isValidReserver(UserEntity userEntity) {
		if(occupiedUser.getId() == userEntity.getId())
			return true;
		return false;
	}

	
}
