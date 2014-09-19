package com.liuyun.site.model;

import com.liuyun.site.domain.UserCache;
import java.io.Serializable;

public class VIPStatisticModel extends UserCache implements Serializable {
	private static final long serialVersionUID = 6104858028607800906L;
	private String username;
	private String realname;
	private String registertime;
	private String collection;

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

	public String getRegistertime() {
		return this.registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
}