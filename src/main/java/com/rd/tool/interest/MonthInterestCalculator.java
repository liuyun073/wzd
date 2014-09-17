package com.rd.tool.interest;

import java.util.ArrayList;
import java.util.List;

import com.rd.util.NumberUtils;

public class MonthInterestCalculator implements InterestCalculator {
	private double account;
	private double apr;
	private int period;
	private double moneyPerMonth;
	private String eachString;
	private List<MonthInterest> monthList;

	public MonthInterestCalculator() {
		this(0.0D, 0.0D, 0);
	}

	public MonthInterestCalculator(double account, double apr, int period) {
		this.account = account;
		this.apr = (apr / 12.0D);
		this.period = period;
		this.monthList = new ArrayList<MonthInterest>();
	}

	public double getTotalAccount() {
		return this.account * this.apr * this.period + this.account;
	}

	public List<MonthInterest> each() {
		double totalRemain = this.account * this.apr * this.period
				+ this.account;

		double remain = totalRemain - this.account * this.apr;

		double accountPerMon = 0.0D;

		double interest = 0.0D;

		StringBuffer sb = new StringBuffer("");
		sb.append("Total Money:" + totalRemain);
		sb.append("\n");
		sb.append("Month Apr:" + this.apr);
		sb.append("\n");

		for (int i = 0; i < this.period; ++i) {
			interest = NumberUtils.format6(this.account * this.apr);
			this.moneyPerMonth = interest;

			remain = NumberUtils.format6(totalRemain - this.moneyPerMonth);

			accountPerMon = 0.0D;

			totalRemain = NumberUtils.format6(totalRemain - this.moneyPerMonth);
			if (i == this.period - 1) {
				accountPerMon = this.account;
				totalRemain -= this.account;
			}

			MonthInterest mi = new MonthInterest(accountPerMon, interest,
					totalRemain);
			this.monthList.add(mi);

			sb.append("每月还钱:" + this.moneyPerMonth + " 月还款本金："
					+ mi.getAccountPerMon() + " 利息：" + mi.getInterest()
					+ "  余额:" + mi.getTotalRemain());
			sb.append("\n");
		}
		this.eachString = sb.toString();
		return this.monthList;
	}

	public String toString() {
		return this.eachString;
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

	public List<MonthInterest> getMonthList() {
		return this.monthList;
	}

	public void setMonthList(List<MonthInterest> monthList) {
		this.monthList = monthList;
	}

	public String eachDay() {
		double totalRemain = this.account * (this.apr / 12.0D / 30.0D)
				* this.period + this.account;
		System.out.println(totalRemain);
		StringBuffer sb = new StringBuffer("");
		sb.append("Total Money:" + totalRemain);
		sb.append("\n");
		sb.append("Month Apr:" + this.apr);
		sb.append("\n");
		return "借款额度为：" + this.account + "  到期需偿还" + totalRemain;
	}
}