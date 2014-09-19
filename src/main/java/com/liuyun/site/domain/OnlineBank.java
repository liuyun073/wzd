package com.liuyun.site.domain;

public class OnlineBank {
	private long id;
	private String bank_name;
	private String bank_logo;
	private String bank_value;
	private long payment_interface_id;

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

	public String getBank_logo() {
		return this.bank_logo;
	}

	public void setBank_logo(String bank_logo) {
		this.bank_logo = bank_logo;
	}

	public String getBank_value() {
		return this.bank_value;
	}

	public void setBank_value(String bank_value) {
		this.bank_value = bank_value;
	}

	public long getPayment_interface_id() {
		return this.payment_interface_id;
	}

	public void setPayment_interface_id(long payment_interface_id) {
		this.payment_interface_id = payment_interface_id;
	}
}