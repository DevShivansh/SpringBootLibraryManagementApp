package com.mmt.libraryApp.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ResponseTO {

	private Map<Object, Object> map;
	
	public ResponseTO(Object key, Object value) {
		// TODO Auto-generated constructor stub
		map = new HashMap<Object, Object>();
		map.put(key, value);
	}
}
