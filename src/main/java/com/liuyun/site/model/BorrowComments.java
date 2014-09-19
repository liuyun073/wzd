package com.liuyun.site.model;

import com.liuyun.site.domain.Comments;

public class BorrowComments extends Comments {
	private static final long serialVersionUID = 6096539246733265840L;
	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "BorrowComments [username=" + this.username + ", getId()="
				+ getId() + ", getPid()=" + getPid() + ", getUser_id()="
				+ getUser_id() + ", getModule_code()=" + getModule_code()
				+ ", getArticle_id()=" + getArticle_id() + ", getFlag()="
				+ getFlag() + ", getOrder()=" + getOrder() + ", getStatus()="
				+ getStatus() + ", getAddip()=" + getAddip()
				+ ", getAddtime()=" + getAddtime() + ", getComment()="
				+ getComment() + ", getClass()=" + super.getClass()
				+ ", hashCode()=" + super.hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}