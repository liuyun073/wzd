package com.rd.model.account;

import com.rd.domain.Account;

public class AccountModel extends Account {
	private static final long serialVersionUID = 3261895775405456319L;
	private String bank;
	private String bankaccount;
	private String bankname;
	private String branch;
	private int province;
	private int city;
	private int area;
	private String addtime;
	private String addip;
	private String username;
	private String realname;
	private String modify_username;

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankaccount() {
		return this.bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getProvince() {
		return this.province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return this.city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getArea() {
		return this.area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
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

	public String getModify_username() {
		return this.modify_username;
	}

	public void setModify_username(String modify_username) {
		this.modify_username = modify_username;
	}

	public synchronized void freeze(double money) {
		setNo_use_money(getNo_use_money() + money);
		setUse_money(getUse_money() - money);
	}
}