package com.liuyun.site.domain;

import java.io.Serializable;

public class Friend implements Serializable {
	private static final long serialVersionUID = -2603972601624997167L;
	private long id;
	private long user_id;
	private long friends_userid;
	private String friends_username;
	private int status;
	private int type;
	private String content;
	private String addtime;
	private String addip;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long userId) {
		this.user_id = userId;
	}

	public long getFriends_userid() {
		return this.friends_userid;
	}

	public void setFriends_userid(long friendsUserid) {
		this.friends_userid = friendsUserid;
	}

	public String getFriends_username() {
		return this.friends_username;
	}

	public void setFriends_username(String friendsUsername) {
		this.friends_username = friendsUsername;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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