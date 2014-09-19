package com.liuyun.site.domain;

import java.io.Serializable;

public class UserLog implements Serializable {
	private static final long serialVersionUID = -1521084399193552247L;
	private long log_id;
	private long user_id;
	private String query;
	private String url;
	private String result;
	private String addtime;
	private String addip;

	public long getLog_id() {
		return this.log_id;
	}

	public void setLog_id(long log_id) {
		this.log_id = log_id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
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