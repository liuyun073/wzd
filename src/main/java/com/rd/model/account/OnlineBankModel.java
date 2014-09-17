package com.rd.model.account;

import com.rd.domain.OnlineBank;

public class OnlineBankModel extends OnlineBank {
	private static final long serialVersionUID = 3261895775405456319L;
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}