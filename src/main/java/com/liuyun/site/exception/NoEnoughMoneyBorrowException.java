package com.liuyun.site.exception;

public class NoEnoughMoneyBorrowException extends BorrowException {
	private static final long serialVersionUID = -7400559552805824955L;

	public NoEnoughMoneyBorrowException() {
		super("没有足够的利息.");
	}

	public NoEnoughMoneyBorrowException(String message) {
		super("没有足够的利息." + message);
	}
}