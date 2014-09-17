package com.rd.exception;

public class UserServiceException extends BussinessException {
	private static final long serialVersionUID = -7400559552805824955L;

	public UserServiceException(String msg, RuntimeException ex) {
		super(msg, ex);
	}

	public UserServiceException() {
	}

	public UserServiceException(String message) {
		super(message);
	}
}