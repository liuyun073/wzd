package com.liuyun.site.model;

import java.io.Serializable;

public class BuildinginfoModel implements Serializable {
	private static final long serialVersionUID = -3729549179827654744L;
	private String house_address;
	private String house_area;
	private String house_year;
	private String house_status;
	private String house_holder1;
	private String house_holder2;
	private String house_loanyear;
	private String house_balance;
	private String house_bank;
	private String type;

	public String getHouse_address() {
		return this.house_address;
	}

	public void setHouse_address(String house_address) {
		this.house_address = house_address;
	}

	public String getHouse_area() {
		return this.house_area;
	}

	public void setHouse_area(String house_area) {
		this.house_area = house_area;
	}

	public String getHouse_year() {
		return this.house_year;
	}

	public void setHouse_year(String house_year) {
		this.house_year = house_year;
	}

	public String getHouse_status() {
		return this.house_status;
	}

	public void setHouse_status(String house_status) {
		this.house_status = house_status;
	}

	public String getHouse_holder1() {
		return this.house_holder1;
	}

	public void setHouse_holder1(String house_holder1) {
		this.house_holder1 = house_holder1;
	}

	public String getHouse_holder2() {
		return this.house_holder2;
	}

	public void setHouse_holder2(String house_holder2) {
		this.house_holder2 = house_holder2;
	}

	public String getHouse_loanyear() {
		return this.house_loanyear;
	}

	public void setHouse_loanyear(String house_loanyear) {
		this.house_loanyear = house_loanyear;
	}

	public String getHouse_balance() {
		return this.house_balance;
	}

	public void setHouse_balance(String house_balance) {
		this.house_balance = house_balance;
	}

	public String getHouse_bank() {
		return this.house_bank;
	}

	public void setHouse_bank(String house_bank) {
		this.house_bank = house_bank;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}