package com.liuyun.site.exception;

public class AccountException extends BussinessException {
	private static final long serialVersionUID = -7400559552805824955L;

	public AccountException() {
	}

	public AccountException(String message) {
		super(message);
	}
}