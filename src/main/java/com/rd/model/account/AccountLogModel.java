package com.rd.model.account;

import com.rd.domain.AccountLog;

public class AccountLogModel extends AccountLog {
	private static final long serialVersionUID = 784451181230576969L;
	private String username;
	private String to_username;
	private String typename;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTo_username() {
		return this.to_username;
	}

	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
}