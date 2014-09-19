package com.liuyun.site.domain;

import java.io.Serializable;

public class AccountBank implements Serializable {
	private static final long serialVersionUID = 8096802588572261837L;
	private long id;
	private long user_id;
	private String account;
	private String bank;
	private String branch;
	private int province;
	private int city;
	private int area;
	private String addtime;
	private String addip;
	private String modify_username;
	private String bank_realname;
	private long payment;

	public String getBank_realname() {
		return this.bank_realname;
	}

	public void setBank_realname(String bank_realname) {
		this.bank_realname = bank_realname;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
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

	public String getModify_username() {
		return this.modify_username;
	}

	public void setModify_username(String modify_username) {
		this.modify_username = modify_username;
	}

	public long getPayment() {
		return this.payment;
	}

	public void setPayment(long payment) {
		this.payment = payment;
	}
}