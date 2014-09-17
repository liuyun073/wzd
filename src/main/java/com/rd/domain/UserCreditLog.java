package com.rd.domain;

import java.io.Serializable;

public class UserCreditLog implements Serializable {
	private static final long serialVersionUID = 4019823126620424066L;
	private int id;
	private long user_id;
	private int type_id;
	private long op;
	private int value;
	private String remark;
	private int op_user;
	private long addtime;
	private String addip;

	public UserCreditLog() {
	}

	public UserCreditLog(long user_id, long op, long addtime, String addip) {
		this.user_id = user_id;
		this.op = op;
		this.addtime = addtime;
		this.addip = addip;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType_id() {
		return this.type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public long getOp() {
		return this.op;
	}

	public void setOp(long op) {
		this.op = op;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getOp_user() {
		return this.op_user;
	}

	public void setOp_user(int op_user) {
		this.op_user = op_user;
	}

	public long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}
}