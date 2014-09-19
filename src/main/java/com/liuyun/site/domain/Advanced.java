package com.liuyun.site.domain;

import java.io.Serializable;

public class Advanced implements Serializable {
	private static final long serialVersionUID = -125906918083036989L;
	private long id;
	private double advance_reserve;
	private double no_advanced_account;
	private double borrow_total;
	private double wait_total;
	private double borrow_day_total;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAdvance_reserve() {
		return this.advance_reserve;
	}

	public void setAdvance_reserve(double advance_reserve) {
		this.advance_reserve = advance_reserve;
	}

	public double getNo_advanced_account() {
		return this.no_advanced_account;
	}

	public void setNo_advanced_account(double no_advanced_account) {
		this.no_advanced_account = no_advanced_account;
	}

	public double getBorrow_total() {
		return this.borrow_total;
	}

	public void setBorrow_total(double borrow_total) {
		this.borrow_total = borrow_total;
	}

	public double getWait_total() {
		return this.wait_total;
	}

	public void setWait_total(double wait_total) {
		this.wait_total = wait_total;
	}

	public double getBorrow_day_total() {
		return this.borrow_day_total;
	}

	public void setBorrow_day_total(double borrow_day_total) {
		this.borrow_day_total = borrow_day_total;
	}
}