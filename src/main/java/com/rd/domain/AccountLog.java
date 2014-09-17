package com.rd.domain;

import java.io.Serializable;

public class AccountLog implements Serializable {
	private static final long serialVersionUID = 220564258543158764L;
	private long id;
	private long user_id;
	private String type;
	private double total;
	private double money;
	private double use_money;
	private double no_use_money;
	private double collection;
	private long to_user;
	private String remark;
	private String addtime;
	private String addip;

	public AccountLog() {
	}

	public AccountLog(long user_id, String type, long to_user, String addtime,
			String addip) {
		this.user_id = user_id;
		this.type = type;
		this.to_user = to_user;
		this.addtime = addtime;
		this.addip = addip;
	}

	public AccountLog(long user_id, String type, double total, double money,
			double use_money, double no_use_money, double collection,
			long to_user, String remark, String addtime, String addip) {
		this.user_id = user_id;
		this.type = type;
		this.total = total;
		this.money = money;
		this.use_money = use_money;
		this.no_use_money = no_use_money;
		this.collection = collection;
		this.to_user = to_user;
		this.remark = remark;
		this.addtime = addtime;
		this.addip = addip;
	}

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

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getUse_money() {
		return this.use_money;
	}

	public void setUse_money(double use_money) {
		this.use_money = use_money;
	}

	public double getNo_use_money() {
		return this.no_use_money;
	}

	public void setNo_use_money(double no_use_money) {
		this.no_use_money = no_use_money;
	}

	public double getCollection() {
		return this.collection;
	}

	public void setCollection(double collection) {
		this.collection = collection;
	}

	public long getTo_user() {
		return this.to_user;
	}

	public void setTo_user(long to_user) {
		this.to_user = to_user;
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