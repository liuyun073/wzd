package com.liuyun.site.tool.interest;

import java.util.List;

public abstract interface InterestCalculator {
	public static final int TYPE_DAY_END = 1;
	public static final int TYPE_MONTH_END = 2;
	public static final int TYPE_MONTH_EQUAL = 3;
	public static final int TYPE_MONTH_INTEREST = 4;

	public abstract List<MonthInterest> each();

	public abstract double getMoneyPerMonth();

	public abstract int getPeriod();

	public abstract List<MonthInterest> getMonthList();

	public abstract double getTotalAccount();

	public abstract String eachDay();
}