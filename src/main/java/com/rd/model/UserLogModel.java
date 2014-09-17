package com.rd.model;

import com.rd.domain.UserLog;
import java.io.Serializable;

public class UserLogModel extends UserLog implements Serializable {
	private static final long serialVersionUID = -6799451812033085927L;
	private String userName;
	private String realName;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}