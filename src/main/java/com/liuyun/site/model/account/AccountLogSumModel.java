package com.liuyun.site.model.account;

import com.liuyun.site.domain.AccountLog;

public class AccountLogSumModel extends AccountLog {
	private static final long serialVersionUID = -3239716062224960269L;
	private String typename;
	private double sum;

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public double getSum() {
		return this.sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
}