package com.rd.model;

import com.rd.domain.UserCache;
import java.io.Serializable;

public class UserCacheModel extends UserCache implements Serializable {
	private static final long serialVersionUID = -889562087705028166L;
	private String kefu_name;
	private String kefu_realname;
	private String credit_pic;
	private String credit_jifen;
	private String username;
	private String type_id;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType_id() {
		return this.type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getKefu_name() {
		return this.kefu_name;
	}

	public void setKefu_name(String kefu_name) {
		this.kefu_name = kefu_name;
	}

	public String getKefu_realname() {
		return this.kefu_realname;
	}

	public void setKefu_realname(String kefu_realname) {
		this.kefu_realname = kefu_realname;
	}

	public String getCredit_pic() {
		return this.credit_pic;
	}

	public void setCredit_pic(String credit_pic) {
		this.credit_pic = credit_pic;
	}

	public String getCredit_jifen() {
		return this.credit_jifen;
	}

	public void setCredit_jifen(String credit_jifen) {
		this.credit_jifen = credit_jifen;
	}
}