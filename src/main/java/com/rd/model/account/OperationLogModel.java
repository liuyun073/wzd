package com.rd.model.account;

import com.rd.domain.OperationLog;

public class OperationLogModel extends OperationLog {
	private static final long serialVersionUID = 784451181230576969L;
	private String verify_username;
	private String username;
	private String typename;
	private String usertypename;

	public String getUsertypename() {
		return this.usertypename;
	}

	public void setUsertypename(String usertypename) {
		this.usertypename = usertypename;
	}

	public String getVerify_username() {
		return this.verify_username;
	}

	public void setVerify_username(String verify_username) {
		this.verify_username = verify_username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
}