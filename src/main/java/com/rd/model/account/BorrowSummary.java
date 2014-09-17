package com.rd.model.account;

public class BorrowSummary {
	private double borrowTotal;
	private double borrowInterest;
	private int borrowTimes;
	private long user_id;

	public double getBorrowTotal() {
		return this.borrowTotal;
	}

	public void setBorrowTotal(double borrowTotal) {
		this.borrowTotal = borrowTotal;
	}

	public double getBorrowInterest() {
		return this.borrowInterest;
	}

	public void setBorrowInterest(double borrowInterest) {
		this.borrowInterest = borrowInterest;
	}

	public int getBorrowTimes() {
		return this.borrowTimes;
	}

	public void setBorrowTimes(int borrowTimes) {
		this.borrowTimes = borrowTimes;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
}