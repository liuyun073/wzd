package com.rd.tool.interest;

import java.util.ArrayList;
import java.util.List;

public class EndInterestCalculator implements InterestCalculator {
	private double account;
	private double apr;
	private int period;
	private double moneyPerMonth;
	private String eachString;
	private List<MonthInterest> monthList;

	public EndInterestCalculator() {
		this(0.0D, 0.0D, 0);
	}

	public EndInterestCalculator(double account, double apr, int day) {
		this.account = account;
		this.apr = (apr * day / 360.0D);
		this.period = 1;
		this.monthList = new ArrayList<MonthInterest>();
	}

	public EndInterestCalculator(double account, double apr, int period,
			int type) {
		this.account = account;
		if (type == 1)
			this.apr = (apr * period / 360.0D);
		else {
			this.apr = (apr * period / 12.0D);
		}
		this.period = 1;
		this.monthList = new ArrayList<MonthInterest>();
	}

	public double getTotalAccount() {
		this.moneyPerMonth = (this.account * (1.0D + this.apr));
		return this.moneyPerMonth;
	}

	public List<MonthInterest> each() {
		getTotalAccount();
		double interest = this.moneyPerMonth - this.account;

		StringBuffer sb = new StringBuffer("");
		sb.append("Total Money:" + this.account);
		sb.append("\n");
		sb.append("Month Apr:" + this.apr);
		sb.append("\n");
		sb.append("Month Money:" + this.moneyPerMonth);
		sb.append("\n");

		MonthInterest mi = new MonthInterest(this.account, interest, 0.0D);
		this.monthList.add(mi);

		sb.append("每期还钱:" + this.moneyPerMonth + " 期还款本金："
				+ mi.getAccountPerMon() + " 利息：" + mi.getInterest() + "  余额:"
				+ mi.getTotalRemain());
		sb.append("\n");
		this.eachString = sb.toString();

		return this.monthList;
	}

	public double getAccount() {
		return this.account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public double getApr() {
		return this.apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public int getPeriod() {
		return this.period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public double getMoneyPerMonth() {
		return this.moneyPerMonth;
	}

	public void setMoneyPerMonth(double moneyPerMonth) {
		this.moneyPerMonth = moneyPerMonth;
	}

	public String getEachString() {
		return this.eachString;
	}

	public void setEachString(String eachString) {
		this.eachString = eachString;
	}

	public List<MonthInterest> getMonthList() {
		return this.monthList;
	}

	public void setMonthList(List<MonthInterest> monthList) {
		this.monthList = monthList;
	}

	public String eachDay() {
		return null;
	}
}