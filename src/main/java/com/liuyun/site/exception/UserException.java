package com.liuyun.site.exception;

public class UserException extends BussinessException {
	private static final long serialVersionUID = -7400559552805824955L;

	public UserException() {
	}

	public UserException(String message) {
		super(message);
	}
}