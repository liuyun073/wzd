package com.rd.domain;

public class DownLineBank {
	private long id;
	private String bank_name;
	private String bank_account;
	private String bank_persion;
	private String bank_value;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBank_name() {
		return this.bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_account() {
		return this.bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getBank_persion() {
		return this.bank_persion;
	}

	public void setBank_persion(String bank_persion) {
		this.bank_persion = bank_persion;
	}

	public String getBank_value() {
		return this.bank_value;
	}

	public void setBank_value(String bank_value) {
		this.bank_value = bank_value;
	}
}