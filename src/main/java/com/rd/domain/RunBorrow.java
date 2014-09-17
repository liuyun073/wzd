package com.rd.domain;

public class RunBorrow {
	private long id;
	private String money;
	private String username;
	private String tel;
	private String description;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}