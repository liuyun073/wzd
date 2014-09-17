package com.rd.model;

import com.rd.domain.Collection;

public class DetailCollection extends Collection {
	private static final long serialVersionUID = -5620227157548250710L;
	private String borrow_name;
	private long borrow_id;
	private String time_limit;
	private String username;
	private String tendertime;
	private String borrow_style;

	public String getBorrow_name() {
		return this.borrow_name;
	}

	public void setBorrow_name(String borrow_name) {
		this.borrow_name = borrow_name;
	}

	public String getTime_limit() {
		return this.time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getBorrow_id() {
		return this.borrow_id;
	}

	public void setBorrow_id(long borrow_id) {
		this.borrow_id = borrow_id;
	}

	public String getTendertime() {
		return this.tendertime;
	}

	public void setTendertime(String tendertime) {
		this.tendertime = tendertime;
	}

	public String getBorrow_style() {
		return this.borrow_style;
	}

	public void setBorrow_style(String borrow_style) {
		this.borrow_style = borrow_style;
	}
}