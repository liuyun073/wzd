package com.rd.domain;

import java.io.Serializable;

public class UserAmountApply implements Serializable {
	private static final long serialVersionUID = -1428496877548363188L;
	private long id;
	private long user_id;
	private String type;
	private double account;
	private double account_new;
	private double account_old;
	private int status;
	private String addtime;
	private String content;
	private String remark;
	private String verify_remark;
	private String verify_time;
	private long verify_user;
	private String addip;
	private String username;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAccount() {
		return this.account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public double getAccount_new() {
		return this.account_new;
	}

	public void setAccount_new(double account_new) {
		this.account_new = account_new;
	}

	public double getAccount_old() {
		return this.account_old;
	}

	public void setAccount_old(double account_old) {
		this.account_old = account_old;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVerify_remark() {
		return this.verify_remark;
	}

	public void setVerify_remark(String verify_remark) {
		this.verify_remark = verify_remark;
	}

	public String getVerify_time() {
		return this.verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public long getVerify_user() {
		return this.verify_user;
	}

	public void setVerify_user(long verify_user) {
		this.verify_user = verify_user;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}