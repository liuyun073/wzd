package com.liuyun.site.domain;

import java.io.Serializable;

public class UserAmountLog implements Serializable {
	private static final long serialVersionUID = 2786223017342339282L;
	private long id;
	private long user_id;
	private String type;
	private String amount_type;
	private String account;
	private String account_all;
	private String account_use;
	private String account_nouse;
	private String remark;
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

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount_type() {
		return this.amount_type;
	}

	public void setAmount_type(String amount_type) {
		this.amount_type = amount_type;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount_all() {
		return this.account_all;
	}

	public void setAccount_all(String account_all) {
		this.account_all = account_all;
	}

	public String getAccount_use() {
		return this.account_use;
	}

	public void setAccount_use(String account_use) {
		this.account_use = account_use;
	}

	public String getAccount_nouse() {
		return this.account_nouse;
	}

	public void setAccount_nouse(String account_nouse) {
		this.account_nouse = account_nouse;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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