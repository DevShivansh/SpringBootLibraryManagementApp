package com.mmt.libraryApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

	private String userName;
	private String email;
	private String password;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
}
