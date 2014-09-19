package com.liuyun.site.model.account;

import com.liuyun.site.domain.Account;

public class AccountSumModel extends Account {
	private static final long serialVersionUID = 4038060732774453500L;
	private double wait_collect;
	private double wait_repay;
	private String username;
	private String realname;
	private double net_assets;

	public double getWait_collect() {
		return this.wait_collect;
	}

	public void setWait_collect(double wait_collect) {
		this.wait_collect = wait_collect;
	}

	public double getWait_repay() {
		return this.wait_repay;
	}

	public void setWait_repay(double wait_repay) {
		this.wait_repay = wait_repay;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public double getNet_assets() {
		return this.net_assets;
	}

	public void setNet_assets(double net_assets) {
		this.net_assets = net_assets;
	}
}