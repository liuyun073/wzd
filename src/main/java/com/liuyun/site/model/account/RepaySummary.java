package com.liuyun.site.model.account;

public class RepaySummary {
	private double repayTotal;
	private double repayInterest;
	private String repayTime;
	private double repayAccount;
	private long user_id;

	public double getRepayTotal() {
		return this.repayTotal;
	}

	public void setRepayTotal(double repayTotal) {
		this.repayTotal = repayTotal;
	}

	public double getRepayInterest() {
		return this.repayInterest;
	}

	public void setRepayInterest(double repayInterest) {
		this.repayInterest = repayInterest;
	}

	public String getRepayTime() {
		return this.repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public double getRepayAccount() {
		return this.repayAccount;
	}

	public void setRepayAccount(double repayAccount) {
		this.repayAccount = repayAccount;
	}
}