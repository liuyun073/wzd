package com.rd.model.account;

public class AccountOneDayModel extends AccountSumModel {
	private static final long serialVersionUID = 4038060732774453500L;
	private String addtime;
	private double jin_money;

	public double getJin_money() {
		return this.jin_money;
	}

	public void setJin_money(double jin_money) {
		this.jin_money = jin_money;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}