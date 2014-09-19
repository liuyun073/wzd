package com.liuyun.site.exception;

public class BussinessException extends RuntimeException {
	private static final long serialVersionUID = 538922474277376456L;

	public BussinessException(String msg, RuntimeException ex) {
		super(msg, ex);
	}

	public BussinessException() {
	}

	public BussinessException(String message) {
		super(message);
	}
}