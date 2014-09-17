package com.rd.model;

import com.rd.domain.Tender;
import java.io.Serializable;

public class BorrowTender extends Tender implements Serializable {
	private static final long serialVersionUID = 2737053575621240588L;
	private String username;
	private String repay_time;
	private String repay_account;
	private String name;
	private double borrow_account;
	private String realname;
	private String card_id;

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCard_id() {
		return this.card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public double getBorrow_account() {
		return this.borrow_account;
	}

	public void setBorrow_account(double borrow_account) {
		this.borrow_account = borrow_account;
	}

	public String getRepay_account() {
		return this.repay_account;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRepay_account(String repay_account) {
		this.repay_account = repay_account;
	}

	public String getRepay_time() {
		return this.repay_time;
	}

	public void setRepay_time(String repay_time) {
		this.repay_time = repay_time;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}