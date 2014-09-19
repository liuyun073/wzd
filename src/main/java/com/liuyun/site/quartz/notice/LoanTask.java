package com.liuyun.site.quartz.notice;

public abstract interface LoanTask {
	public static final String LOAN_STATUS = "false";
	public static final String SMS_STATUS = "false";
	public static final String MESSAGE_STATUS = "false";

	public abstract void execute();

	public abstract void doLoan();

	public abstract void stop();

	public abstract Object getLock();
}