package com.rd.domain;

import java.io.Serializable;

public class Reservation implements Serializable {
	private long id;
	private long reservation_user;
	private String borrow_apr;
	private double tender_account;
	private String addtime;
	private String addip;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getReservation_user() {
		return this.reservation_user;
	}

	public void setReservation_user(long reservation_user) {
		this.reservation_user = reservation_user;
	}

	public String getBorrow_apr() {
		return this.borrow_apr;
	}

	public void setBorrow_apr(String borrow_apr) {
		this.borrow_apr = borrow_apr;
	}

	public double getTender_account() {
		return this.tender_account;
	}

	public void setTender_account(double tender_account) {
		this.tender_account = tender_account;
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
}