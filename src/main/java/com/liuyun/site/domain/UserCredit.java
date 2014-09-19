package com.liuyun.site.domain;

import java.io.Serializable;

public class UserCredit implements Serializable {
	private static final long serialVersionUID = 4019823126620424066L;
	private long user_id;
	private int value;
	private int op_user;
	private long addtime;
	private String addip;
	private String updatetime;
	private String updateip;
	private int tender_value;
	private int borrow_value;
	private int gift_value;
	private int expense_value;
	private int valid_value;

	public UserCredit() {
	}

	public UserCredit(long user_id, int value, long addtime, String addip) {
		this.user_id = user_id;
		this.value = value;
		this.addtime = addtime;
		this.addip = addip;
	}

	public int getValid_value() {
		return this.valid_value;
	}

	public void setValid_value(int valid_value) {
		this.valid_value = valid_value;
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

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateip() {
		return this.updateip;
	}

	public void setUpdateip(String updateip) {
		this.updateip = updateip;
	}

	public int getTender_value() {
		return this.tender_value;
	}

	public void setTender_value(int tender_value) {
		this.tender_value = tender_value;
	}

	public int getBorrow_value() {
		return this.borrow_value;
	}

	public void setBorrow_value(int borrow_value) {
		this.borrow_value = borrow_value;
	}

	public int getGift_value() {
		return this.gift_value;
	}

	public void setGift_value(int gift_value) {
		this.gift_value = gift_value;
	}

	public int getExpense_value() {
		return this.expense_value;
	}

	public void setExpense_value(int expense_value) {
		this.expense_value = expense_value;
	}
}