package com.liuyun.site.domain;

import java.io.Serializable;

public class CooperationLogin implements Serializable {
	private static final long serialVersionUID = -7665220614505321585L;
	private long id;
	private Byte type;
	private long user_id;
	private String open_id;
	private String open_key;
	private String gmt_create;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Byte getType() {
		return this.type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getOpen_id() {
		return this.open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getOpen_key() {
		return this.open_key;
	}

	public void setOpen_key(String open_key) {
		this.open_key = open_key;
	}

	public String getGmt_create() {
		return this.gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
}