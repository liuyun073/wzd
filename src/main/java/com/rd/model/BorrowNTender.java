package com.rd.model;

import com.rd.domain.Tender;
import java.io.Serializable;

public class BorrowNTender extends Tender implements Serializable {
	private static final long serialVersionUID = 2737053575621240588L;
	private String username;
	private String borrowname;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBorrowname() {
		return this.borrowname;
	}

	public void setBorrowname(String borrowname) {
		this.borrowname = borrowname;
	}
}