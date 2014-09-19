package com.liuyun.site.model;

import com.liuyun.site.domain.UserCreditLog;
import java.io.Serializable;

public class UserCreditLogModel extends UserCreditLog implements Serializable {
	private static final long serialVersionUID = 5093702455445890306L;
	private String username;
	private String realname;
	private String typeName;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}