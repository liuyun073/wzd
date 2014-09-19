package com.liuyun.site.tool.interest;

public class MonthInterest {
	private double accountPerMon;
	private double interest;
	private double totalRemain;

	public MonthInterest() {
	}

	public MonthInterest(double accountPerMon, double interest,
			double totalRemain) {
		this.accountPerMon = accountPerMon;
		this.interest = interest;
		this.totalRemain = totalRemain;
	}

	public double getAccountPerMon() {
		return this.accountPerMon;
	}

	public void setAccountPerMon(double accountPerMon) {
		this.accountPerMon = accountPerMon;
	}

	public double getInterest() {
		return this.interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getTotalRemain() {
		return this.totalRemain;
	}

	public void setTotalRemain(double totalRemain) {
		this.totalRemain = totalRemain;
	}
}