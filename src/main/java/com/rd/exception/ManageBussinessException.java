package com.rd.exception;

public class ManageBussinessException extends RuntimeException {
	private static final long serialVersionUID = 538922474277376456L;

	public ManageBussinessException() {
	}

	public ManageBussinessException(String message) {
		super(message);
	}
}